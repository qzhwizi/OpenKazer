package com.cheatbreaker.client.kazerui.module.modules.visual;

import java.util.Locale;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class ModuleHitbox extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private static ModuleHitbox instance;
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting players = this.a("hitboxPlayerShow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting items = this.a("hitboxItemShow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting living = this.a("hitboxPassiveShow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting other = this.a("hitboxOtherShow", true);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting limit = this.a("maxDistanceToggle", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting damaged = this.a("showDamagedColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting pointed = this.a("showHittableColor", false);
   private final com.cheatbreaker.client.kazerui.module.settings.EnumSetting<ModuleHitbox.a> pattern = this.a("hitboxLinePattern", ModuleHitbox.a.SOLID);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting width = this.a("lineWidth", 1.0F, 1.0F, 5.0F);
   private final com.cheatbreaker.client.kazerui.module.settings.SliderSetting distance = this.a("maxDistance", 64, 1, 128);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting color = this.a("lineColor", -1);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting damage = this.a("damagedColor", -43691);
   private final com.cheatbreaker.client.kazerui.module.settings.ColorSetting target = this.a("hittableColor", -11141291);

   public ModuleHitbox() {
      super("HITBOX", false);
      instance = this;
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      var1.a(this.players, this.items, this.living, this.other, this.pattern, this.width, this.color);
      var1.a(this.limit, var1x -> var1x.a(this.distance));
      var1.a(this.damaged, var1x -> var1x.a(this.damage));
      var1.a(this.pointed, var1x -> var1x.a(this.target));
   }

   public static boolean enabled(boolean var0) {
      return var0 || instance != null && instance.a();
   }

   public static boolean render(Entity var0, double var1, double var3, double var5, float var7, float var8) {
      ModuleHitbox var9 = instance;
      if (var9 == null || !var9.a()) {
         return false;
      } else if (var0 instanceof com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer) {
         return true;
      } else if (var0 instanceof EntityPlayer
         ? var9.players.b()
         : (var0 instanceof EntityItem ? var9.items.b() : (var0 instanceof EntityLivingBase ? var9.living.b() : var9.other.b()))) {
         if (var9.limit.b() && m().thePlayer != null && var0.getDistanceSqToEntity(m().thePlayer) > var9.distance.b() * var9.distance.b()) {
            return true;
         } else {
            AxisAlignedBB var10 = var0.getEntityBoundingBox();
            GL11.glPushAttrib(1048575);

            try {
               GL11.glDisable(3553);
               GL11.glDisable(2896);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glDepthMask(false);
               GL11.glLineWidth(var9.width.b());
               ModuleHitbox.a var11 = var9.pattern.j();
               GL11.glEnable(2852);
               GL11.glLineStipple(var11.factor, (short)var11.mask);
               com.cheatbreaker.client.kazerui.module.settings.ColorSetting var12 = var9.damaged.b() && var0 instanceof EntityLivingBase && ((EntityLivingBase)var0).hurtTime > 0
                  ? var9.damage
                  : (var9.pointed.b() && m().objectMouseOver != null && m().objectMouseOver.entityHit == var0 ? var9.target : var9.color);
               com.cheatbreaker.client.kazerui.module.modules.visual.BoxRenderHelper.a(var12.a(0.0F));
               com.cheatbreaker.client.kazerui.module.modules.visual.BoxRenderHelper.a(
                  var10.minX - var0.posX + var1,
                  var10.minY - var0.posY + var3,
                  var10.minZ - var0.posZ + var5,
                  var10.maxX - var0.posX + var1,
                  var10.maxY - var0.posY + var3,
                  var10.maxZ - var0.posZ + var5
               );
            } finally {
               GL11.glPopAttrib();
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public static enum a implements com.cheatbreaker.client.kazerui.module.settings.EnumSetting.a {
      SOLID(1, 65535),
      DASHED(3, 65280),
      DOTTED(1, 43690);

      final int factor;
      final int mask;

      private a(int var3, int var4) {
         this.factor = var3;
         this.mask = var4;
      }

      @Override
      public String a() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
