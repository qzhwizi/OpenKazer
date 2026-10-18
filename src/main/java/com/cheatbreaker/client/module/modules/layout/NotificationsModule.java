package com.cheatbreaker.client.module.modules.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;

public class NotificationsModule extends com.cheatbreaker.client.module.Module {
   public long time = System.currentTimeMillis();
   private List<NotificationEntry> notifications = new ArrayList<>();

   public NotificationsModule() {
      super("Notifications");
      this.a(com.cheatbreaker.client.event.event.KeepAliveEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.ClientTickEvent.class, this::a);
      this.a(com.cheatbreaker.client.event.event.RenderHudEvent.class, this::a);
      this.c(true);
   }

   private void a(com.cheatbreaker.client.event.event.KeepAliveEvent var1) {
      this.time = System.currentTimeMillis();
   }

   private void a(com.cheatbreaker.client.event.event.ClientTickEvent var1) {
      Iterator var2 = this.notifications.iterator();

      while (var2.hasNext()) {
         NotificationEntry var3 = (NotificationEntry)var2.next();
         var3.a();
         if (var3.IIIIllIlIIIllIlllIlllllIl + var3.IlllIIIlIlllIllIlIIlllIlI - System.currentTimeMillis() <= 0L) {
            int var4 = var3.IIIIllIIllIIIIllIllIIIlIl;

            for (NotificationEntry var6 : this.notifications) {
               if (var6.IIIIllIIllIIIIllIllIIIlIl < var3.IIIIllIIllIIIIllIllIIIlIl) {
                  var6.IllIIIIIIIlIlIllllIIllIII = 0;
                  var6.IlIlIIIlllIIIlIlllIlIllIl = var4;
                  var4 = var6.IIIIllIIllIIIIllIllIIIlIl;
               }
            }

            var2.remove();
         }
      }
   }

   private void a(com.cheatbreaker.client.event.event.RenderHudEvent var1) {
      for (NotificationEntry var3 : this.notifications) {
         var3.a(var1.a().getScaledWidth());
      }
   }

   public void a(String var1, String var2, long var3) {
      ScaledResolution var5 = new ScaledResolution(this.minecraft);
      if (var3 < 2000L) {
         var3 = 2000L;
      }

      var2 = var2.replaceAll("&([abcdefghijklmrABCDEFGHIJKLMNR0-9])|(&$)", "§$1");
      String var6 = var1.toLowerCase();
      com.cheatbreaker.client.module.modules.layout.NotificationType var7;
      switch (var6) {
         case "info":
            var7 = com.cheatbreaker.client.module.modules.layout.NotificationType.INFO;
            break;
         case "error":
            var7 = com.cheatbreaker.client.module.modules.layout.NotificationType.ERROR;
            break;
         default:
            var7 = com.cheatbreaker.client.module.modules.layout.NotificationType.DEFAULT;
      }

      NotificationEntry var8 = new NotificationEntry(this, var5, var7, var2, var3);
      int var13 = var8.IlIlIIIlllIIIlIlllIlIllIl - var8.IIIllIllIlIlllllllIlIlIII - 2;

      for (int var10 = this.notifications.size() - 1; var10 >= 0; var10--) {
         NotificationEntry var11 = this.notifications.get(var10);
         var11.IllIIIIIIIlIlIllllIIllIII = 0;
         var11.IlIlIIIlllIIIlIlllIlIllIl = var13;
         var13 -= 2 + var11.IIIllIllIlIlllllllIlIlIII;
      }

      this.notifications.add(var8);
   }
}
