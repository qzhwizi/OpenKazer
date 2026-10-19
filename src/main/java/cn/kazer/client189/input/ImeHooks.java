package cn.kazer.client189.input;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;

public final class ImeHooks {
   private ImeHooks() {
   }

   public static boolean shouldDispatch() {
      return cn.kazer.client189.input.TypedCharacterFilter.a(Keyboard.getEventKeyState(), Keyboard.getEventKey(), Keyboard.getEventCharacter());
   }

   public static boolean shouldDispatchInGui() {
      return Minecraft.getMinecraft().currentScreen == null ? Keyboard.getEventKeyState() : shouldDispatch();
   }

   public static void textField(Object var0) {
      GuiTextField var1 = (GuiTextField)var0;
      if (var1.isFocused() && var1.getVisible()) {
         Minecraft var2 = Minecraft.getMinecraft();
         int var3 = ReflectionHelper.<Integer, GuiTextField>getPrivateValue(GuiTextField.class, var1, "lineScrollOffset", "field_146225_q");
         FontRenderer var4 = ReflectionHelper.getPrivateValue(GuiTextField.class, var1, "fontRendererInstance", "field_146211_a");
         String var5 = var4.trimStringToWidth(var1.getText().substring(var3), var1.getWidth());
         int var6 = var1.getCursorPosition() - var3;
         if (var6 >= 0 && var6 <= var5.length()) {
            int var7 = var1.xPosition + (var1.getEnableBackgroundDrawing() ? 4 : 0) + var4.getStringWidth(var5.substring(0, var6));
            if (var1.getCursorPosition() < var1.getText().length() || var1.getText().length() >= var1.getMaxStringLength()) {
               var7--;
            }

            int var8 = var1.yPosition + (var1.getEnableBackgroundDrawing() ? (var1.height - 8) / 2 : 0);
            WindowsImeSupport.a(var1, var7, var8 - 1, var4.FONT_HEIGHT + 2);
         }
      }
   }

   public static void screen(GuiScreen var0) {
      Minecraft var1 = Minecraft.getMinecraft();
      FontRenderer var2 = var1.fontRendererObj;
      cn.kazer.client189.input.ImeCaretPosition var3 = null;
      if (var0 instanceof GuiEditSign) {
         GuiEditSign var4 = (GuiEditSign)var0;
         TileEntitySign var5 = ReflectionHelper.getPrivateValue(GuiEditSign.class, var4, "tileSign", "field_146848_f");
         int var6 = ReflectionHelper.<Integer, GuiEditSign>getPrivateValue(GuiEditSign.class, var4, "editLine", "field_146851_h");
         var3 = cn.kazer.client189.input.ImeCaretPosition.a(var0.width, var0.height, var6, var2.getStringWidth(var5.signText[var6].getUnformattedText()), var2.FONT_HEIGHT);
      } else if (var0 instanceof GuiScreenBook) {
         GuiScreenBook var13 = (GuiScreenBook)var0;
         boolean var14 = ReflectionHelper.<Boolean, GuiScreenBook>getPrivateValue(GuiScreenBook.class, var13, "bookIsUnsigned", "field_146475_i");
         if (!var14) {
            return;
         }

         boolean var15 = ReflectionHelper.<Boolean, GuiScreenBook>getPrivateValue(GuiScreenBook.class, var13, "bookGettingSigned", "field_146480_s");
         int var7 = (var0.width - 192) / 2;
         if (var15) {
            String var8 = ReflectionHelper.getPrivateValue(GuiScreenBook.class, var13, "bookTitle", "field_146482_z");
            var3 = cn.kazer.client189.input.ImeCaretPosition.a(var7 + 94, 50, var2.getStringWidth(var8), var2.FONT_HEIGHT);
         } else {
            NBTTagList var16 = ReflectionHelper.getPrivateValue(GuiScreenBook.class, var13, "bookPages", "field_146483_y");
            int var9 = ReflectionHelper.<Integer, GuiScreenBook>getPrivateValue(GuiScreenBook.class, var13, "currPage", "field_146484_x");
            String var10 = var16 != null && var9 >= 0 && var9 < var16.tagCount() ? var16.getStringTagAt(var9) : "";
            List var11 = var2.listFormattedStringToWidth(var10 + "§0_", 116);
            int var12 = Math.max(0, var2.getStringWidth((String)var11.get(var11.size() - 1)) - var2.getStringWidth("_"));
            var3 = cn.kazer.client189.input.ImeCaretPosition.b(var7 + 36, 34, var12, var11.size(), var2.FONT_HEIGHT);
         }
      }

      if (var3 != null) {
         WindowsImeSupport.a(var3.a(), var3.b(), var3.c());
      }
   }
}
