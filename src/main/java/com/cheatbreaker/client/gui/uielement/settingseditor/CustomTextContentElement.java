package com.cheatbreaker.client.gui.uielement.settingseditor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class CustomTextContentElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private static final int CONTENT_MAX_LENGTH = 256;
   private static final int CONTENT_HEIGHT = 14;
   private final com.cheatbreaker.client.module.modules.config.CustomTextModule module;
   private final com.cheatbreaker.client.module.modules.config.CustomTextModule.a unit;
   private final com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextContentElement.a editor = new com.cheatbreaker.client.gui.uielement.settingseditor.CustomTextContentElement.a();

   public CustomTextContentElement(com.cheatbreaker.client.module.modules.config.CustomTextModule var1, com.cheatbreaker.client.module.modules.config.CustomTextModule.a var2, float var3) {
      super(var3);
      this.module = var1;
      this.unit = var2;
      this.editor.a(var1.z().b(var2.z().id) != null ? var1.z().b(var2.z().id).content : "");
   }

   @Override
   public int c() {
      return 24;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      Minecraft var4 = Minecraft.getMinecraft();
      int var5 = this.y;
      var4.fontRendererObj.drawString("CONTENT", this.x, var5, -6250336);
      var5 += 10;
      cn.kazer.client189.render.DrawRect.a(this.x - 1, var5 - 1, this.x + this.width + 1, var5 + 14 + 1, -6250336);
      cn.kazer.client189.render.DrawRect.a(this.x, var5, this.x + this.width, var5 + 14, -16777216);
      int var6 = this.width - 8;
      this.editor.a(var4.fontRendererObj, var6);
      String var7 = var4.fontRendererObj.trimStringToWidth(this.editor.value.substring(this.editor.scrollFrom), var6);
      var4.fontRendererObj.drawString(var7, this.x + 4, var5 + 3, -2039584);
      if (this.editor.focused && Minecraft.getSystemTime() % 1000L < 500L) {
         int var8 = this.x + 4 + var4.fontRendererObj.getStringWidth(var7.substring(0, Math.min(var7.length(), this.editor.cursor - this.editor.scrollFrom)));
         cn.kazer.client189.render.DrawRect.a(var8, var5 + 2, var8 + 1, var5 + 14 - 2, -3092272);
      }
   }

   @Override
   public void a(int var1, int var2, int var3) {
      if (var3 == 0) {
         if (var1 > this.x * this.scale
            && var1 < (this.x + this.width) * this.scale
            && var2 > (this.y + this.yOffset) * this.scale
            && var2 < (this.y + this.c() + this.yOffset) * this.scale) {
            this.editor.a();
            d();
         } else {
            this.editor.b();
         }
      }
   }

   @Override
   public boolean a(char var1, int var2) {
      if (!this.editor.focused) {
         return false;
      } else if (this.editor.a(var1, var2)) {
         com.cheatbreaker.client.module.modules.config.CustomTextItem var3 = this.module.z().b(this.unit.z().id);
         if (var3 != null) {
            this.module.a(var3.id, this.editor.value);
         }

         return true;
      } else if (var2 != 28 && var2 != 1) {
         return false;
      } else {
         this.editor.b();
         return true;
      }
   }

   @Override
   public void a() {
      this.editor.b();
   }

   private static void d() {
      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   static final class a {
      String value = "";
      int cursor;
      int scrollFrom;
      boolean focused;

      void a(String var1) {
         this.value = var1 == null ? "" : var1;
         this.cursor = this.value.length();
         this.scrollFrom = 0;
         this.focused = false;
      }

      void a() {
         this.focused = true;
         this.cursor = this.value.length();
      }

      void b() {
         this.focused = false;
      }

      void a(FontRenderer var1, int var2) {
         while (this.scrollFrom < this.cursor && var1.getStringWidth(this.value.substring(this.scrollFrom, this.cursor)) > var2) {
            this.scrollFrom++;
         }

         if (this.scrollFrom > this.cursor) {
            this.scrollFrom = this.cursor;
         }
      }

      boolean a(char var1, int var2) {
         if (!this.focused) {
            return false;
         } else {
            StringBuilder var3 = new StringBuilder(this.value);
            switch (var2) {
               case 14:
                  if (this.cursor > 0) {
                     var3.deleteCharAt(this.cursor - 1);
                     this.cursor--;
                     this.value = var3.toString();
                  }

                  return true;
               case 199:
                  this.cursor = 0;
                  return true;
               case 203:
                  if (this.cursor > 0) {
                     this.cursor--;
                  }

                  return true;
               case 205:
                  if (this.cursor < this.value.length()) {
                     this.cursor++;
                  }

                  return true;
               case 207:
                  this.cursor = this.value.length();
                  return true;
               case 211:
                  if (this.cursor < this.value.length()) {
                     this.value = var3.deleteCharAt(this.cursor).toString();
                  }

                  return true;
               default:
                  if (var1 >= ' ' && var1 != 127 && this.value.length() < 256) {
                     this.value = var3.insert(this.cursor, var1).toString();
                     this.cursor++;
                     return true;
                  } else {
                     return false;
                  }
            }
         }
      }
   }
}
