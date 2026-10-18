package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public final class ToggleSneakHudModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static final ResourceLocation SPRINTING = new ResourceLocation("kazerui", "textures/hud/sprinting.png");
   private static final ResourceLocation SNEAKING = new ResourceLocation("kazerui", "textures/hud/sneaking.png");
   private final com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule parent;
   private final DecimalFormat boostFormat = new DecimalFormat("#.00");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting iconMode = this.a("iconMode", false);
   private final com.cheatbreaker.client.kazerui.module.settings.StringSetting sprintingText = this.c("sprintingText");
   private final com.cheatbreaker.client.kazerui.module.settings.StringSetting sneakingText = this.c("sneakingText");
   private final com.cheatbreaker.client.kazerui.module.settings.StringSetting flyingText = this.c("flyingText");
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showRidingText = this.a("showRidingText", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting descendingText = this.a("descendingText", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting showWhileTyping = this.a("showWhileTyping", true);
   private final com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.a hud;
   private int stateFlags = Integer.MIN_VALUE;
   private long stateRevision = Long.MIN_VALUE;
   private String cachedState = "";

   ToggleSneakHudModule(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule var1) {
      super("TOGGLE_SNEAK_HUD_CHILD", true);
      this.parent = var1;
      this.hud = this.a(new com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.a());
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(
         "generalOptions",
         var1x -> var1x.a(this.iconMode, this.sprintingText, this.sneakingText, this.flyingText, this.showRidingText, this.descendingText, this.showWhileTyping)
      );
   }

   private String a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b var1) {
      if (!this.sprintingText.b() && "sprinting".equals(var1.main)) {
         return this.sprintingText.j();
      } else if (!this.sneakingText.b() && "sneaking".equals(var1.main)) {
         return this.sneakingText.j();
      } else {
         return !this.flyingText.b() && "flying".equals(var1.main) ? this.flyingText.j() : com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var1.main);
      }
   }

   private String a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b var1, Object... var2) {
      return var1.partition != null && var2 != null && (!var1.extraArguments || var2.length != 0)
         ? com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var1.partition, var2)
         : null;
   }

   private String b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b var1, Object... var2) {
      return this.a(var1, null, var2);
   }

   private String a(
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b var1, com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b var2, Object... var3
   ) {
      if (this.iconMode.b()) {
         switch (var1) {
            case FLYING_BOOST:
            case SPRINTING_VANILLA:
               var1 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.VANILLA;
               break;
            case SNEAKING_TOGGLED:
            case SPRINTING_TOGGLED:
               var1 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.TOGGLED;
               break;
            case SNEAKING_HELD:
            case SPRINTING_HELD:
               var1 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.HELD;
         }
      }

      if (this.descendingText.b()
         || var1 != com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.DESCENDING
            && var1 != com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.DISMOUNTING) {
         if (this.hud.background.b()) {
            return this.a(var1);
         } else {
            StringBuilder var4 = new StringBuilder();
            if (var2 != null) {
               var4.append(this.b(var2, var3)).append(" ");
            }

            var4.append(this.a(var1));
            String var5 = this.a(var1, var3);
            if (var5 != null) {
               var4.append(" (").append(var5).append(")");
            }

            return var4.toString();
         }
      } else {
         return var2 == null ? "" : this.b(var2, var3);
      }
   }

   private String p() {
      Minecraft var1 = Minecraft.getMinecraft();
      EntityPlayerSP var2 = var1.thePlayer;
      GameSettings var3 = var1.gameSettings;
      int var4 = (var2.capabilities.isFlying ? 1 : 0)
         | (var2.isRiding() ? 2 : 0)
         | (var2.isSprinting() ? 4 : 0)
         | (com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule.a(var3.keyBindSneak) ? 8 : 0)
         | (com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule.a(var3.keyBindSprint) ? 16 : 0)
         | (var3.keyBindSneak.isKeyDown() ? 32 : 0)
         | (var3.keyBindSprint.isKeyDown() ? 64 : 0)
         | (this.parent.sprinting ? 128 : 0)
         | (this.parent.sneaking ? 256 : 0)
         | (this.parent.sprintHeld ? 512 : 0);
      long var5 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.d().revision;
      if (var4 != this.stateFlags || var5 != this.stateRevision) {
         this.cachedState = this.q();
         this.stateFlags = var4;
         this.stateRevision = var5;
      }

      return this.cachedState;
   }

   private String q() {
      Minecraft var1 = Minecraft.getMinecraft();
      EntityPlayerSP var2 = var1.thePlayer;
      GameSettings var3 = var1.gameSettings;
      String var4 = "";
      boolean var5 = var2.capabilities.isFlying;
      boolean var6 = var2.isRiding();
      boolean var7 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule.a(var3.keyBindSneak);
      boolean var8 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakModule.a(var3.keyBindSprint);
      com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b var9 = null;
      if (var5) {
         if (this.parent.flyBoost.b() && var8) {
            var9 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.FLYING_BOOST;
            var4 = var4 + this.b(var9, this.boostFormat.format(this.parent.flyBoostAmount.c()));
         } else {
            var9 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.FLYING;
            var4 = var4 + this.b(var9);
         }
      }

      if (var6 && this.showRidingText.b()) {
         var9 = com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.RIDING;
         var4 = this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.RIDING);
      }

      if (var3.keyBindSneak.isKeyDown()) {
         var4 = var5
            ? this.a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.DESCENDING, var9)
            : (
               var6 && var9 != null
                  ? this.a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.DISMOUNTING, var9)
                  : (
                     var7 && !this.parent.sneaking
                        ? var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SNEAKING_HELD)
                        : var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SNEAKING_TOGGLED)
                  )
            );
      } else if (var3.keyBindSprint.isKeyDown()) {
         if (!var5 && !var6) {
            boolean var10 = !this.parent.toggleSprint.b();
            var4 = var8 && !this.parent.sprinting
               ? var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SPRINTING_HELD)
               : (
                  this.parent.sprintHeld
                     ? var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SPRINTING_HELD)
                     : (
                        var10
                           ? var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SPRINTING_VANILLA)
                           : var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SPRINTING_TOGGLED)
                     )
               );
         }
      } else if (!var5 && var2.isSprinting()) {
         var4 = var4 + this.b(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.SPRINTING_VANILLA);
      }

      return var4;
   }

   private final class a extends com.cheatbreaker.client.kazerui.module.modulerender.RichTextHudBase {
      a() {
         super(ToggleSneakHudModule.this, 0.0F, 0.0F, com.cheatbreaker.client.kazerui.hud.HudAnchor.TOP_RIGHT, a(10, 18, 22, 50, 56, 80), false, false, false);
      }

      @Override
      protected String c(boolean var1) {
         if (!var1 && !ToggleSneakHudModule.this.showWhileTyping.b() && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
            return "";
         } else if (Minecraft.getMinecraft().thePlayer == null) {
            return var1 ? "Sprinting" : "";
         } else {
            String var2 = ToggleSneakHudModule.this.p();
            return var1 && var2.isEmpty() ? "Sprinting" : var2;
         }
      }

      @Override
      protected Boolean b(String var1) {
         return var1 == null
            ? null
            : !var1.contains(ToggleSneakHudModule.this.a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.DESCENDING))
               && !var1.contains(ToggleSneakHudModule.this.a(com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.b.DISMOUNTING));
      }

      @Override
      protected float f(boolean var1) {
         return super.f(var1) + (ToggleSneakHudModule.this.iconMode.b() ? 24 : 0);
      }

      @Override
      protected void a(String var1, float var2, float var3, boolean var4, boolean var5) {
         if (ToggleSneakHudModule.this.iconMode.b()) {
            float var6 = com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var4 ? "[" + var1 + "]" : var1);
            EntityPlayerSP var7 = Minecraft.getMinecraft().thePlayer;
            ResourceLocation var8 = var7 != null && var7.isSneaking()
               ? com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.SNEAKING
               : com.cheatbreaker.client.kazerui.module.modules.mechanic.ToggleSneakHudModule.SPRINTING;
            com.cheatbreaker.client.kazerui.module.modulerender.HudRenderUtils.a(var8, (float)((int)(var2 + var6 / 2.0F - 12.0F)), (float)((int)(var3 - 12.0F)), 24.0F, 24.0F, -1);
            super.a(var1, var2, var3 + 12.0F, var4, var5);
         } else {
            super.a(var1, var2, var3, var4, var5);
         }
      }
   }

   static enum b {
      FLYING("flying"),
      FLYING_BOOST("flying", "boost", true),
      RIDING("riding"),
      DESCENDING("descending"),
      DISMOUNTING("dismounting"),
      SNEAKING_TOGGLED("sneaking", "toggled"),
      SNEAKING_HELD("sneaking", "held"),
      SPRINTING_TOGGLED("sprinting", "toggled"),
      SPRINTING_HELD("sprinting", "held"),
      SPRINTING_VANILLA("sprinting", "vanilla"),
      HELD("held", null),
      TOGGLED("toggled", null),
      VANILLA("vanilla", null);

      final String main;
      final String partition;
      final boolean extraArguments;

      private b(String var3) {
         this(var3, null, false);
      }

      private b(String var3, String var4) {
         this(var3, var4, false);
      }

      private b(String var3, String var4, boolean var5) {
         this.main = var3;
         this.partition = var4;
         this.extraArguments = var5;
      }
   }
}
