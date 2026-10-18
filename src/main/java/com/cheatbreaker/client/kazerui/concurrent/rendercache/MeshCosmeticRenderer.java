package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

public final class MeshCosmeticRenderer {
   private static final Pattern PATH = Pattern.compile("cosmetics/models/(hats|bodywear)/([^/]+)/");
   private static final String[] CONDITIONS = new String[]{
      "world", "gui", "player", "first_person", "third_person", "helmet", "chestplate", "leggings", "boots", "skin_layer", "none"
   };
   private static Map<String, com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a> index;
   private static final Map<String, Object> MODELS = new HashMap<>();
   private static final com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a MISSING = new com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a();
   private static final ConcurrentMap<Integer, com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a> ENTRIES = new ConcurrentHashMap<>();
   private static final ExecutorService DECODE = Executors.newSingleThreadExecutor(var0 -> {
      Thread var1 = new Thread(var0, "Kazer mesh decode");
      var1.setDaemon(true);
      return var1;
   });

   private MeshCosmeticRenderer() {
   }

   private static synchronized Map<String, com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a> a() {
      if (index != null) {
         return index;
      } else {
         index = new HashMap<>();

         try (InputStreamReader var0 = new InputStreamReader(
               com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.class.getResourceAsStream("/assets/kazerui/cosmetics/models.json"), StandardCharsets.UTF_8
            )) {
            for (Entry var3 : new JsonParser().parse(var0).getAsJsonObject().entrySet()) {
               JsonObject var4 = ((JsonElement)var3.getValue()).getAsJsonObject();
               com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a var5 = new com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a();
               var5.name = (String)var3.getKey();
               var5.folder = var4.get("folder").getAsString();
               var5.modelPath = "cosmetics/models/" + var5.folder + "/" + var5.name + "/" + var5.name + ".obj";
               var5.head = var4.get("part").getAsString().equals("HEAD");
               var5.showWithArmor = var4.get("armor").getAsBoolean();

               for (JsonElement var7 : var4.getAsJsonArray("t")) {
                  a(var5.ops, var7.getAsJsonObject());
               }

               index.put(var5.name, var5);
            }
         } catch (Exception var18) {
            LogManager.getLogger("Kazer/Cosmetics").error("Could not read the model index", var18);
         }

         return index;
      }
   }

   static void a(List<com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b> var0, JsonObject var1) {
      char var2 = var1.get("transformType").getAsString().charAt(0);
      JsonObject var3 = var1.getAsJsonObject("values");
      boolean var4 = false;

      for (String var8 : CONDITIONS) {
         if (var3.has(var8)) {
            var4 = true;
            var0.add(a(var2, var8, var3.getAsJsonObject(var8)));
         }
      }

      if (!var4 && var3.has("x")) {
         var0.add(a(var2, "none", var3));
      }
   }

   private static com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b a(char var0, String var1, JsonObject var2) {
      return new com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b(
         var0,
         var1,
         var2.has("angle") ? var2.get("angle").getAsFloat() : 0.0F,
         var2.get("x").getAsFloat(),
         var2.get("y").getAsFloat(),
         var2.get("z").getAsFloat()
      );
   }

   static com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a a(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      if (var0.geckolib) {
         return null;
      } else {
         com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a var1 = ENTRIES.get(var0.id);
         if (var1 != null) {
            return var1 == MISSING ? null : var1;
         } else {
            Matcher var2 = PATH.matcher(var0.resource);
            com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a var3 = var2.find() ? a().get(var2.group(2)) : null;
            ENTRIES.putIfAbsent(var0.id, var3 == null ? MISSING : var3);
            return var3;
         }
      }
   }

   public static boolean b(com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var0) {
      return a(var0) != null;
   }

   private static com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh a(com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a var0) {
      String var1 = var0.modelPath;
      synchronized (MODELS) {
         Object var3 = MODELS.get(var1);
         if (var3 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh) {
            return (com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh)var3;
         } else if (var3 == Boolean.FALSE) {
            return null;
         } else if (var3 == null) {
            if (!com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.a(var1)) {
               MODELS.put(var1, Boolean.FALSE);
               return null;
            } else {
               if (MODELS.size() >= 32) {
                  Iterator var9 = MODELS.values().iterator();

                  while (var9.hasNext()) {
                     Object var11 = var9.next();
                     if (!(var11 instanceof Future) || ((Future)var11).isDone()) {
                        if (var11 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh) {
                           ((com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh)var11).b();
                        }

                        var9.remove();
                        break;
                     }
                  }

                  if (MODELS.size() >= 32) {
                     return null;
                  }
               }

               Future var10 = com.cheatbreaker.client.kazerui.concurrent.CosmeticAssetStore.b(var1);
               MODELS.put(var1, DECODE.submit(() -> {
                  byte[] var1x = (byte[])var10.get();
                  return var1x == null ? null : com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh.a(var1x);
               }));
               return null;
            }
         } else {
            Future var4 = (Future)var3;
            if (!var4.isDone()) {
               return null;
            } else {
               com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh var10000;
               try {
                  com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh var5 = (com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh)var4.get();
                  MODELS.put(var1, var5 == null ? Boolean.FALSE : var5);
                  var10000 = var5;
               } catch (Exception var7) {
                  LogManager.getLogger("Kazer/Cosmetics").warn("Could not load {}: {}", new Object[]{var1, var7.toString()});
                  MODELS.put(var1, Boolean.FALSE);
                  return null;
               }

               return var10000;
            }
         }
      }
   }

   static boolean a(String var0, EntityPlayer var1) {
      switch (var0) {
         case "player":
         case "third_person":
         case "none":
            return true;
         case "helmet":
            return var1.getCurrentArmor(3) != null;
         case "chestplate":
            return var1.getCurrentArmor(2) != null;
         case "leggings":
            return var1.getCurrentArmor(1) != null;
         case "boots":
            return var1.getCurrentArmor(0) != null;
         default:
            return false;
      }
   }

   static void a(List<com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b> var0, EntityPlayer var1) {
      for (com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b var3 : var0) {
         if (a(var3.condition, var1)) {
            switch (var3.kind) {
               case 'r':
                  GlStateManager.rotate(var3.angle, var3.x, var3.y, var3.z);
                  break;
               case 's':
                  GlStateManager.scale(var3.x, var3.y, var3.z);
                  break;
               case 't':
                  GlStateManager.translate(var3.x, var3.y, var3.z);
            }
         }
      }
   }

   static void a(EntityPlayer var0, ModelBiped var1, com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var2) {
      com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.a var3 = a(var2);
      if (var3 != null) {
         if (var3.showWithArmor || var0.getCurrentArmor(var3.head ? 3 : 2) == null) {
            com.cheatbreaker.client.kazerui.concurrent.rendercache.ObjMesh var4 = a(var3);
            if (var4 != null) {
               ResourceLocation var5 = com.cheatbreaker.client.kazerui.concurrent.CosmeticTextures.a(var2);
               if (var5 != null) {
                  GlStateManager.pushMatrix();
                  if (var0.isSneaking()) {
                     GlStateManager.translate(0.0F, 0.2F, 0.0F);
                  }

                  if (var3.head) {
                     var1.bipedHead.postRender(0.0625F);
                  } else {
                     var1.bipedBody.postRender(0.0625F);
                  }

                  if (var2.type.render == com.cheatbreaker.client.kazerui.concurrent.CosmeticType.a.HAT) {
                     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                  }

                  a(var3.ops, var0);
                  Minecraft.getMinecraft().getTextureManager().bindTexture(var5);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  GlStateManager.enableBlend();
                  GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                  GlStateManager.enableRescaleNormal();
                  GlStateManager.disableCull();
                  var4.a();
                  GlStateManager.enableCull();
                  GlStateManager.disableBlend();
                  GlStateManager.popMatrix();
               }
            }
         }
      }
   }

   static final class a {
      String name;
      String folder;
      String modelPath;
      boolean head;
      boolean showWithArmor;
      final List<com.cheatbreaker.client.kazerui.concurrent.rendercache.MeshCosmeticRenderer.b> ops = new ArrayList<>();
   }

   static final class b {
      final char kind;
      final String condition;
      final float angle;
      final float x;
      final float y;
      final float z;

      b(char var1, String var2, float var3, float var4, float var5, float var6) {
         this.kind = var1;
         this.condition = var2;
         this.angle = var3;
         this.x = var4;
         this.y = var5;
         this.z = var6;
      }
   }
}
