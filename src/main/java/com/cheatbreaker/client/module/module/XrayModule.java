package com.cheatbreaker.client.module.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;

public class XrayModule extends com.cheatbreaker.client.module.module.KeybindModule {
   private List<Integer> staffModuleEnabled;
   public com.cheatbreaker.client.settings.Setting opacity;

   public XrayModule() {
      super("xray");
      this.d(true);
      this.staffModuleEnabled = new ArrayList<>();
      Collections.addAll(this.staffModuleEnabled, 14, 15, 16, 15, 56, 129, 52);
      this.opacity = new com.cheatbreaker.client.settings.Setting(this, "Opacity").a(45).a(15, 255);
   }

   @Override
   public void b() {
      super.b();
      Minecraft.getMinecraft().renderGlobal.loadRenderers();
   }

   @Override
   public void c() {
      super.c();
      Minecraft.getMinecraft().renderGlobal.loadRenderers();
   }

   public boolean a(int var1, boolean var2) {
      return !this.g() ? var2 : this.staffModuleEnabled.contains(var1);
   }

   public List<Integer> A() {
      return this.staffModuleEnabled;
   }
}
