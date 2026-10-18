package com.cheatbreaker.client.kazerui.concurrent.rendercache;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.WorldSettings.GameType;

public final class PreviewPlayer extends EntityOtherPlayerMP {
   public static final ResourceLocation ASTRONAUT = new ResourceLocation("kazerui", "skins/astronaut_dark.png");
   private static WorldClient previewWorld;
   private final boolean self;
   private ResourceLocation sessionSkin;
   private final List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> cosmetics = new ArrayList<>();

   private PreviewPlayer(World var1, boolean var2) {
      super(var1, new GameProfile(UUID.randomUUID(), var2 ? Minecraft.getMinecraft().getSession().getUsername() : "Astronaut"));
      this.self = var2;
      if (var2) {
         GameProfile var3 = Minecraft.getMinecraft().getSession().getProfile();
         this.sessionSkin = DefaultPlayerSkin.getDefaultSkin(var3.getId() == null ? new UUID(0L, 0L) : var3.getId());
         if (var3.getId() != null) {
            Minecraft.getMinecraft().getSkinManager().loadProfileTextures(var3, new SkinAvailableCallback() {
               public void skinAvailable(Type var1, ResourceLocation var2x, MinecraftProfileTexture var3x) {
                  if (var1 == Type.SKIN) {
                     PreviewPlayer.this.sessionSkin = var2x;
                  }
               }
            }, true);
         }
      }
   }

   public static com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer a() {
      return new com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer(c(), false);
   }

   public static com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer b() {
      return new com.cheatbreaker.client.kazerui.concurrent.rendercache.PreviewPlayer(c(), true);
   }

   public static World c() {
      Minecraft var0 = Minecraft.getMinecraft();
      if (var0.theWorld != null) {
         return var0.theWorld;
      } else {
         if (previewWorld == null) {
            previewWorld = new WorldClient(
               null, new WorldSettings(0L, GameType.NOT_SET, false, false, WorldType.DEFAULT), 0, EnumDifficulty.PEACEFUL, var0.mcProfiler
            );
         }

         return previewWorld;
      }
   }

   public List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> d() {
      return this.cosmetics;
   }

   public void a(List<com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry> var1) {
      this.cosmetics.clear();
      this.cosmetics.addAll(var1);
   }

   public ResourceLocation getLocationSkin() {
      return this.self ? (Minecraft.getMinecraft().thePlayer != null ? Minecraft.getMinecraft().thePlayer.getLocationSkin() : this.sessionSkin) : ASTRONAUT;
   }

   public String getSkinType() {
      return this.self && Minecraft.getMinecraft().thePlayer != null ? Minecraft.getMinecraft().thePlayer.getSkinType() : "slim";
   }

   public ResourceLocation getLocationCape() {
      return this.self && Minecraft.getMinecraft().thePlayer != null ? Minecraft.getMinecraft().thePlayer.getLocationCape() : null;
   }

   public boolean hasPlayerInfo() {
      return this.self && Minecraft.getMinecraft().thePlayer != null;
   }

   public boolean isWearing(EnumPlayerModelParts var1) {
      return !this.self || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().thePlayer.isWearing(var1);
   }

   public boolean isSpectator() {
      return false;
   }

   public boolean isInvisibleToPlayer(EntityPlayer var1) {
      return false;
   }
}
