package com.cheatbreaker.client.kazerui.input;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.RawInputEnvironmentPlugin;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller.Type;
import net.minecraft.util.MouseHelper;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public final class RawMouseInput extends MouseHelper {
   private static volatile com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner[] devices;
   private static boolean initialized;
   private boolean enabled;
   private boolean primed;

   public void a(boolean var1) {
      if (this.enabled != var1) {
         this.enabled = var1;
         this.primed = false;
      }

      if (var1 && !initialized) {
         initialized = true;
         Thread var2 = new Thread(() -> {
            ArrayList var0 = new ArrayList();

            try {
               RawInputEnvironmentPlugin var1x = new RawInputEnvironmentPlugin();
               if (var1x.isSupported()) {
                  for (Controller var5 : var1x.getControllers()) {
                     if (var5.getType() == Type.MOUSE && var0.size() < 8) {
                        Component var6 = var5.getComponent(Axis.X);
                        Component var7 = var5.getComponent(Axis.Y);
                        if (var6 != null && var7 != null && var6.isRelative() && var7.isRelative()) {
                           var0.add(new com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner(var5, var6, var7));
                        }
                     }
                  }
               }
            } catch (RuntimeException | LinkageError var8) {
               LogManager.getLogger().warn("[KC] Raw input unavailable; retaining native mouse input", var8);
            }

            devices = (com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner[]) var0.toArray(new com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner[var0.size()]);
            LogManager.getLogger().info("[KC] Raw mouse devices: {}", new Object[]{var0.size()});
         }, "Kazer raw mouse discovery");
         var2.setDaemon(true);
         var2.start();
      }
   }

   public boolean a() {
      com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner[] var1 = devices;
      return var1 != null && var1.length > 0;
   }

   public void grabMouseCursor() {
      super.grabMouseCursor();
      this.primed = false;
   }

   public void ungrabMouseCursor() {
      super.ungrabMouseCursor();
      this.primed = false;
   }

   public void mouseXYChange() {
      super.mouseXYChange();
      com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner[] var1 = devices;
      if (this.enabled && var1 != null && var1.length != 0) {
         int var2 = 0;
         int var3 = 0;
         boolean var4 = true;

         for (com.cheatbreaker.client.kazerui.input.RawMouseInput.aInner var8 : var1) {
            if (!var8.controller.poll()) {
               var4 = false;
            } else {
               var2 += Math.round(var8.x.getPollData());
               var3 -= Math.round(var8.y.getPollData());
            }
         }

         if (!var4) {
            this.primed = false;
         } else {
            boolean var9 = Display.isActive() && Mouse.isGrabbed();
            if (var9 && this.primed) {
               this.deltaX = var2;
               this.deltaY = var3;
            } else {
               this.deltaX = this.deltaY = 0;
               this.primed = var9;
            }
         }
      } else {
         this.primed = false;
      }
   }

   private static final class aInner {
      final Controller controller;
      final Component x;
      final Component y;

      aInner(Controller var1, Component var2, Component var3) {
         this.controller = var1;
         this.x = var2;
         this.y = var3;
      }
   }
}
