package cn.kazer.client189.animation;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;

public final class LegacyVisualsModule extends com.cheatbreaker.client.module.Module {
   public final com.cheatbreaker.client.settings.Setting animations;
   public final com.cheatbreaker.client.settings.Setting items;
   public final com.cheatbreaker.client.settings.Setting healthFlash;
   public final com.cheatbreaker.client.settings.Setting smoothSneaking;
   public final com.cheatbreaker.client.settings.Setting longSneaking;
   public final com.cheatbreaker.client.settings.Setting redArmor;
   public final com.cheatbreaker.client.settings.Setting blockHitting;
   public final com.cheatbreaker.client.settings.Setting itemTransforms;
   public final com.cheatbreaker.client.settings.Setting thirdPerson;
   public final com.cheatbreaker.client.settings.Setting swordBlock;
   public final com.cheatbreaker.client.settings.Setting bows;
   public final com.cheatbreaker.client.settings.Setting rods;
   public final com.cheatbreaker.client.settings.Setting consuming;
   public final com.cheatbreaker.client.settings.Setting punching;
   public final com.cheatbreaker.client.settings.Setting itemSwitch;
   private boolean loading;

   public LegacyVisualsModule() {
      super("1.7 Visuals");
      this.c(true);
      this.g(false);
      this.isEditable = false;
      this.animations = this.a("Animations", null);
      this.smoothSneaking = this.a("Smooth Sneaking", this.animations);
      this.longSneaking = this.a("Longer Unsneak", this.animations);
      this.redArmor = this.a("Red Armor", this.animations);
      this.healthFlash = this.a("Remove Health Bar Flashing", this.animations);
      this.items = this.a("Items", null);
      this.itemTransforms = this.a("1.7 Item Positions", this.items);
      this.bows = this.a("1.7 Bow Pullback", this.items);
      this.swordBlock = this.a("1.7 Block Animation", this.items);
      this.rods = this.a("1.7 Rod Position", this.items);
      this.thirdPerson = this.a("1.7 3rd Person Block Animation", this.items);
      this.consuming = this.a("Consume Animation", this.items);
      this.blockHitting = this.a("Block-Hitting Animation", this.items);
      this.punching = this.a("Punching During Usage", this.items);
      this.itemSwitch = this.a("Item Switching Animation", this.items);
      cn.kazer.client189.animation.LegacyVisualsFlags.a(this);
   }

   private com.cheatbreaker.client.settings.Setting a(String var1, com.cheatbreaker.client.settings.Setting var2) {
      com.cheatbreaker.client.settings.Setting var3 = new com.cheatbreaker.client.settings.Setting(this, var1).a(true).a(var1x -> this.z());
      return var2 == null ? var3 : var3.a(var2);
   }

   public boolean a(com.cheatbreaker.client.settings.Setting var1) {
      return this.g() && var1.j() && Boolean.TRUE.equals(var1.d());
   }

   @Override
   public void a(boolean var1) {
      super.a(var1);
      cn.kazer.client189.animation.OldAnimationsToggle.a(var1);
      this.z();
   }

   public static String a(String var0) {
      switch (var0) {
         case "1.7 Item Positions":
            return "Item Transforms";
         case "1.7 Bow Pullback":
            return "Bows";
         case "1.7 Block Animation":
            return "Swords";
         case "1.7 Rod Position":
            return "Fishing Rods";
         case "1.7 3rd Person Block Animation":
            return "Third Person Held Items";
         case "Consume Animation":
            return "Food";
         case "Block-Hitting Animation":
            return "Block Hitting";
         case "Punching During Usage":
            return "Use Item While Digging";
         case "Smooth Sneaking":
            return "Sneaking";
         default:
            return null;
      }
   }

   public static boolean b(String var0) {
      return "1.7".equals(var0) || Boolean.parseBoolean(var0);
   }

   public void a() {
      this.loading = true;

      try {
         cn.kazer.client189.config.IniConfigFile var1 = cn.kazer.client189.config.IniConfigFile.a(cn.kazer.client189.config.ClientConfigPaths.c(this.minecraft.mcDataDir.toPath()));
         String var2 = var1.a("", "BlockHitAnimation");
         this.a(var2 == null || Boolean.parseBoolean(var2));

         for (com.cheatbreaker.client.settings.Setting var4 : this.m()) {
            if (var4 != this.scale) {
               var2 = var1.a("", var4.f());
               String var5 = a(var4.f());
               if (var2 == null && var5 != null) {
                  var2 = var1.a("", var5);
               }

               if (var2 == null && var4 == this.thirdPerson) {
                  var2 = var1.a("", "ThirdPerson");
               }

               if (var2 != null) {
                  var4.a(b(var2), false);
               }
            }
         }
      } catch (IOException var9) {
         LogManager.getLogger().warn("Could not read animation preferences", var9);
      } finally {
         this.loading = false;
         cn.kazer.client189.animation.LegacyVisualsFlags.a(this);
      }
   }

   private void z() {
      if (this.itemSwitch != null) {
         cn.kazer.client189.animation.LegacyVisualsFlags.a(this);
         if (!this.loading) {
            try {
               Path var1 = cn.kazer.client189.config.ClientConfigPaths.c(this.minecraft.mcDataDir.toPath());
               cn.kazer.client189.config.IniConfigFile var2 = cn.kazer.client189.config.IniConfigFile.a(var1);
               var2.a("", "BlockHitAnimation", String.valueOf(this.g()));

               for (com.cheatbreaker.client.settings.Setting var4 : this.m()) {
                  if (var4 != this.scale) {
                     var2.a("", var4.f(), String.valueOf(var4.d()));
                  }
               }

               var2.b(var1);
            } catch (IOException var5) {
               LogManager.getLogger().warn("Could not save animation preferences", var5);
            }
         }
      }
   }
}
