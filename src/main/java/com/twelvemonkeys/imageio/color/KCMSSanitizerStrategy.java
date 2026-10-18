package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.lang.Validate;
import java.awt.color.ICC_Profile;

final class KCMSSanitizerStrategy implements ICCProfileSanitizer {
   private static final int CORBIS_RGB_ALTERNATE_XYZ = 396690872;

   @Override
   public void fixProfile(ICC_Profile var1) {
      Validate.notNull(var1, "profile");
      byte[] var2 = var1.getData(1751474532);
      if (intFromBigEndian(var2, 64) != 0) {
         intToBigEndian(0, var2, 64);
         var1.setData(1751474532, var2);
      }

      if (fixProfileXYZTag(var1, 2004119668)) {
         fixProfileXYZTag(var1, 1918392666);
         fixProfileXYZTag(var1, 1733843290);
         fixProfileXYZTag(var1, 1649957210);
      }
   }

   @Override
   public boolean validationAltersProfileHeader() {
      return false;
   }

   private static boolean fixProfileXYZTag(ICC_Profile var0, int var1) {
      byte[] var2 = var0.getData(var1);
      if (var2 != null && intFromBigEndian(var2, 0) == 396690872) {
         intToBigEndian(1482250784, var2, 0);
         var0.setData(var1, var2);
         return true;
      } else {
         return false;
      }
   }

   private static int intFromBigEndian(byte[] var0, int var1) {
      return (var0[var1] & 0xFF) << 24 | (var0[var1 + 1] & 0xFF) << 16 | (var0[var1 + 2] & 0xFF) << 8 | var0[var1 + 3] & 0xFF;
   }

   private static void intToBigEndian(int var0, byte[] var1, int var2) {
      var1[var2] = (byte)(var0 >> 24);
      var1[var2 + 1] = (byte)(var0 >> 16);
      var1[var2 + 2] = (byte)(var0 >> 8);
      var1[var2 + 3] = (byte)var0;
   }
}
