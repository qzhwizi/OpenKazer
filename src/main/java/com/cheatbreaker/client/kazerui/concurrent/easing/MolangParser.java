package com.cheatbreaker.client.kazerui.concurrent.easing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;

public final class MolangParser {
   private static final Map<String, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a> CACHE = new LinkedHashMap<String, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a>(
      256, 0.75F, true
   ) {
      @Override
      protected boolean removeEldestEntry(Entry<String, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a> var1) {
         return this.size() > 4096;
      }
   };
   private static final Random RANDOM = new Random();
   public static final com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a ZERO = var0 -> 0.0;
   private static final Map<String, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.b> FUNCTIONS = new HashMap<>();

   private MolangParser() {
   }

   public static com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a a(double var0) {
      return var2 -> var0;
   }

   public static synchronized com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a a(String var0) {
      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = CACHE.get(var0);
      if (var1 == null) {
         try {
            var1 = new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.cInner(var0).a();
         } catch (RuntimeException var3) {
            LogManager.getLogger("Kazer/Molang").debug("Could not parse '{}': {}", new Object[]{var0, var3.toString()});
            var1 = ZERO;
         }

         CACHE.put(var0, var1);
      }

      return var1;
   }

   public static synchronized void b(String var0) {
      String var1 = null;
      String[] var2 = null;
      StringBuilder var3 = new StringBuilder();

      for (String var7 : var0.split("\\r?\\n")) {
         if (!var7.isEmpty() && !Character.isWhitespace(var7.charAt(0))) {
            if (var1 != null) {
               FUNCTIONS.put(var1, new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.b(var2, a(var3.toString())));
            }

            int var8 = var7.indexOf(40);
            int var9 = var7.indexOf(41);
            if (var8 >= 0 && var9 >= var8) {
               var1 = var7.substring(0, var8).trim().toLowerCase();
               String var10 = var7.substring(var8 + 1, var9).trim();
               var2 = var10.isEmpty() ? new String[0] : var10.toLowerCase().split("\\s*,\\s*");
               var3.setLength(0);
            } else {
               var1 = null;
            }
         } else if (var1 != null) {
            var3.append(var7).append('\n');
         }
      }

      if (var1 != null) {
         FUNCTIONS.put(var1, new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.b(var2, a(var3.toString())));
      }
   }

   static String c(String var0) {
      if (var0.startsWith("q.")) {
         return "query." + var0.substring(2);
      } else if (var0.startsWith("v.")) {
         return "variable." + var0.substring(2);
      } else if (var0.startsWith("t.")) {
         return "temp." + var0.substring(2);
      } else {
         return var0.startsWith("c.") ? "context." + var0.substring(2) : var0;
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a b(String var0, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[] var1) {
      switch (var0) {
         case "math.cos":
            return var1x -> Math.cos(Math.toRadians(a(var1, 0, var1x)));
         case "math.sin":
            return var1x -> Math.sin(Math.toRadians(a(var1, 0, var1x)));
         case "math.acos":
            return var1x -> Math.toDegrees(Math.acos(a(var1, 0, var1x)));
         case "math.asin":
            return var1x -> Math.toDegrees(Math.asin(a(var1, 0, var1x)));
         case "math.atan":
            return var1x -> Math.toDegrees(Math.atan(a(var1, 0, var1x)));
         case "math.atan2":
            return var1x -> Math.toDegrees(Math.atan2(a(var1, 0, var1x), a(var1, 1, var1x)));
         case "math.abs":
            return var1x -> Math.abs(a(var1, 0, var1x));
         case "math.ceil":
            return var1x -> Math.ceil(a(var1, 0, var1x));
         case "math.floor":
            return var1x -> Math.floor(a(var1, 0, var1x));
         case "math.round":
            return var1x -> Math.round(a(var1, 0, var1x));
         case "math.trunc":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               return var2 < 0.0 ? Math.ceil(var2) : Math.floor(var2);
            };
         case "math.sqrt":
            return var1x -> Math.sqrt(a(var1, 0, var1x));
         case "math.exp":
            return var1x -> Math.exp(a(var1, 0, var1x));
         case "math.ln":
            return var1x -> Math.log(a(var1, 0, var1x));
         case "math.pow":
            return var1x -> Math.pow(a(var1, 0, var1x), a(var1, 1, var1x));
         case "math.mod":
            return var1x -> {
               double var2 = a(var1, 1, var1x);
               return var2 == 0.0 ? 0.0 : a(var1, 0, var1x) % var2;
            };
         case "math.min":
            return var1x -> Math.min(a(var1, 0, var1x), a(var1, 1, var1x));
         case "math.max":
            return var1x -> Math.max(a(var1, 0, var1x), a(var1, 1, var1x));
         case "math.clamp":
            return var1x -> Math.max(a(var1, 1, var1x), Math.min(a(var1, 2, var1x), a(var1, 0, var1x)));
         case "math.lerp":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               return var2 + (a(var1, 1, var1x) - var2) * var1x.a(a(var1, 2, var1x));
            };
         case "math.lerprotate":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               double var4 = a(var1, 1, var1x);
               double var6 = var1x.a(a(var1, 2, var1x));
               double var8 = ((var4 - var2) % 360.0 + 540.0) % 360.0 - 180.0;
               return var2 + var8 * var6;
            };
         case "math.hermite_blend":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               return 3.0 * var2 * var2 - 2.0 * var2 * var2 * var2;
            };
         case "math.min_angle":
            return var1x -> {
               double var2 = a(var1, 0, var1x) % 360.0;
               if (var2 >= 180.0) {
                  var2 -= 360.0;
               }

               if (var2 < -180.0) {
                  var2 += 360.0;
               }

               return var2;
            };
         case "math.sign":
            return var1x -> Math.signum(a(var1, 0, var1x));
         case "math.random":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               double var4 = a(var1, 1, var1x);
               return var2 + RANDOM.nextDouble() * (var4 - var2);
            };
         case "math.random_integer":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               double var4 = a(var1, 1, var1x);
               return Math.floor(var2 + RANDOM.nextDouble() * (var4 - var2 + 1.0));
            };
         case "math.die_roll":
            return var1x -> {
               double var2 = a(var1, 0, var1x);
               double var4 = a(var1, 1, var1x);
               double var6 = a(var1, 2, var1x);
               double var8 = 0.0;

               for (int var10 = 0; var10 < var2; var10++) {
                  var8 += var4 + RANDOM.nextDouble() * (var6 - var4);
               }

               return var8;
            };
         default:
            return var0.startsWith("lunar.") ? var2 -> {
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.b var3 = FUNCTIONS.get(var0);
               if (var3 == null) {
                  return 0.0;
               } else {
                  double[] var4 = new double[var3.params.length];

                  for (int var5 = 0; var5 < var4.length; var5++) {
                     var4[var5] = a(var1, var5, var2);
                  }

                  Double[] var14 = new Double[var4.length];

                  for (int var6 = 0; var6 < var4.length; var6++) {
                     var14[var6] = var2.vars.get(var3.params[var6]);
                     var2.vars.put(var3.params[var6], var4[var6]);
                  }

                  boolean var12 = false /* VF: Semaphore variable */;

                  double var15;
                  try {
                     var12 = true;
                     var15 = var3.body.eval(var2);
                     var12 = false;
                  } finally {
                     if (var12) {
                        int var10 = 0;

                        while (true) {
                           if (var10 >= var4.length) {
                              ;
                           } else {
                              if (var14[var10] == null) {
                                 var2.vars.remove(var3.params[var10]);
                              } else {
                                 var2.vars.put(var3.params[var10], var14[var10]);
                              }

                              var10++;
                           }
                        }
                     }
                  }

                  for (int var8 = 0; var8 < var4.length; var8++) {
                     if (var14[var8] == null) {
                        var2.vars.remove(var3.params[var8]);
                     } else {
                        var2.vars.put(var3.params[var8], var14[var8]);
                     }
                  }

                  return var15;
               }
            } : var2 -> var0.startsWith("query.") && var2.queries != null && var1.length == 0 ? var2.queries.query(var0.substring(6)) : 0.0;
      }
   }

   private static double a(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[] var0, int var1, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f var2) {
      return var1 < var0.length ? var0[var1].eval(var2) : 0.0;
   }

   public interface a {
      double eval(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f var1);
   }

   private static final class b {
      final String[] params;
      final com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a body;

      b(String[] var1, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2) {
         this.params = var1;
         this.body = var2;
      }
   }

   private static final class cInner {
      private final String s;
      private int i;

      cInner(String var1) {
         this.s = var1.toLowerCase();
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a a() {
         ArrayList var1 = new ArrayList();

         while (true) {
            this.m();
            if (this.i >= this.s.length()) {
               if (var1.isEmpty()) {
                  return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.ZERO;
               }

               return var1x -> {
                  double var2 = 0.0;

                  try {
                     for (com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var5 : (Iterable<com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a>) var1) {
                        var2 = var5.eval(var1x);
                     }

                     return var2;
                  } catch (com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.e var6) {
                     return var6.value;
                  }
               };
            }

            if (this.a(';')) {
               this.i++;
            } else {
               var1.add(this.b());
            }
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a b() {
         this.m();
         if (this.s.startsWith("return", this.i) && !Character.isLetterOrDigit(this.a(this.i + 6)) && this.a(this.i + 6) != '_') {
            this.i += 6;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var5 = this.c();
            return var1x -> {
               throw new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.e(var5.eval(var1x));
            };
         } else if (this.s.startsWith("if", this.i) && !Character.isLetterOrDigit(this.a(this.i + 2)) && this.a(this.i + 2) != '_') {
            this.i += 2;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.k();
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = this.k();
            this.m();
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.ZERO;
            if (this.s.startsWith("else", this.i)) {
               this.i += 4;
               this.m();
               var3 = this.s.startsWith("if", this.i) ? this.b() : this.k();
            }

            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var4 = var3;
            return var3x -> var1.eval(var3x) != 0.0 ? var2.eval(var3x) : var4.eval(var3x);
         } else {
            return this.c();
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a c() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.d();
         this.m();
         if (this.a('?') && this.a(this.i + 1) != '?') {
            this.i++;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = this.c();
            this.m();
            if (!this.a(':')) {
               return var2x -> var1.eval(var2x) != 0.0 ? var2.eval(var2x) : 0.0;
            } else {
               this.i++;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = this.c();
               return var3x -> var1.eval(var3x) != 0.0 ? var2.eval(var3x) : var3.eval(var3x);
            }
         } else {
            return var1;
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a d() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.e();

         while (true) {
            this.m();
            if (!this.s.startsWith("??", this.i)) {
               return var1;
            }

            this.i += 2;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = var1;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = this.e();
            var1 = var2 instanceof com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.g
               ? var2x -> var2x.vars.containsKey(((com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.g)var2).key) ? var2.eval(var2x) : var3.eval(var2x)
               : var2x -> {
                  double var3x = var2.eval(var2x);
                  return var3x != 0.0 ? var3x : var3.eval(var2x);
               };
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a e() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.f();

         while (true) {
            this.m();
            if (!this.s.startsWith("||", this.i)) {
               return var1;
            }

            this.i += 2;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = var1;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = this.f();
            var1 = var2x -> var2.eval(var2x) == 0.0 && var3.eval(var2x) == 0.0 ? 0.0 : 1.0;
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a f() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.g();

         while (true) {
            this.m();
            if (!this.s.startsWith("&&", this.i)) {
               return var1;
            }

            this.i += 2;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = var1;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = this.g();
            var1 = var2x -> var2.eval(var2x) != 0.0 && var3.eval(var2x) != 0.0 ? 1.0 : 0.0;
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a g() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.h();

         while (true) {
            this.m();
            String var2;
            if (!this.s.startsWith("==", this.i) && !this.s.startsWith("!=", this.i) && !this.s.startsWith("<=", this.i) && !this.s.startsWith(">=", this.i)) {
               if (!this.a('<') && !this.a('>')) {
                  return var1;
               }

               var2 = this.s.substring(this.i, this.i + 1);
            } else {
               var2 = this.s.substring(this.i, this.i + 2);
            }

            this.i = this.i + var2.length();
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = var1;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var4 = this.h();
            switch (var2) {
               case "==":
                  var1 = var2x -> var3.eval(var2x) == var4.eval(var2x) ? 1.0 : 0.0;
                  break;
               case "!=":
                  var1 = var2x -> var3.eval(var2x) != var4.eval(var2x) ? 1.0 : 0.0;
                  break;
               case "<=":
                  var1 = var2x -> var3.eval(var2x) <= var4.eval(var2x) ? 1.0 : 0.0;
                  break;
               case ">=":
                  var1 = var2x -> var3.eval(var2x) >= var4.eval(var2x) ? 1.0 : 0.0;
                  break;
               case "<":
                  var1 = var2x -> var3.eval(var2x) < var4.eval(var2x) ? 1.0 : 0.0;
                  break;
               default:
                  var1 = var2x -> var3.eval(var2x) > var4.eval(var2x) ? 1.0 : 0.0;
            }
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a h() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.i();

         while (true) {
            this.m();
            if (this.a('+')) {
               this.i++;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var4 = var1;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var5 = this.i();
               var1 = var2x -> var4.eval(var2x) + var5.eval(var2x);
            } else {
               if (!this.a('-')) {
                  return var1;
               }

               this.i++;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = var1;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = this.i();
               var1 = var2x -> var2.eval(var2x) - var3.eval(var2x);
            }
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a i() {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.j();

         while (true) {
            this.m();
            if (this.a('*')) {
               this.i++;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var4 = var1;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var6 = this.j();
               var1 = var2x -> var4.eval(var2x) * var6.eval(var2x);
            } else if (this.a('/')) {
               this.i++;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var7 = var1;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = this.j();
               var1 = var2x -> {
                  double var3x = var3.eval(var2x);
                  return var3x == 0.0 ? 0.0 : var7.eval(var2x) / var3x;
               };
            } else {
               if (!this.a('%')) {
                  return var1;
               }

               this.i++;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = var1;
               com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var5 = this.j();
               var1 = var2x -> {
                  double var3x = var5.eval(var2x);
                  return var3x == 0.0 ? 0.0 : var2.eval(var2x) % var3x;
               };
            }
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a j() {
         this.m();
         if (this.a('-')) {
            this.i++;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var2 = this.j();
            return var1x -> -var2.eval(var1x);
         } else if (this.a('+')) {
            this.i++;
            return this.j();
         } else if (this.a('!')) {
            this.i++;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var1 = this.j();
            return var1x -> var1.eval(var1x) == 0.0 ? 1.0 : 0.0;
         } else {
            return this.k();
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a k() {
         this.m();
         if (this.a('(')) {
            this.i++;
            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var7 = this.c();
            this.m();
            if (this.a(')')) {
               this.i++;
            }

            return var7;
         } else if (this.a('{')) {
            int var6 = 0;

            int var9;
            for (var9 = ++this.i; this.i < this.s.length() && (this.s.charAt(this.i) != '}' || var6 != 0); this.i++) {
               if (this.s.charAt(this.i) == '{') {
                  var6++;
               }

               if (this.s.charAt(this.i) == '}') {
                  var6--;
               }
            }

            com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var12 = new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.cInner(
                  this.s.substring(var9, Math.min(this.i, this.s.length()))
               )
               .a();
            this.i++;
            return var12;
         } else if (this.a('\'')) {
            int var5 = this.s.indexOf(39, this.i + 1);
            this.i = var5 < 0 ? this.s.length() : var5 + 1;
            return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.ZERO;
         } else {
            char var1 = this.a(this.i);
            if (!Character.isDigit(var1) && var1 != '.') {
               String var8 = this.l();
               if (var8 == null) {
                  throw new IllegalArgumentException("Unexpected '" + var1 + "' in " + this.s);
               } else if (var8.equals("true")) {
                  return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(1.0);
               } else if (var8.equals("false")) {
                  return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(0.0);
               } else {
                  this.m();
                  if (!this.a('(')) {
                     String var11 = com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.c(var8);
                     if (var11.equals("math.pi")) {
                        return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(Math.PI);
                     } else if (this.a('=') && this.a(this.i + 1) != '=') {
                        this.i++;
                        com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var4 = this.c();
                        return var2x -> {
                           double var3x = var4.eval(var2x);
                           var2x.vars.put(var11, var3x);
                           return var3x;
                        };
                     } else {
                        return new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.g(var11);
                     }
                  } else {
                     this.i++;
                     ArrayList var10 = new ArrayList();
                     this.m();

                     for (; !this.a(')') && this.i < this.s.length(); this.m()) {
                        var10.add(this.c());
                        this.m();
                        if (this.a(',')) {
                           this.i++;
                        }
                     }

                     this.i++;
                     return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.b(
                        com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.c(var8), (com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[]) var10.toArray(new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[0])
                     );
                  }
               }
            } else {
               int var2 = this.i;

               while (this.i < this.s.length() && (Character.isDigit(this.s.charAt(this.i)) || this.s.charAt(this.i) == '.')) {
                  this.i++;
               }

               if (this.i < this.s.length() && this.s.charAt(this.i) == 'f') {
                  this.i++;
               }

               double var3 = Double.parseDouble(this.s.substring(var2, this.i).replace("f", ""));
               return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(var3);
            }
         }
      }

      String l() {
         this.m();
         int var1 = this.i;

         while (this.i < this.s.length() && (Character.isLetterOrDigit(this.s.charAt(this.i)) || this.s.charAt(this.i) == '_' || this.s.charAt(this.i) == '.')) {
            this.i++;
         }

         if (this.i != var1 && !Character.isDigit(this.s.charAt(var1))) {
            return this.s.substring(var1, this.i);
         } else {
            this.i = var1;
            return null;
         }
      }

      void m() {
         while (this.i < this.s.length() && Character.isWhitespace(this.s.charAt(this.i))) {
            this.i++;
         }
      }

      boolean a(char var1) {
         return this.i < this.s.length() && this.s.charAt(this.i) == var1;
      }

      char a(int var1) {
         return var1 < this.s.length() ? this.s.charAt(var1) : '\u0000';
      }
   }

   public interface d {
      double query(String var1);
   }

   private static final class e extends RuntimeException {
      final double value;

      e(double var1) {
         super(null, null, false, false);
         this.value = var1;
      }
   }

   public static final class f {
      public final Map<String, Double> vars = new HashMap<>();
      public com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.d queries;
      public double interpolationSeconds = -1.0;
      private double cachedSeconds = Double.NaN;
      private double f05;
      private double f08;
      private double f1;
      private double f125;
      private double f25;
      private double f5;

      double a(double var1) {
         if (this.interpolationSeconds < 0.0) {
            return var1;
         } else {
            if (this.cachedSeconds != this.interpolationSeconds) {
               this.cachedSeconds = this.interpolationSeconds;
               this.f05 = this.b(0.05);
               this.f08 = this.b(0.08);
               this.f1 = this.b(0.1);
               this.f125 = this.b(0.125);
               this.f25 = this.b(0.25);
               this.f5 = this.b(0.5);
            }

            return var1 == 0.05
               ? this.f05
               : (
                  var1 == 0.08
                     ? this.f08
                     : (var1 == 0.1 ? this.f1 : (var1 == 0.125 ? this.f125 : (var1 == 0.25 ? this.f25 : (var1 == 0.5 ? this.f5 : this.b(var1)))))
               );
         }
      }

      private double b(double var1) {
         return com.cheatbreaker.client.kazerui.concurrent.rendercache.AnimationClock.a(var1, this.interpolationSeconds);
      }

      double a(String var1) {
         Double var2 = this.vars.get(var1);
         if (var2 != null) {
            return var2;
         } else if (var1.startsWith("query.")) {
            return this.queries == null ? 0.0 : this.queries.query(var1.substring(6));
         } else {
            return var2 == null ? 0.0 : var2;
         }
      }
   }

   private static final class g implements com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a {
      final String key;

      g(String var1) {
         this.key = var1;
      }

      @Override
      public double eval(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f var1) {
         return var1.a(this.key);
      }
   }
}
