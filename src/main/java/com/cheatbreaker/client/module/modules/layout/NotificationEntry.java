package com.cheatbreaker.client.module.modules.layout;

import net.minecraft.client.gui.ScaledResolution;

class NotificationEntry {
   public com.cheatbreaker.client.module.modules.layout.NotificationType type;
   public String lIIIIIIIIIlIllIIllIlIIlIl;
   public long IlllIIIlIlllIllIlIIlllIlI;
   public long IIIIllIlIIIllIlllIlllllIl = System.currentTimeMillis();
   public int IIIIllIIllIIIIllIllIIIlIl = 2;
   public int IlIlIIIlllIIIlIlllIlIllIl;
   public int IIIllIllIlIlllllllIlIlIII = 20;
   public int IllIIIIIIIlIlIllllIIllIII = 0;
   final com.cheatbreaker.client.module.modules.layout.NotificationsModule notificationsModule;

   NotificationEntry(com.cheatbreaker.client.module.modules.layout.NotificationsModule var1, ScaledResolution var2, com.cheatbreaker.client.module.modules.layout.NotificationType var3, String var4, long var5) {
      this.notificationsModule = var1;
      this.type = var3;
      this.lIIIIIIIIIlIllIIllIlIIlIl = var4;
      this.IlllIIIlIlllIllIlIIlllIlI = var5;
      this.IIIllIllIlIlllllllIlIlIII = var3 == com.cheatbreaker.client.module.modules.layout.NotificationType.DEFAULT ? 16 : 20;
      this.IlIlIIIlllIIIlIlllIlIllIl = var2.getScaledHeight() - 14 - this.IIIllIllIlIlllllllIlIlIII;
      this.IIIIllIIllIIIIllIllIIIlIl = var2.getScaledHeight() + this.IIIllIllIlIlllllllIlIlIII;
   }

   public void a() {
      if (this.IlIlIIIlllIIIlIlllIlIllIl != -1) {
         this.IllIIIIIIIlIlIllllIIllIII++;
         float var1 = this.IllIIIIIIIlIlIllllIIllIII * (this.IllIIIIIIIlIlIllllIIllIII / 5.0F) / 7.0F;
         if (this.IIIIllIIllIIIIllIllIIIlIl > this.IlIlIIIlllIIIlIlllIlIllIl) {
            if (this.IIIIllIIllIIIIllIllIIIlIl - var1 < this.IlIlIIIlllIIIlIlllIlIllIl) {
               this.IIIIllIIllIIIIllIllIIIlIl = this.IlIlIIIlllIIIlIlllIlIllIl;
               this.IlIlIIIlllIIIlIlllIlIllIl = -1;
            } else {
               this.IIIIllIIllIIIIllIllIIIlIl = (int)(this.IIIIllIIllIIIIllIllIIIlIl - var1);
            }
         } else if (this.IIIIllIIllIIIIllIllIIIlIl < this.IlIlIIIlllIIIlIlllIlIllIl) {
            if (this.IIIIllIIllIIIIllIllIIIlIl + var1 > this.IlIlIIIlllIIIlIlllIlIllIl) {
               this.IIIIllIIllIIIIllIllIIIlIl = this.IlIlIIIlllIIIlIlllIlIllIl;
               this.IlIlIIIlllIIIlIlllIlIllIl = -1;
            } else {
               this.IIIIllIIllIIIIllIllIIIlIl = (int)(this.IIIIllIIllIIIIllIllIIIlIl + var1);
            }
         } else {
            this.IlIlIIIlllIIIlIlllIlIllIl = -1;
         }
      }
   }

   public void a(int var1) {
      com.cheatbreaker.client.module.modules.layout.NotificationRenderer.a(
         this.type,
         this.lIIIIIIIIIlIllIIllIlIIlIl,
         var1,
         this.IIIIllIIllIIIIllIllIIIlIl,
         this.IIIIllIlIIIllIlllIlllllIl,
         this.IlllIIIlIlllIllIlIIlllIlI,
         System.currentTimeMillis()
      );
   }
}
