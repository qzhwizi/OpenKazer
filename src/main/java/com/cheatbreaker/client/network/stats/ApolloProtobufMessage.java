package com.cheatbreaker.client.network.stats;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ApolloProtobufMessage {
   private final Map<Integer, List<Object>> fields = new HashMap<>();

   ApolloProtobufMessage(byte[] var1) {
      if (var1.length > 32767) {
         throw new IllegalArgumentException("Oversized Apollo payload");
      } else {
         ApolloProtobufMessage.a var2 = new ApolloProtobufMessage.a(var1);
         int var3 = 0;

         while (var2.pos < var1.length) {
            long var4 = var2.a();
            int var6 = (int)(var4 >>> 3);
            int var7 = (int)(var4 & 7L);
            if (var6 > 0 && var4 >>> 3 <= 536870911L) {
               if (++var3 <= 8192) {
                  Object var8;
                  switch (var7) {
                     case 0:
                        var8 = var2.a();
                        break;
                     case 1:
                        var8 = var2.a(8);
                        break;
                     case 2:
                        long var9 = var2.a();
                        if (var9 < 0L || var9 > var1.length - var2.pos) {
                           throw new IllegalArgumentException("Truncated protobuf");
                        }

                        var8 = Arrays.copyOfRange(var1, var2.pos, var2.pos + (int)var9);
                        var2.pos += (int)var9;
                        break;
                     case 3:
                     case 4:
                     default:
                        throw new IllegalArgumentException("Unsupported protobuf wire type");
                     case 5:
                        var8 = var2.a(4);
                  }

                  this.fields.computeIfAbsent(var6, var0 -> new ArrayList<>()).add(var8);
                  continue;
               }
            }

            throw new IllegalArgumentException("Invalid protobuf field");
         }
      }
   }

   List<Object> a(int var1) {
      return this.fields.getOrDefault(var1, Collections.emptyList());
   }

   boolean b(int var1) {
      return this.fields.containsKey(var1);
   }

   List<Long> c(int var1) {
      ArrayList var2 = new ArrayList();

      for (Object var4 : this.a(var1)) {
         if (var4 instanceof Long) {
            var2.add((Long)var4);
         } else {
            ApolloProtobufMessage.a var5 = new ApolloProtobufMessage.a((byte[])var4);

            while (var5.pos < var5.bytes.length) {
               if (var2.size() >= 64) {
                  throw new IllegalArgumentException("Too many packed values");
               }

               var2.add(var5.a());
            }
         }
      }

      return var2;
   }

   private Object i(int var1) {
      List var2 = this.a(var1);
      return var2.isEmpty() ? null : var2.get(var2.size() - 1);
   }

   long d(int var1) {
      Object var2 = this.i(var1);
      return var2 == null ? 0L : (Long)var2;
   }

   float e(int var1) {
      return Float.intBitsToFloat((int)this.d(var1));
   }

   byte[] f(int var1) {
      Object var2 = this.i(var1);
      return var2 == null ? new byte[0] : (byte[])var2;
   }

   String g(int var1) {
      return new String(this.f(var1), StandardCharsets.UTF_8);
   }

   com.cheatbreaker.client.network.stats.ApolloProtobufMessage h(int var1) {
      return new com.cheatbreaker.client.network.stats.ApolloProtobufMessage(this.f(var1));
   }

   private static final class a {
      final byte[] bytes;
      int pos;

      a(byte[] var1) {
         this.bytes = var1;
      }

      long a() {
         long var1 = 0L;

         for (int var3 = 0; var3 < 10; var3++) {
            if (this.pos >= this.bytes.length) {
               throw new IllegalArgumentException("Truncated varint");
            }

            int var4 = this.bytes[this.pos++] & 255;
            if (var3 == 9 && (var4 & 254) != 0) {
               throw new IllegalArgumentException("Varint overflow");
            }

            var1 |= (long)(var4 & 127) << var3 * 7;
            if ((var4 & 128) == 0) {
               return var1;
            }
         }

         throw new IllegalArgumentException("Varint overflow");
      }

      long a(int var1) {
         if (this.bytes.length - this.pos < var1) {
            throw new IllegalArgumentException("Truncated fixed value");
         } else {
            long var2 = 0L;

            for (int var4 = 0; var4 < var1; var4++) {
               var2 |= (long)(this.bytes[this.pos++] & 255) << var4 * 8;
            }

            return var2;
         }
      }
   }

   static final class b {
      private final ByteArrayOutputStream out = new ByteArrayOutputStream();

      private void a(long var1) {
         while ((var1 & -128L) != 0L) {
            this.out.write((int)var1 & 127 | 128);
            var1 >>>= 7;
         }

         this.out.write((int)var1);
      }

      ApolloProtobufMessage.b a(int var1, long var2) {
         this.a((long)var1 << 3);
         this.a(var2);
         return this;
      }

      ApolloProtobufMessage.b b(int var1, long var2) {
         this.a((long)var1 << 3 | 1L);
         this.a(var2, 8);
         return this;
      }

      ApolloProtobufMessage.b a(int var1, float var2) {
         this.a((long)var1 << 3 | 5L);
         this.a((long)Float.floatToIntBits(var2), 4);
         return this;
      }

      private void a(long var1, int var3) {
         for (int var4 = 0; var4 < var3; var4++) {
            this.out.write((int)var1 & 0xFF);
            var1 >>>= 8;
         }
      }

      ApolloProtobufMessage.b a(int var1, byte[] var2) {
         this.a((long)var1 << 3 | 2L);
         this.a(var2.length);
         this.out.write(var2, 0, var2.length);
         return this;
      }

      ApolloProtobufMessage.b a(int var1, String var2) {
         return this.a(var1, var2.getBytes(StandardCharsets.UTF_8));
      }

      byte[] a() {
         return this.out.toByteArray();
      }
   }
}
