package cn.kazer.client189.animation;

final class LegacyVisualsFlags {
   static final int SMOOTH = 1;
   static final int LONG = 2;
   static final int RED = 4;
   static final int HEALTH = 8;
   static final int BLOCK_HIT = 16;
   static final int MODEL = 32;
   static final int THIRD = 64;
   static final int SWORD = 128;
   static final int BOW = 256;
   static final int ROD = 512;
   static final int CONSUME = 1024;
   static final int PUNCH = 2048;
   static final int SWITCH = 4096;
   static final int ITEMS = 8192;
   static final int ANIMATIONS = 16384;
   static final int ENABLED = 32768;
   private static volatile int bits;

   static boolean a(int var0) {
      return cn.kazer.client189.animation.OldAnimationsToggle.a() && (bits & var0) != 0;
   }

   static void a(cn.kazer.client189.animation.LegacyVisualsModule var0) {
      char var1 = 0;
      if (var0.g()) {
         var1 |= '耀';
         if (var0.a(var0.animations)) {
            var1 |= 16384;
         }

         if (var0.a(var0.items)) {
            var1 |= 8192;
         }

         if (var0.a(var0.smoothSneaking)) {
            var1 |= 1;
         }

         if (var0.a(var0.longSneaking)) {
            var1 |= 2;
         }

         if (var0.a(var0.redArmor)) {
            var1 |= 4;
         }

         if (var0.a(var0.healthFlash)) {
            var1 |= '\b';
         }

         if (var0.a(var0.blockHitting)) {
            var1 |= 16;
         }

         if (var0.a(var0.itemTransforms)) {
            var1 |= ' ';
         }

         if (var0.a(var0.thirdPerson)) {
            var1 |= '@';
         }

         if (var0.a(var0.swordBlock)) {
            var1 |= 128;
         }

         if (var0.a(var0.bows)) {
            var1 |= 256;
         }

         if (var0.a(var0.rods)) {
            var1 |= 512;
         }

         if (var0.a(var0.consuming)) {
            var1 |= 1024;
         }

         if (var0.a(var0.punching)) {
            var1 |= 2048;
         }

         if (var0.a(var0.itemSwitch)) {
            var1 |= 4096;
         }
      }

      bits = var1;
   }
}
