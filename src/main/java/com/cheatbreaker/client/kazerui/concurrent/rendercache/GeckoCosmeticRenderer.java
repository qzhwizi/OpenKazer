package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

public final class GeckoCosmeticRenderer {
   private static final ThreadPoolExecutor LOAD = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8), var0 -> {
      Thread var1 = new Thread(var0, "Kazer gecko load");
      var1.setDaemon(true);
      return var1;
   }, new AbortPolicy());
   private static final Map<String, Future<GeckoCosmeticRenderer.a>> DEFINITIONS = new LinkedHashMap<>(32, 0.75F, true);
   private static final Map<String, Long> RETRY = new LinkedHashMap<>(32, 0.75F, true);
   private static final Map<EntityPlayer, Map<Integer, GeckoCosmeticRenderer.b>> STATES = new WeakHashMap<>();
   private static final Map<EntityPlayer, Map<Integer, GeckoCosmeticRenderer.b>> GUI_STATES = new WeakHashMap<>();
   private static final long PREVIEW_START = System.nanoTime();
   private static com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a constants;
   private static com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a petConstants;
   private static boolean molangLoaded;

   private GeckoCosmeticRenderer() {
   }

   public static boolean a(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      return var0.geckolib && com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.a(var0.resource);
   }

   private static String b(String var0) {
      return var0.startsWith("lunar:") ? var0.substring(6) : var0;
   }

   static GeckoCosmeticRenderer.a b(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      Future var1;
      synchronized (DEFINITIONS) {
         Long var3 = RETRY.get(var0.resource);
         if (var3 != null) {
            if (System.nanoTime() < var3) {
               return null;
            }

            RETRY.remove(var0.resource);
         }

         var1 = DEFINITIONS.get(var0.resource);
         if (var1 == null) {
            if (DEFINITIONS.size() >= 32) {
               Iterator var4 = DEFINITIONS.values().iterator();

               while (var4.hasNext()) {
                  Future var5 = (Future)var4.next();
                  if (var5.isDone()) {
                     try {
                        GeckoCosmeticRenderer.a var6 = (GeckoCosmeticRenderer.a)var5.get();
                        if (var6 != null) {
                           for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var8 : var6.models) {
                              var8.d();
                           }
                        }
                     } catch (Exception var14) {
                     }

                     var4.remove();
                     break;
                  }
               }

               if (DEFINITIONS.size() >= 32) {
                  return null;
               }
            }

            try {
               var1 = LOAD.submit(() -> c(var0.resource));
            } catch (RejectedExecutionException var13) {
               return null;
            }

            DEFINITIONS.put(var0.resource, var1);
         }
      }

      if (!var1.isDone()) {
         return null;
      } else {
         try {
            GeckoCosmeticRenderer.a var16 = (GeckoCosmeticRenderer.a)var1.get();
            if (var16 != null) {
               return var16;
            }
         } catch (Exception var12) {
         }

         synchronized (DEFINITIONS) {
            if (DEFINITIONS.get(var0.resource) == var1) {
               DEFINITIONS.remove(var0.resource);
            }

            if (RETRY.size() >= 32) {
               RETRY.remove(RETRY.keySet().iterator().next());
            }

            RETRY.put(var0.resource, System.nanoTime() + 30000000000L);
            return null;
         }
      }
   }

   private static GeckoCosmeticRenderer.a c(String var0) {
      try {
         JsonObject var1 = a(com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(var0).get());
         if (var1 == null) {
            return null;
         } else {
            GeckoCosmeticRenderer.a var2 = new GeckoCosmeticRenderer.a();
            var2.type = var1.has("type") ? var1.get("type").getAsString() : "";
            var2.texture = b(var1.get("texture").getAsString());
            if (var1.has("attached_bone")) {
               var2.attached = var1.get("attached_bone").getAsString();
            }

            Future var3 = var1.has("animation") ? com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(b(var1.get("animation").getAsString())) : null;
            JsonElement var4 = var1.get("model");
            if (var4.isJsonObject()) {
               for (Entry var6 : var4.getAsJsonObject().entrySet()) {
                  var2.models.add(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a(a(com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(b((String)var6.getKey())).get())));
                  var2.modelConditions.add(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(((JsonElement)var6.getValue()).getAsString()));
               }
            } else {
               var2.models.add(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a(a(com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(b(var4.getAsString())).get())));
               var2.modelConditions.add(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(1.0));
            }

            JsonObject var12 = var3 == null ? null : a((byte[])var3.get());
            var2.animations = var12 == null ? Collections.emptyMap() : com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation.a(var12);
            if (var1.has("transformations") && var1.get("transformations").isJsonArray()) {
               for (JsonElement var7 : var1.getAsJsonArray("transformations")) {
                  if (var7.isJsonObject() && var7.getAsJsonObject().has("transformType")) {
                     com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a(var2.ops, var7.getAsJsonObject());
                  }
               }
            }

            var2.offsetWithChestplate = a(var1, "offset_with_chestplate");
            var2.hideHead = a(var1, "hide_head");
            var2.hideBody = a(var1, "hide_body");
            var2.hideRightArm = a(var1, "hide_right_arm");
            var2.hideLeftArm = a(var1, "hide_left_arm");
            var2.hideRightLeg = a(var1, "hide_right_leg");
            var2.hideLeftLeg = a(var1, "hide_left_leg");
            a(var2, var1);

            for (List var16 : var2.controllers) {
               ArrayList var8 = new ArrayList(var16.size());

               for (String[] var10 : (Iterable<String[]>) var16) {
                  var8.add(com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(var10[1]));
               }

               var2.controllerConditions.add(var8);
            }

            if ("NONE".equals(var2.attached)) {
               for (com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var17 : var2.models) {
                  var2.coversHead = var2.coversHead | var17.a("armorHead");
                  var2.coversBody = var2.coversBody | var17.a("armorBody");
                  var2.coversRightArm = var2.coversRightArm | var17.a("armorRightArm");
                  var2.coversLeftArm = var2.coversLeftArm | var17.a("armorLeftArm");
                  var2.coversRightLeg = var2.coversRightLeg | (var17.a("armorRightLeg") || var17.a("armorRightBoot"));
                  var2.coversLeftLeg = var2.coversLeftLeg | (var17.a("armorLeftLeg") || var17.a("armorLeftBoot"));
               }
            }

            return var2;
         }
      } catch (Exception var11) {
         LogManager.getLogger("Kazer/Cosmetics").warn("Could not load {}: {}", new Object[]{var0, var11.toString()});
         return null;
      }
   }

   private static boolean a(JsonObject var0, String var1) {
      return var0.has(var1) && var0.get(var1).isJsonPrimitive() && var0.get(var1).getAsBoolean();
   }

   private static JsonObject a(byte[] var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = new String(var0, StandardCharsets.UTF_8);
         if (!var1.isEmpty() && var1.charAt(0) == '\ufeff') {
            var1 = var1.substring(1);
         }

         return new JsonParser().parse(var1).getAsJsonObject();
      }
   }

   private static void a(GeckoCosmeticRenderer.a var0, JsonObject var1) {
      JsonObject var2 = var1.has("state_machine") && var1.get("state_machine").isJsonObject() ? var1.getAsJsonObject("state_machine") : null;
      if (var2 != null && var2.has("controllers")) {
         for (JsonElement var11 : var2.getAsJsonArray("controllers")) {
            ArrayList var12 = new ArrayList();
            JsonArray var6 = var11.getAsJsonObject().getAsJsonArray("states");
            if (var6 != null) {
               for (JsonElement var8 : var6) {
                  JsonObject var9 = var8.getAsJsonObject();
                  if (var9.has("anim")) {
                     var12.add(new String[]{var9.get("anim").getAsString(), var9.has("plays_when") ? var9.get("plays_when").getAsString() : "1"});
                  }
               }
            }

            var0.controllers.add(var12);
         }
      } else if (var2 != null && var2.has("anim")) {
         var0.controllers.add(Collections.singletonList(new String[]{var2.get("anim").getAsString(), "1"}));
      } else if (var1.has("default_anim") && var1.get("default_anim").isJsonObject()) {
         ArrayList var3 = new ArrayList();

         for (Entry var5 : var1.getAsJsonObject("default_anim").entrySet()) {
            var3.add(new String[]{(String)var5.getKey(), ((JsonElement)var5.getValue()).getAsString()});
         }

         var0.controllers.add(var3);
      }
   }

   private static synchronized void a() {
      if (!molangLoaded) {
         molangLoaded = true;
         com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.b(d("functions.molang"));
         String var0 = d("constants.molang");
         constants = com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(var0);
         petConstants = com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a(a(var0));
      }
   }

   static String a(String var0) {
      return var0.replace(
            "math.lerp(c.entity_y_rotation_smoothed, q.entity_y_rotation, 0.05)", "math.lerprotate(c.entity_y_rotation_smoothed, q.entity_y_rotation, 0.05)"
         )
         .replace("c.entity_y_rotation_smoothed - q.entity_y_rotation;", "math.min_angle(c.entity_y_rotation_smoothed - q.entity_y_rotation);")
         .replace("c.last_head_y_rotation - q.head_y_rotation;", "math.min_angle(c.last_head_y_rotation - q.head_y_rotation);");
   }

   private static String d(String var0) {
      try (InputStream var1 = GeckoCosmeticRenderer.class.getResourceAsStream("/assets/kazerui/cosmetics/" + var0)) {
         if (var1 != null) {
            ByteArrayOutputStream var19 = new ByteArrayOutputStream();
            byte[] var4 = new byte[4096];

            int var5;
            while ((var5 = var1.read(var4)) > 0) {
               var19.write(var4, 0, var5);
            }

            return new String(var19.toByteArray(), StandardCharsets.UTF_8);
         } else {
            return "";
         }
      } catch (Exception var18) {
         return "";
      }
   }

   private static double a(EntityPlayer var0, float var1) {
      return var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer ? (System.nanoTime() - PREVIEW_START) / 1.0E9 : ((double)var0.ticksExisted + var1) / 20.0;
   }

   private static double b(String var0, EntityPlayer var1, float var2, com.cheatbreaker.client.kazerui.concurrent.rendercache.MotionInterpolator var3, boolean var4) {
      boolean var5 = var4 || var1 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer;
      double var6 = (var1.posX - var1.prevPosX) * 20.0;
      double var8 = (var1.posY - var1.prevPosY) * 20.0;
      double var10 = (var1.posZ - var1.prevPosZ) * 20.0;
      if (var5) {
         var10 = 0.0;
         var8 = 0.0;
         var6 = 0.0;
      } else if (var3 != null) {
         var6 = var3.a(var2);
         var8 = var3.b(var2);
         var10 = var3.c(var2);
      }

      float var12 = var3 == null ? var1.renderYawOffset : com.cheatbreaker.client.kazerui.concurrent.rendercache.MotionInterpolator.a(var1.prevRenderYawOffset, var1.renderYawOffset, var2);
      float var13 = var12 * (float) Math.PI / 180.0F;
      switch (var0) {
         case "life_time":
            return a(var1, var2);
         case "is_in_gui":
         case "is_in_preview_model":
            return var5 ? 1.0 : 0.0;
         case "is_crouching":
         case "is_sneaking":
            return var1.isSneaking() ? 1.0 : 0.0;
         case "is_sprinting":
         case "is_running":
            return var1.isSprinting() ? 1.0 : 0.0;
         case "is_on_ground":
            return !var1.onGround && !var5 ? 0.0 : 1.0;
         case "is_in_water":
            return var1.isInWater() ? 1.0 : 0.0;
         case "is_flying":
            return var1.capabilities.isFlying ? 1.0 : 0.0;
         case "ground_speed":
            return Math.sqrt(var6 * var6 + var10 * var10);
         case "is_moving":
            return var6 * var6 + var10 * var10 > 1.0E-4 ? 1.0 : 0.0;
         case "x_velocity":
            return var6;
         case "y_velocity":
         case "local_y_velocity":
            return var8;
         case "z_velocity":
            return var10;
         case "local_z_velocity":
            return -var6 * MathHelper.sin(var13) + var10 * MathHelper.cos(var13);
         case "local_x_velocity":
            return var6 * MathHelper.cos(var13) + var10 * MathHelper.sin(var13);
         case "head_y_rotation":
            return var5
               ? 0.0
               : MathHelper.wrapAngleTo180_float(
                  (var3 == null ? var1.rotationYawHead : com.cheatbreaker.client.kazerui.concurrent.rendercache.MotionInterpolator.a(var1.prevRotationYawHead, var1.rotationYawHead, var2)) - var12
               );
         case "head_x_rotation":
            return var5 ? 0.0 : (var3 == null ? var1.rotationPitch : var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var2);
         case "body_y_rotation":
            return var5 ? 0.0 : var12;
         case "entity_y_rotation":
            return var5 ? 0.0 : (var3 == null ? var1.rotationYaw : com.cheatbreaker.client.kazerui.concurrent.rendercache.MotionInterpolator.a(var1.prevRotationYaw, var1.rotationYaw, var2));
         case "is_first_person":
            return 0.0;
         case "is_slim_model":
            return "slim".equals(((AbstractClientPlayer)var1).getSkinType()) ? 1.0 : 0.0;
         default:
            return 0.0;
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel a(EntityPlayer var0, GeckoCosmeticRenderer.a var1, com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var2, float var3) {
      a();
      boolean var4 = var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer || !Minecraft.getMinecraft().getRenderManager().isRenderShadow();
      Map var5 = var4 ? GUI_STATES : STATES;
      Map var6 = (Map)var5.get(var0);
      if (var6 == null) {
         var6 = new LinkedHashMap(8, 0.75F, true);
         var5.put(var0, var6);
      }

      GeckoCosmeticRenderer.b var7 = (GeckoCosmeticRenderer.b)var6.get(var2.id);
      if (var7 == null) {
         if (var6.size() >= 8) {
            var6.remove(var6.keySet().iterator().next());
         }

         var7 = new GeckoCosmeticRenderer.b(
            var1.controllers.size(),
            var0,
            var2.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.PET || var2.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.COMPANION,
            var4
         );
         var6.put(var2.id, var7);
      }

      var7.partialTicks = Math.max(0.0F, Math.min(1.0F, var3));
      if (var7.motion != null) {
         var7.motion
            .a(
               var0.ticksExisted,
               (var0.posX - var0.prevPosX) * 20.0,
               (var0.posY - var0.prevPosY) * 20.0,
               (var0.posZ - var0.prevPosZ) * 20.0
            );
      }

      double var8 = a(var0, var7.partialTicks);
      if (var7.clock != null) {
         var8 = var7.clock.a(var8);
      }

      if (var7.clock == null || var7.clock.a()) {
         var7.scope.interpolationSeconds = var7.clock == null ? -1.0 : var7.clock.b();

         try {
            (var7.clock == null ? constants : petConstants).eval(var7.scope);
         } catch (RuntimeException var24) {
         } finally {
            var7.scope.interpolationSeconds = -1.0;
         }
      }

      com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var10 = var1.models.get(0);

      for (int var11 = 0; var11 < var1.models.size(); var11++) {
         if (var1.modelConditions.get(var11).eval(var7.scope) != 0.0) {
            var10 = var1.models.get(var11);
            break;
         }
      }

      var10.b();

      for (int var26 = 0; var26 < var1.controllers.size(); var26++) {
         String var12 = null;

         for (int var13 = 0; var13 < var1.controllers.get(var26).size(); var13++) {
            String[] var14 = var1.controllers.get(var26).get(var13);

            double var15;
            try {
               var15 = var1.controllerConditions.get(var26).get(var13).eval(var7.scope);
            } catch (RuntimeException var22) {
               var15 = 0.0;
            }

            if (var15 != 0.0) {
               var12 = var14[0];
               break;
            }
         }

         if (var12 != null) {
            if (!var12.equals(var7.playing[var26])) {
               var7.playing[var26] = var12;
               var7.started[var26] = var8;
            }

            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation var27 = var1.animations.get(var12);
            if (var27 != null) {
               try {
                  var27.a(var10, var7.scope, var8 - var7.started[var26]);
               } catch (RuntimeException var23) {
               }
            }
         }
      }

      return var10;
   }

   private static void a(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var0, ResourceLocation var1) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(var1);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.alphaFunc(516, 0.003F);
      GlStateManager.enableRescaleNormal();
      var0.c();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.disableBlend();
   }

   public static boolean a(EntityPlayer var0, com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var1, float var2, float var3, float var4, float var5) {
      GeckoCosmeticRenderer.a var6 = b(var1);
      if (var6 != null && !var6.models.isEmpty()) {
         ResourceLocation var7 = com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a(var6.texture, false);
         if (var7 == null) {
            return false;
         } else {
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var8 = a(var0, var6, var1, 0.0F);
            float[] var9 = var8.a();
            float var10 = Math.max(var9[3] - var9[0], Math.max(var9[4] - var9[1], var9[5] - var9[2]));
            if (var10 <= 0.0F) {
               return false;
            } else {
               float var11 = Math.min(var4, var5) * 0.62F / var10;
               GlStateManager.pushMatrix();
               GlStateManager.enableDepth();
               GlStateManager.enableColorMaterial();
               GlStateManager.translate(var2 + var4 / 2.0F, var3 + var5 / 2.0F, 150.0F);
               GlStateManager.scale(var11, -var11, var11);
               GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(215.0F, 0.0F, 1.0F, 0.0F);
               GlStateManager.translate(-(var9[0] + var9[3]) / 2.0F, -(var9[1] + var9[4]) / 2.0F, -(var9[2] + var9[5]) / 2.0F);
               RenderHelper.enableStandardItemLighting();
               a(var8, var7);
               RenderHelper.disableStandardItemLighting();
               GlStateManager.disableRescaleNormal();
               GlStateManager.popMatrix();
               return true;
            }
         }
      } else {
         return false;
      }
   }

   static void a(EntityPlayer var0, ModelBiped var1, com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var2, float var3) {
      boolean var4 = var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer;
      GeckoCosmeticRenderer.a var5 = b(var2);
      if (var5 != null && !var5.models.isEmpty()) {
         ResourceLocation var6 = com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a(var5.texture, false);
         if (var6 != null) {
            com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var7 = a(var0, var5, var2, var3);
            GlStateManager.pushMatrix();
            if (var2.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.COMPANION || var4 && var2.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.PET) {
               GlStateManager.translate(var4 ? 0.45F : 0.8F, var4 && var2.type == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.PET ? -0.2F : 0.0F, 0.0F);
            }

            if ("NONE".equals(var5.attached)) {
               a(var7, var1);
            }

            if (var0.isSneaking()) {
               GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            ModelRenderer var8 = a(var1, var5.attached);
            if (var8 != null) {
               var8.postRender(0.0625F);
            }

            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, -1.51F, 0.0F);
            if (var5.offsetWithChestplate && var0.getCurrentArmor(2) != null) {
               GlStateManager.translate(0.0F, 0.065F, 0.0F);
            }

            com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a(var5.ops, var0);
            a(var7, var6);
            GlStateManager.popMatrix();
         }
      }
   }

   private static ModelRenderer a(ModelBiped var0, String var1) {
      switch (var1) {
         case "HEAD":
            return var0.bipedHead;
         case "SHOULDER":
            return var0.bipedBody;
         case "LEFT_ARM":
            return var0.bipedLeftArm;
         case "RIGHT_ARM":
            return var0.bipedRightArm;
         case "LEFT_LEG":
            return var0.bipedLeftLeg;
         case "RIGHT_LEG":
            return var0.bipedRightLeg;
         default:
            return null;
      }
   }

   private static void a(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel var0, ModelBiped var1) {
      a(var0.bones.get("armorHead"), var1.bipedHead, 0.0F, 0.0F);
      a(var0.bones.get("armorBody"), var1.bipedBody, 0.0F, 0.0F);
      a(var0.bones.get("armorRightArm"), var1.bipedRightArm, 5.0F, 2.0F);
      a(var0.bones.get("armorLeftArm"), var1.bipedLeftArm, -5.0F, 2.0F);
      a(var0.bones.get("armorRightLeg"), var1.bipedRightLeg, 2.0F, 12.0F);
      a(var0.bones.get("armorRightBoot"), var1.bipedRightLeg, 2.0F, 12.0F);
      a(var0.bones.get("armorLeftLeg"), var1.bipedLeftLeg, -2.0F, 12.0F);
      a(var0.bones.get("armorLeftBoot"), var1.bipedLeftLeg, -2.0F, 12.0F);
   }

   private static void a(com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel.a var0, ModelRenderer var1, float var2, float var3) {
      if (var0 != null) {
         var0.rotX = -var1.rotateAngleX;
         var0.rotY = -var1.rotateAngleY;
         var0.rotZ = var1.rotateAngleZ;
         var0.posX = var1.rotationPointX + var2;
         var0.posY = var3 - var1.rotationPointY;
         var0.posZ = var1.rotationPointZ;
      }
   }

   public static void a(EntityPlayer var0, ModelPlayer var1, List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> var2) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;

      for (com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var10 : var2) {
         if (var10.geckolib) {
            GeckoCosmeticRenderer.a var11 = b(var10);
            if (var11 != null) {
               var3 |= var11.hideHead;
               var4 |= var11.hideBody;
               var5 |= var11.hideRightArm;
               var6 |= var11.hideLeftArm;
               var7 |= var11.hideRightLeg;
               var8 |= var11.hideLeftLeg;
               if (var11.coversHead) {
                  var1.bipedHeadwear.showModel = false;
               }

               if (var11.coversBody) {
                  var1.bipedBodyWear.showModel = false;
               }

               if (var11.coversRightArm) {
                  var1.bipedRightArmwear.showModel = false;
               }

               if (var11.coversLeftArm) {
                  var1.bipedLeftArmwear.showModel = false;
               }

               if (var11.coversRightLeg) {
                  var1.bipedRightLegwear.showModel = false;
               }

               if (var11.coversLeftLeg) {
                  var1.bipedLeftLegwear.showModel = false;
               }
            }
         }
      }

      if (var3) {
         var1.bipedHead.showModel = false;
         var1.bipedHeadwear.showModel = false;
      }

      if (var4) {
         var1.bipedBody.showModel = false;
         var1.bipedBodyWear.showModel = false;
      }

      if (var5) {
         var1.bipedRightArm.showModel = false;
         var1.bipedRightArmwear.showModel = false;
      }

      if (var6) {
         var1.bipedLeftArm.showModel = false;
         var1.bipedLeftArmwear.showModel = false;
      }

      if (var7) {
         var1.bipedRightLeg.showModel = false;
         var1.bipedRightLegwear.showModel = false;
      }

      if (var8) {
         var1.bipedLeftLeg.showModel = false;
         var1.bipedLeftLegwear.showModel = false;
      }
   }

   public static int a(ModelBiped var0) {
      int var1 = (var0.bipedHead.showModel ? 1 : 0)
         | (var0.bipedBody.showModel ? 2 : 0)
         | (var0.bipedRightArm.showModel ? 4 : 0)
         | (var0.bipedLeftArm.showModel ? 8 : 0)
         | (var0.bipedRightLeg.showModel ? 16 : 0)
         | (var0.bipedLeftLeg.showModel ? 32 : 0)
         | (var0.bipedHeadwear.showModel ? 64 : 0);
      if (var0 instanceof ModelPlayer) {
         ModelPlayer var2 = (ModelPlayer)var0;
         var1 |= (var2.bipedBodyWear.showModel ? 128 : 0)
            | (var2.bipedRightArmwear.showModel ? 256 : 0)
            | (var2.bipedLeftArmwear.showModel ? 512 : 0)
            | (var2.bipedRightLegwear.showModel ? 1024 : 0)
            | (var2.bipedLeftLegwear.showModel ? 2048 : 0);
      }

      return var1;
   }

   public static void a(ModelBiped var0, int var1) {
      var0.bipedHead.showModel = (var1 & 1) != 0;
      var0.bipedBody.showModel = (var1 & 2) != 0;
      var0.bipedRightArm.showModel = (var1 & 4) != 0;
      var0.bipedLeftArm.showModel = (var1 & 8) != 0;
      var0.bipedRightLeg.showModel = (var1 & 16) != 0;
      var0.bipedLeftLeg.showModel = (var1 & 32) != 0;
      var0.bipedHeadwear.showModel = (var1 & 64) != 0;
      if (var0 instanceof ModelPlayer) {
         ModelPlayer var2 = (ModelPlayer)var0;
         var2.bipedBodyWear.showModel = (var1 & 128) != 0;
         var2.bipedRightArmwear.showModel = (var1 & 256) != 0;
         var2.bipedLeftArmwear.showModel = (var1 & 512) != 0;
         var2.bipedRightLegwear.showModel = (var1 & 1024) != 0;
         var2.bipedLeftLegwear.showModel = (var1 & 2048) != 0;
      }
   }

   static final class a {
      final List<com.cheatbreaker.client.kazerui.concurrent.easing.BedrockModel> models = new ArrayList<>();
      final List<com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a> modelConditions = new ArrayList<>();
      Map<String, com.cheatbreaker.client.kazerui.concurrent.easing.BedrockAnimation> animations;
      String texture;
      String type;
      String attached = "NONE";
      final List<com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b> ops = new ArrayList<>();
      boolean coversHead;
      boolean coversBody;
      boolean coversRightArm;
      boolean coversLeftArm;
      boolean coversRightLeg;
      boolean coversLeftLeg;
      boolean offsetWithChestplate;
      boolean hideHead;
      boolean hideBody;
      boolean hideRightArm;
      boolean hideLeftArm;
      boolean hideRightLeg;
      boolean hideLeftLeg;
      final List<List<String[]>> controllers = new ArrayList<>();
      final List<List<com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.a>> controllerConditions = new ArrayList<>();
   }

   static final class b {
      final com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f scope = new com.cheatbreaker.client.kazerui.concurrent.easing.MolangParser.f();
      final String[] playing;
      final double[] started;
      final WeakReference<EntityPlayer> wearer;
      float partialTicks;
      final com.cheatbreaker.client.kazerui.concurrent.rendercache.MotionInterpolator motion;
      final com.cheatbreaker.client.kazerui.concurrent.rendercache.AnimationClock clock;
      final boolean gui;

      b(int var1, EntityPlayer var2, boolean var3, boolean var4) {
         this.gui = var4;
         this.clock = var3 ? new com.cheatbreaker.client.kazerui.concurrent.rendercache.AnimationClock() : null;
         this.motion = var3 ? new com.cheatbreaker.client.kazerui.concurrent.rendercache.MotionInterpolator() : null;
         this.playing = new String[var1];
         this.started = new double[var1];
         this.wearer = new WeakReference<>(var2);
         this.scope.queries = var2x -> {
            EntityPlayer var3x = this.wearer.get();
            return var3x == null
               ? 0.0
               : ("life_time".equals(var2x) && this.clock != null ? this.clock.c() : GeckoCosmeticRenderer.b(var2x, var3x, this.partialTicks, this.motion, var4));
         };
      }
   }
}
