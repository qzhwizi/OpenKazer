package cn.kazer.client189.screens;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackRepository;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

public final class KazerResourcePacksScreen extends GuiScreenResourcePacks {
   private GuiTextField search;
   private cn.kazer.client189.screens.KazerResourcePacksScreen.a available;
   private cn.kazer.client189.screens.KazerResourcePacksScreen.a selected;
   private final List<ResourcePackListEntry> filtered = new ArrayList<>();
   private volatile boolean repositoryReady;
   private boolean repositoryLoading;
   private static final ExecutorService SCANNER = Executors.newSingleThreadExecutor(var0 -> {
      Thread var1 = new Thread(var0, "Kazer resource-pack scan");
      var1.setDaemon(true);
      return var1;
   });
   private static CompletableFuture<Void> pendingScan;

   private static synchronized CompletableFuture<Void> a(ResourcePackRepository var0) {
      if (pendingScan == null || pendingScan.isDone()) {
         pendingScan = CompletableFuture.runAsync(var0::updateRepositoryEntriesAll, SCANNER);
      }

      return pendingScan;
   }

   public KazerResourcePacksScreen(GuiScreen var1) {
      super(var1);
   }

   public void initGui() {
      if (!this.repositoryReady) {
         if (!this.repositoryLoading) {
            this.repositoryLoading = true;
            ResourcePackRepository var2 = this.mc.getResourcePackRepository();
            a(var2).whenComplete((var1x, var2x) -> this.mc.addScheduledTask(() -> {
               this.repositoryReady = true;
               if (var2x != null) {
                  LogManager.getLogger().warn("Resource-pack scan failed", var2x);
               }

               if (this.mc.currentScreen == this) {
                  this.setWorldAndResolution(this.mc, this.width, this.height);
               }
            }));
         }
      } else {
         super.initGui();
         this.buttonList.clear();
         int var1 = this.width / 2 + 4;
         this.buttonList.add(new GuiButton(2, this.width / 2 - 154, this.height - 48, 150, 20, "Open resource pack folder"));
         this.buttonList.add(new GuiButton(1, var1, this.height - 48, 200, 20, "Done"));
         this.buttonList.add(new GuiButton(25, var1, this.height - 92, 200, 20, ""));
         this.buttonList.add(new GuiButton(23, var1, this.height - 70, 112, 20, ""));
         this.buttonList.add(new GuiButton(24, var1 + 114, this.height - 70, 86, 20, ""));
         this.a();
         this.search = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 204, this.height - 72, 200, 20);
         this.search.setMaxStringLength(128);
         this.b();
         this.available = new cn.kazer.client189.screens.KazerResourcePacksScreen.a(this.width / 2 - 204, this.height - 76, this.filtered);
         this.selected = new cn.kazer.client189.screens.KazerResourcePacksScreen.a(var1, this.height - 96, this.getSelectedResourcePacks());
      }
   }

   private void a() {
      com.cheatbreaker.client.settings.GlobalSettings var1 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;

      for (GuiButton var3 : this.buttonList) {
         if (var3.id == 25) {
            var3.displayString = "Background: " + ((Boolean) var1.transparentBackground.d() ? "TRANSPARENT" : "DEFAULT");
         }

         if (var3.id == 23) {
            var3.displayString = "Clear Glass: " + var1.clearGlass.d();
         }

         if (var3.id == 24) {
            var3.displayString = "Red String: " + ((Boolean) var1.redString.d() ? "ON" : "OFF");
         }
      }
   }

   private void b() {
      this.filtered.clear();
      String var1 = this.search == null ? "" : this.search.getText().toLowerCase(Locale.ROOT);

      for (ResourcePackListEntry var3 : this.getAvailableResourcePacks()) {
         if (var1.isEmpty()) {
            this.filtered.add(var3);
         } else {
            String var4 = "";

            try {
               Method var5 = ResourcePackListEntry.class.getDeclaredMethod("func_148312_b");
               var5.setAccessible(true);
               var4 = String.valueOf(var5.invoke(var3));
            } catch (ReflectiveOperationException var6) {
            }

            if (var1.isEmpty() || var4.toLowerCase(Locale.ROOT).contains(var1)) {
               this.filtered.add(var3);
            }
         }
      }
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id < 20) {
         super.actionPerformed(var1);
      } else {
         com.cheatbreaker.client.settings.GlobalSettings var2 = com.cheatbreaker.client.CheatBreakerClient.N().globalSettings;
         com.cheatbreaker.client.settings.Setting var3 = var1.id == 25 ? var2.transparentBackground : (var1.id == 23 ? var2.clearGlass : var2.redString);
         if (var1.id == 23) {
            String var4 = String.valueOf(var3.d());
            var3.a(var4.equals("OFF") ? "REGULAR" : (var4.equals("REGULAR") ? "ALL" : "OFF"));
            this.mc.renderGlobal.loadRenderers();
         } else {
            var3.a(!(Boolean)var3.d());
            if (var1.id == 24) {
               this.mc.renderGlobal.loadRenderers();
            }
         }

         com.cheatbreaker.client.CheatBreakerClient.N().configManager.c();
         this.a();
      }
   }

   public void updateScreen() {
      if (this.search != null) {
         this.search.updateCursorCounter();
      }
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.search == null) {
         if (var2 == 1) {
            super.keyTyped(var1, var2);
         }
      } else if (this.search.textboxKeyTyped(var1, var2)) {
         this.b();
      } else {
         super.keyTyped(var1, var2);
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (this.search != null) {
         this.search.mouseClicked(var1, var2, var3);
         if (var3 == 0) {
            for (GuiButton var5 : this.buttonList) {
               if (var5.mousePressed(this.mc, var1, var2)) {
                  var5.playPressSound(this.mc.getSoundHandler());
                  this.actionPerformed(var5);
                  return;
               }
            }
         }

         this.available.mouseClicked(var1, var2, var3);
         this.selected.mouseClicked(var1, var2, var3);
         this.b();
      }
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if (this.available != null) {
         this.available.mouseReleased(var1, var2, var3);
         this.selected.mouseReleased(var1, var2, var3);
      }
   }

   public void handleMouseInput() throws IOException {
      if (this.available != null && this.selected != null) {
         super.handleMouseInput();
         this.available.handleMouseInput();
         this.selected.handleMouseInput();
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      boolean var4 = (Boolean)com.cheatbreaker.client.CheatBreakerClient.N().globalSettings.transparentBackground.d() && this.mc.theWorld != null;
      if (var4) {
         Gui.drawRect(0, 0, this.width, this.height, 1879048192);
      } else {
         this.drawDefaultBackground();
      }

      if (this.search == null) {
         com.cheatbreaker.client.gui.render.font.TrueTypeFontRenderer var7 = com.cheatbreaker.client.CheatBreakerClient.N().playRegular18px;
         String var8 = "Loading resource packs...";
         var7.b(var8, (double)((this.width - var7.b(var8)) / 2.0F), (double)(this.height / 2.0F), -1);
      } else {
         this.drawCenteredString(this.fontRendererObj, "Available Resource Packs", this.width / 2 - 104, 17, -1);
         this.drawCenteredString(this.fontRendererObj, "Selected Resource Packs", this.width / 2 + 104, 17, -1);
         this.available.drawScreen(var1, var2, var3);
         this.selected.drawScreen(var1, var2, var3);
         this.search.drawTextBox();
         if (this.search.getText().isEmpty() && !this.search.isFocused()) {
            this.fontRendererObj.drawStringWithShadow("§oSearch...", this.width / 2 - 200, this.height - 66, 11184810);
         }

         for (GuiButton var6 : this.buttonList) {
            var6.drawButton(this.mc, var1, var2);
         }

         this.drawCenteredString(this.fontRendererObj, "(Place resource-pack files here)", this.width / 2 - 80, this.height - 23, 7829367);
      }
   }

   private final class a extends GuiListExtended {
      private final List<ResourcePackListEntry> entries;

      a(int var2, int var3, List<ResourcePackListEntry> var4) {
         super(KazerResourcePacksScreen.this.mc, 200, KazerResourcePacksScreen.this.height, 36, var3, 36);
         this.entries = var4;
         this.setSlotXBoundsFromLeft(var2);
         this.setShowSelectionBox(false);
      }

      public IGuiListEntry getListEntry(int var1) {
         return (IGuiListEntry)this.entries.get(var1);
      }

      protected int getSize() {
         return this.entries.size();
      }

      protected void drawBackground() {
      }

      public int getListWidth() {
         return 200;
      }

      protected int getScrollBarX() {
         return this.right - 6;
      }

      public void drawScreen(int var1, int var2, float var3) {
         this.mouseX = var1;
         this.mouseY = var2;
         this.bindAmountScrolled();
         Gui.drawRect(this.left, this.top, this.right, this.bottom, 2013265920);
         ScaledResolution var4 = new ScaledResolution(this.mc);
         int var5 = var4.getScaleFactor();
         GL11.glEnable(3089);
         GL11.glScissor(
            this.left * var5, (var4.getScaledHeight() - this.bottom) * var5, 200 * var5, (this.bottom - this.top) * var5
         );

         for (int var6 = 0; var6 < this.entries.size(); var6++) {
            int var7 = this.top + 4 + var6 * 36 - (int)this.amountScrolled;
            if (var7 + 32 > this.top && var7 < this.bottom) {
               this.entries
                  .get(var6)
                  .drawEntry(
                     var6,
                     this.left + 2,
                     var7,
                     198,
                     32,
                     var1,
                     var2,
                     var1 >= this.left && var1 < this.right && var2 >= var7 && var2 < var7 + 36
                  );
            }
         }

         GL11.glDisable(3089);
         int var9 = this.func_148135_f();
         if (var9 > 0) {
            int var10 = Math.max(20, (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight());
            int var8 = this.top + (int)this.amountScrolled * (this.bottom - this.top - var10) / var9;
            Gui.drawRect(this.right - 6, var8, this.right, var8 + var10, -4473925);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
