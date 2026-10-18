package com.cheatbreaker.client.util.playerskin;

import net.minecraft.util.ResourceLocation;

public class PlayerSkin {
   private final float scale;
   private final String name;
   private final ResourceLocation location;
   private final ResourceLocation previewLocation;
   private boolean equipped;
   private final String playerId;

   public PlayerSkin(String var1, String var2, float var3, boolean var4, String var5) {
      this.playerId = var1;
      this.name = var2;
      this.scale = var3;
      this.equipped = var4;
      this.location = new ResourceLocation(var5);
      this.previewLocation = new ResourceLocation("preview/" + var5);
   }

   public String a() {
      return this.name;
   }

   public ResourceLocation b() {
      return this.location;
   }

   public float c() {
      return this.scale;
   }

   public String d() {
      return this.playerId;
   }

   public ResourceLocation e() {
      return this.previewLocation;
   }

   public boolean f() {
      return this.equipped;
   }

   public void a(boolean var1) {
      this.equipped = var1;
   }
}
