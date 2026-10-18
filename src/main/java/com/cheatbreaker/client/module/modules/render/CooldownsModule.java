package com.cheatbreaker.client.module.modules.render;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class CooldownsModule extends com.cheatbreaker.client.module.Module {
   private static final List<com.cheatbreaker.client.module.modules.render.CooldownEntry> real = new ArrayList<>();
   public final com.cheatbreaker.client.settings.Setting colorTheme;
   private final com.cheatbreaker.client.settings.Setting listMode;
   private final com.cheatbreaker.client.settings.Setting coloredColor;
   private final List<com.cheatbreaker.client.module.modules.render.CooldownEntry> fake = new ArrayList<>();

   public CooldownsModule() {
      super("Cooldowns");
      this.b(com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_BOTTOM);
      this.b(-154.99998F, -51.0F);
      this.colorTheme = new com.cheatbreaker.client.settings.Setting(this, "Color Theme").a("Colored").a("Bright", "Dark", "Colored");
      this.listMode = new com.cheatbreaker.client.settings.Setting(this, "List Mode").a("horizontal").a("vertical", "horizontal");
      this.coloredColor = new com.cheatbreaker.client.settings.Setting(this, "Colored color").a(-1).a(Integer.MIN_VALUE, Integer.MAX_VALUE);
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEditorEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::b);
      this.c(false);
   }

   public static void a(String var0, long var1, int var3) {
      for (com.cheatbreaker.client.module.modules.render.CooldownEntry var5 : real) {
         if (var5.c().equalsIgnoreCase(var0) && var5.e() == var3) {
            var5.b();
            var5.a(var1);
            return;
         }
      }

      real.add(new com.cheatbreaker.client.module.modules.render.CooldownEntry(var0, var3, var1));
   }

   public void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      if (!real.isEmpty()) {
         real.removeIf(com.cheatbreaker.client.module.modules.render.CooldownEntry::a);
      }

      if (!this.fake.isEmpty()) {
         this.fake.removeIf(com.cheatbreaker.client.module.modules.render.CooldownEntry::a);
      }
   }

   public void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         if (real.isEmpty()) {
            GL11.glPushMatrix();
            if (this.fake.isEmpty()) {
               this.fake.add(new com.cheatbreaker.client.module.modules.render.CooldownEntry("CombatTag", 283, 30000L));
               this.fake.add(new com.cheatbreaker.client.module.modules.render.CooldownEntry("EnderPearl", 368, 12000L));
            }

            this.a(var1.a());
            float var2 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
            GL11.glScalef(var2, var2, var2);
            boolean var3 = ((String)this.listMode.d()).equalsIgnoreCase("vertical");
            byte var4 = 36;
            byte var5 = 36;
            int var6 = var3 ? var4 : this.fake.size() * var4;
            int var7 = var3 ? this.fake.size() * var5 : var5;
            this.c((int)(var6 * var2), (int)(var7 * var2));

            for (int var8 = 0; var8 < this.fake.size(); var8++) {
               com.cheatbreaker.client.module.modules.render.CooldownEntry var9 = this.fake.get(var8);
               if (((String)this.listMode.d()).equalsIgnoreCase("vertical")) {
                  var9.a(this.colorTheme, this.width / 2.0F - var4 / 2, var8 * var5, this.coloredColor.a());
               } else {
                  var9.a(this.colorTheme, var8 * var4, 0.0F, this.coloredColor.a());
               }
            }

            GL11.glPopMatrix();
         }
      }
   }

   public void b(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      if (this.q()) {
         GL11.glPushMatrix();
         if (real.size() > 0) {
            this.a(var1.a());
            float var2 = 1.0F / com.cheatbreaker.client.CheatBreakerClient.N().e();
            GL11.glScalef(var2, var2, var2);
            boolean var3 = ((String)this.listMode.d()).equalsIgnoreCase("vertical");
            byte var4 = 36;
            byte var5 = 36;
            int var6 = var3 ? var4 : real.size() * var4;
            int var7 = var3 ? real.size() * var5 : var5;
            this.c((int)(var6 * var2), (int)(var7 * var2));

            for (int var8 = 0; var8 < real.size(); var8++) {
               com.cheatbreaker.client.module.modules.render.CooldownEntry var9 = real.get(var8);
               if (((String)this.listMode.d()).equalsIgnoreCase("vertical")) {
                  var9.a(this.colorTheme, this.width / 2.0F - var4 / 2, var8 * var5, this.coloredColor.a());
               } else {
                  var9.a(this.colorTheme, var8 * var4, 0.0F, this.coloredColor.a());
               }
            }
         } else if (!(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.HudEditorScreen)
            && !(this.minecraft.currentScreen instanceof com.cheatbreaker.client.gui.hudeditor.ModulePlacementScreen)) {
            this.c(50.0F, 24.0F);
            this.a(var1.a());
         }

         GL11.glPopMatrix();
      }
   }
}
