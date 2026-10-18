package com.cheatbreaker.client.module.modules.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public final class BlockhitSoundModule extends com.cheatbreaker.client.module.Module {
   private static final ResourceLocation ANVIL_LAND_SOUND = new ResourceLocation("random.anvil_land");
   private static final ResourceLocation DEBUG_HIT_SOUND = new ResourceLocation("random.successful_hit");
   private final com.cheatbreaker.client.module.modules.animation.SwingTracker tracker = new com.cheatbreaker.client.module.modules.animation.SwingTracker();
   private final com.cheatbreaker.client.settings.Setting volume;
   private final com.cheatbreaker.client.settings.Setting maxDistance;
   private final com.cheatbreaker.client.settings.Setting debugSound;
   private int lastHurtTime = 0;

   public BlockhitSoundModule() {
      super("Blockhit Sound");
      this.c(true);
      this.g(false);
      this.isEditable = false;
      this.a("Blockhit Sound", 1.0F);
      this.volume = new com.cheatbreaker.client.settings.Setting(this, "Volume").a(100).a(0, 100);
      this.maxDistance = new com.cheatbreaker.client.settings.Setting(this, "Max Distance").a(Float.valueOf(5.0F)).a(3.0F, 6.0F);
      this.debugSound = new com.cheatbreaker.client.settings.Setting(this, "Debug Sound").a(false);
      new com.cheatbreaker.client.settings.Setting(this, "label").a("Heuristic detection, may be inaccurate");
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
   }

   @Override
   public void a(boolean var1) {
      if (!var1) {
         this.tracker.a();
         this.lastHurtTime = 0;
      }

      super.a(var1);
   }

   private void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      Minecraft var2 = this.minecraft;
      if (var2.thePlayer != null && var2.theWorld != null) {
         long var3 = System.currentTimeMillis();
double var5 = var2.thePlayer.posX;
         double var7 = var2.thePlayer.posZ;
         double var9 = com.cheatbreaker.client.module.modules.animation.SwingTracker.a((Float)this.maxDistance.d());

         for (EntityPlayer var12 : var2.theWorld.playerEntities) {
            if (var12 != var2.thePlayer && var12.isSwingInProgress && var12.swingProgressInt <= 0) {
               this.tracker.a(var12.posX, var12.posZ, var12.rotationYaw, var5, var7, var3, var9);
            }
         }

         int var15 = var2.thePlayer.hurtTime;
         boolean var16 = var15 > this.lastHurtTime;
         this.lastHurtTime = var15;
         if (var16) {
            if (this.tracker.a(var2.thePlayer.isBlocking(), var5, var7, var3)) {
               float var13 = com.cheatbreaker.client.module.modules.animation.SwingTracker.a((Integer)this.volume.d());
               if (var13 > 0.0F) {
ResourceLocation var14 = (Boolean) this.debugSound.d() ? DEBUG_HIT_SOUND : ANVIL_LAND_SOUND;
                  var2.getSoundHandler().playSound(PositionedSoundRecord.create(var14, var13));
               }
            }
         }
      } else {
         this.tracker.a();
         this.lastHurtTime = 0;
      }
   }
}
