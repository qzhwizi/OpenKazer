package com.cheatbreaker.client.kazerui.concurrent.easing;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class BedrockAnimation {
   public final String name;
   public double length;
   public final boolean loop;
   private final Map<String, com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner[]> bones = new LinkedHashMap<>();

   private BedrockAnimation(String var1, double var2, boolean var4) {
      this.name = var1;
      this.length = var2;
      this.loop = var4;
   }

   private static double[] b(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[] var0, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f var1, double[] var2) {
      var2[0] = var0[0].eval(var1);
      var2[1] = var0[1].eval(var1);
      var2[2] = var0[2].eval(var1);
      return var2;
   }

   public static Map<String, com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation> a(JsonObject var0) {
      HashMap var1 = new HashMap();
      JsonObject var2 = var0.getAsJsonObject("animations");
      if (var2 == null) {
         return var1;
      } else {
         for (Entry var4 : var2.entrySet()) {
            if (((JsonElement)var4.getValue()).isJsonObject()) {
               JsonObject var5 = ((JsonElement)var4.getValue()).getAsJsonObject();
               JsonElement var6 = var5.get("loop");
               boolean var7 = var6 != null && var6.isJsonPrimitive() && var6.getAsJsonPrimitive().isBoolean() && var6.getAsBoolean();
               double var8 = var5.has("animation_length") ? var5.get("animation_length").getAsDouble() : -1.0;
               com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation var10 = new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation((String)var4.getKey(), var8, var7);
               double var11 = 0.0;
               JsonObject var13 = var5.getAsJsonObject("bones");
               if (var13 != null) {
                  for (Entry var15 : var13.entrySet()) {
                     if (((JsonElement)var15.getValue()).isJsonObject()) {
                        JsonObject var16 = ((JsonElement)var15.getValue()).getAsJsonObject();
                        com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner[] var17 = new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner[3];
                        String[] var18 = new String[]{"rotation", "position", "scale"};

                        for (int var19 = 0; var19 < 3; var19++) {
                           if (var16.has(var18[var19])) {
                              var17[var19] = a(var16.get(var18[var19]), var19 == 2);
                              if (var17[var19] != null) {
                                 for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c var21 : var17[var19].keys) {
                                    var11 = Math.max(var11, var21.time);
                                 }
                              }
                           }
                        }

                        var10.bones.put((String)var15.getKey(), var17);
                     }
                  }
               }

               if (var8 <= 0.0) {
                  var10.length = var11;
               }

               var1.put(var4.getKey(), var10);
            }
         }

         return var1;
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner a(JsonElement var0, boolean var1) {
      com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner var2 = new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner();
      if (var0.isJsonObject() && !var0.getAsJsonObject().has("vector") && !var0.getAsJsonObject().has("post") && !var0.getAsJsonObject().has("pre")) {
         ArrayList var3 = new ArrayList(var0.getAsJsonObject().entrySet());
         Iterator var4 = var3.iterator();

         while (true) {
            Entry var5;
            double var6;
            while (true) {
               if (!var4.hasNext()) {
                  Collections.sort(var2.keys, (var0x, var1x) -> Double.compare(var0x.time, var1x.time));
                  if (var2.keys.isEmpty()) {
                     return null;
                  }

                  return var2;
               }

               var5 = (Entry)var4.next();

               try {
                  var6 = Double.parseDouble((String)var5.getKey());
                  break;
               } catch (NumberFormatException var14) {
               }
            }

            JsonElement var8 = (JsonElement)var5.getValue();
            String var9 = null;
            double[] var10 = null;
            if (var8.isJsonObject()) {
               JsonObject var11 = var8.getAsJsonObject();
               if (var11.has("easing")) {
                  var9 = var11.get("easing").getAsString();
               }

               if (var11.has("easingArgs")) {
                  JsonArray var12 = var11.getAsJsonArray("easingArgs");
                  var10 = new double[var12.size()];

                  for (int var13 = 0; var13 < var10.length; var13++) {
                     var10[var13] = var12.get(var13).getAsDouble();
                  }
               }

               if (var11.has("lerp_mode") && var11.get("lerp_mode").getAsString().equals("step")) {
                  var9 = "step";
               }
            }

            var2.keys.add(new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c(var6, b(var8, var1), var9, var10));
         }
      } else {
         var2.keys.add(new com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c(0.0, b(var0, var1), null, null));
         return var2;
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[] b(JsonElement var0, boolean var1) {
      if (var0.isJsonObject()) {
         JsonObject var2 = var0.getAsJsonObject();
         var0 = var2.has("vector")
            ? var2.get("vector")
            : (var2.has("post") ? var2.get("post") : (var2.has("pre") ? var2.get("pre") : new JsonPrimitive(var1 ? 1 : 0)));
         if (var0.isJsonObject()) {
            return b((JsonElement)var0, var1);
         }
      }

      if (var0.isJsonArray()) {
         JsonArray var7 = var0.getAsJsonArray();
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var3 = a(var7.get(0));
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var4 = var7.size() > 1 ? a(var7.get(1)) : var3;
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var5 = var7.size() > 2 ? a(var7.get(2)) : var3;
         return new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[]{var3, var4, var5};
      } else {
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a var6 = a((JsonElement)var0);
         return new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[]{var6, var6, var6};
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a a(JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonPrimitive var1 = var0.getAsJsonPrimitive();
         if (var1.isNumber()) {
            return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(var1.getAsDouble());
         } else if (var1.isBoolean()) {
            return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(var1.getAsBoolean() ? 1.0 : 0.0);
         } else {
            String var2 = var1.getAsString().trim();

            try {
               return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(Double.parseDouble(var2));
            } catch (NumberFormatException var4) {
               return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(var2);
            }
         }
      } else {
         return com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.ZERO;
      }
   }

   public void a(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var1, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f var2, double var3) {
      double var5 = this.length > 0.0 ? (this.loop ? var3 % this.length : Math.min(var3, this.length)) : var3;
      var2.vars.put("query.anim_time", var5);

      for (Entry var8 : this.bones.entrySet()) {
         com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var9 = var1.bones.get(var8.getKey());
         if (var9 != null) {
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner[] var10 = (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.aInner[])var8.getValue();
            if (var10[0] != null) {
               double[] var11 = var10[0].a(var2, var5);
               var9.rotX = var9.b() + (float)Math.toRadians(-var11[0]);
               var9.rotY = var9.c() + (float)Math.toRadians(-var11[1]);
               var9.rotZ = var9.d() + (float)Math.toRadians(var11[2]);
            }

            if (var10[1] != null) {
               double[] var12 = var10[1].a(var2, var5);
               var9.posX = (float)var12[0];
               var9.posY = (float)var12[1];
               var9.posZ = (float)var12[2];
            }

            if (var10[2] != null) {
               double[] var13 = var10[2].a(var2, var5);
               var9.scaleX = (float)var13[0];
               var9.scaleY = (float)var13[1];
               var9.scaleZ = (float)var13[2];
            }
         }
      }
   }

   static final class aInner {
      final List<com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c> keys = new ArrayList<>();
      final double[] result = new double[3];

      double[] a(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f var1, double var2) {
         if (this.keys.size() != 1 && !(var2 <= this.keys.get(0).time)) {
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c var4 = this.keys.get(this.keys.size() - 1);
            if (var2 >= var4.time) {
               return com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.b(var4.value, var1, this.result);
            } else {
               for (int var5 = 1; var5 < this.keys.size(); var5++) {
                  com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c var6 = this.keys.get(var5);
                  if (!(var2 > var6.time)) {
                     com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.c var7 = this.keys.get(var5 - 1);
                     double var8 = var6.time - var7.time;
                     double var10 = var8 <= 0.0 ? 1.0 : (var2 - var7.time) / var8;
                     var10 = com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.b.a(var6.easing, var6.easingArgs, var10);
                     double var12 = var7.value[0].eval(var1);
                     double var14 = var7.value[1].eval(var1);
                     double var16 = var7.value[2].eval(var1);
                     double var18 = var6.value[0].eval(var1);
                     double var20 = var6.value[1].eval(var1);
                     double var22 = var6.value[2].eval(var1);
                     this.result[0] = var12 + (var18 - var12) * var10;
                     this.result[1] = var14 + (var20 - var14) * var10;
                     this.result[2] = var16 + (var22 - var16) * var10;
                     return this.result;
                  }
               }

               return com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.b(var4.value, var1, this.result);
            }
         } else {
            return com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.b(this.keys.get(0).value, var1, this.result);
         }
      }
   }

   static final class b {
      static double a(String var0, double[] var1, double var2) {
         if (var0 == null) {
            return var2;
         } else {
            switch (var0) {
               case "step":
                  return var2 >= 1.0 ? 1.0 : 0.0;
               case "linear":
                  return var2;
               case "easeInSine":
                  return 1.0 - Math.cos(var2 * Math.PI / 2.0);
               case "easeOutSine":
                  return Math.sin(var2 * Math.PI / 2.0);
               case "easeInOutSine":
                  return -(Math.cos(Math.PI * var2) - 1.0) / 2.0;
               case "easeInQuad":
                  return var2 * var2;
               case "easeOutQuad":
                  return 1.0 - (1.0 - var2) * (1.0 - var2);
               case "easeInOutQuad":
                  return var2 < 0.5 ? 2.0 * var2 * var2 : 1.0 - Math.pow(-2.0 * var2 + 2.0, 2.0) / 2.0;
               case "easeInCubic":
                  return var2 * var2 * var2;
               case "easeOutCubic":
                  return 1.0 - Math.pow(1.0 - var2, 3.0);
               case "easeInOutCubic":
                  return var2 < 0.5 ? 4.0 * var2 * var2 * var2 : 1.0 - Math.pow(-2.0 * var2 + 2.0, 3.0) / 2.0;
               case "easeInQuart":
                  return var2 * var2 * var2 * var2;
               case "easeOutQuart":
                  return 1.0 - Math.pow(1.0 - var2, 4.0);
               case "easeInOutQuart":
                  return var2 < 0.5 ? 8.0 * Math.pow(var2, 4.0) : 1.0 - Math.pow(-2.0 * var2 + 2.0, 4.0) / 2.0;
               case "easeInQuint":
                  return Math.pow(var2, 5.0);
               case "easeOutQuint":
                  return 1.0 - Math.pow(1.0 - var2, 5.0);
               case "easeInOutQuint":
                  return var2 < 0.5 ? 16.0 * Math.pow(var2, 5.0) : 1.0 - Math.pow(-2.0 * var2 + 2.0, 5.0) / 2.0;
               case "easeInExpo":
                  return var2 == 0.0 ? 0.0 : Math.pow(2.0, 10.0 * var2 - 10.0);
               case "easeOutExpo":
                  return var2 == 1.0 ? 1.0 : 1.0 - Math.pow(2.0, -10.0 * var2);
               case "easeInOutExpo":
                  return var2 == 0.0
                     ? 0.0
                     : (var2 == 1.0 ? 1.0 : (var2 < 0.5 ? Math.pow(2.0, 20.0 * var2 - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * var2 + 10.0)) / 2.0));
               case "easeInCirc":
                  return 1.0 - Math.sqrt(1.0 - var2 * var2);
               case "easeOutCirc":
                  return Math.sqrt(1.0 - Math.pow(var2 - 1.0, 2.0));
               case "easeInOutCirc":
                  return var2 < 0.5
                     ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * var2, 2.0))) / 2.0
                     : (Math.sqrt(1.0 - Math.pow(-2.0 * var2 + 2.0, 2.0)) + 1.0) / 2.0;
               case "easeInBack":
                  double var10 = a(var1, 1.70158);
                  return (var10 + 1.0) * var2 * var2 * var2 - var10 * var2 * var2;
               case "easeOutBack":
                  double var9 = a(var1, 1.70158);
                  return 1.0 + (var9 + 1.0) * Math.pow(var2 - 1.0, 3.0) + var9 * Math.pow(var2 - 1.0, 2.0);
               case "easeInOutBack":
                  double var8 = a(var1, 1.70158) * 1.525;
                  return var2 < 0.5
                     ? Math.pow(2.0 * var2, 2.0) * ((var8 + 1.0) * 2.0 * var2 - var8) / 2.0
                     : (Math.pow(2.0 * var2 - 2.0, 2.0) * ((var8 + 1.0) * (var2 * 2.0 - 2.0) + var8) + 2.0) / 2.0;
               case "easeInElastic":
                  return var2 == 0.0 ? 0.0 : (var2 == 1.0 ? 1.0 : -Math.pow(2.0, 10.0 * var2 - 10.0) * Math.sin((var2 * 10.0 - 10.75) * (Math.PI * 2.0 / 3.0)));
               case "easeOutElastic":
                  return var2 == 0.0 ? 0.0 : (var2 == 1.0 ? 1.0 : Math.pow(2.0, -10.0 * var2) * Math.sin((var2 * 10.0 - 0.75) * (Math.PI * 2.0 / 3.0)) + 1.0);
               case "easeInOutElastic":
                  double var6 = Math.PI * 4.0 / 9.0;
                  return var2 == 0.0
                     ? 0.0
                     : (
                        var2 == 1.0
                           ? 1.0
                           : (
                              var2 < 0.5
                                 ? -(Math.pow(2.0, 20.0 * var2 - 10.0) * Math.sin((20.0 * var2 - 11.125) * var6)) / 2.0
                                 : Math.pow(2.0, -20.0 * var2 + 10.0) * Math.sin((20.0 * var2 - 11.125) * var6) / 2.0 + 1.0
                           )
                     );
               case "easeInBounce":
                  return 1.0 - a(1.0 - var2);
               case "easeOutBounce":
                  return a(var2);
               case "easeInOutBounce":
                  return var2 < 0.5 ? (1.0 - a(1.0 - 2.0 * var2)) / 2.0 : (1.0 + a(2.0 * var2 - 1.0)) / 2.0;
               default:
                  return var2;
            }
         }
      }

      private static double a(double[] var0, double var1) {
         return var0 != null && var0.length > 0 ? var0[0] : var1;
      }

      private static double a(double var0) {
         double var2 = 7.5625;
         double var4 = 2.75;
         if (var0 < 1.0 / var4) {
            return var2 * var0 * var0;
         } else if (var0 < 2.0 / var4) {
            double var8;
            return var2 * (var8 = var0 - 1.5 / var4) * var8 + 0.75;
         } else {
            double var6;
            double var7;
            return var0 < 2.5 / var4 ? var2 * (var6 = var0 - 2.25 / var4) * var6 + 0.9375 : var2 * (var7 = var0 - 2.625 / var4) * var7 + 0.984375;
         }
      }
   }

   static final class c {
      final double time;
      final com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[] value;
      final String easing;
      final double[] easingArgs;

      c(double var1, com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a[] var3, String var4, double[] var5) {
         this.time = var1;
         this.value = var3;
         this.easing = var4;
         this.easingArgs = var5;
      }
   }
}
