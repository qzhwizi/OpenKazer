package com.cheatbreaker.client.kazerui.gui.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class MiniJsonParser {
   private final String s;
   private int i;

   private MiniJsonParser(String var1) {
      this.s = var1;
   }

   static Object a(String var0) {
      return new com.cheatbreaker.client.kazerui.gui.components.MiniJsonParser(var0).a();
   }

   private Object a() {
      this.e();
      char var1 = this.s.charAt(this.i);
      if (var1 == '{') {
         return this.b();
      } else if (var1 == '[') {
         return this.c();
      } else if (var1 == '"') {
         return this.d();
      } else {
         int var2 = this.i;

         while (this.i < this.s.length() && ",}] \t\r\n".indexOf(this.s.charAt(this.i)) < 0) {
            this.i++;
         }

         String var3 = this.s.substring(var2, this.i);
         if (var3.equals("null")) {
            return null;
         } else {
            return !var3.equals("true") && !var3.equals("false") ? var3 : Boolean.valueOf(var3);
         }
      }
   }

   private Map<String, Object> b() {
      LinkedHashMap var1 = new LinkedHashMap();
      this.i++;
      this.e();
      if (this.s.charAt(this.i) == '}') {
         this.i++;
         return var1;
      } else {
         do {
            this.e();
            String var2 = this.d();
            this.e();
            this.i++;
            var1.put(var2, this.a());
            this.e();
         } while (this.s.charAt(this.i++) != '}');

         return var1;
      }
   }

   private List<Object> c() {
      ArrayList var1 = new ArrayList();
      this.i++;
      this.e();
      if (this.s.charAt(this.i) == ']') {
         this.i++;
         return var1;
      } else {
         do {
            var1.add(this.a());
            this.e();
         } while (this.s.charAt(this.i++) != ']');

         return var1;
      }
   }

   private String d() {
      StringBuilder var1 = new StringBuilder();
      this.i++;

      while (true) {
         char var2 = this.s.charAt(this.i++);
         if (var2 == '"') {
            return var1.toString();
         }

         if (var2 != '\\') {
            var1.append(var2);
         } else {
            char var3 = this.s.charAt(this.i++);
            switch (var3) {
               case 'b':
                  var1.append('\b');
                  break;
               case 'c':
               case 'd':
               case 'e':
               case 'g':
               case 'h':
               case 'i':
               case 'j':
               case 'k':
               case 'l':
               case 'm':
               case 'o':
               case 'p':
               case 'q':
               case 's':
               default:
                  var1.append(var3);
                  break;
               case 'f':
                  var1.append('\f');
                  break;
               case 'n':
                  var1.append('\n');
                  break;
               case 'r':
                  var1.append('\r');
                  break;
               case 't':
                  var1.append('\t');
                  break;
               case 'u':
                  var1.append((char)Integer.parseInt(this.s.substring(this.i, this.i + 4), 16));
                  this.i += 4;
            }
         }
      }
   }

   private void e() {
      while (this.i < this.s.length() && Character.isWhitespace(this.s.charAt(this.i))) {
         this.i++;
      }
   }
}
