package com.twelvemonkeys.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

final class Win32FileSystem extends FileSystem {
   @Override
   public long getFreeSpace(File var1) {
      try {
         BufferedReader var2 = exec(new String[]{"CMD.EXE", "/C", "DIR", "/-C", var1.getAbsolutePath()});
         String var3 = null;

         String var4;
         try {
            while ((var4 = var2.readLine()) != null) {
               var3 = var4;
            }
         } finally {
            FileUtil.close(var2);
         }

         if (var3 != null) {
            int var5 = var3.lastIndexOf(" bytes free");
            int var6 = var3.lastIndexOf(32, var5 - 1);
            if (var6 >= 0 && var5 >= 0) {
               try {
                  return Long.parseLong(var3.substring(var6 + 1, var5));
               } catch (NumberFormatException var12) {
               }
            }
         }
      } catch (IOException var13) {
      }

      return 0L;
   }

   @Override
   long getTotalSpace(File var1) {
      return this.getFreeSpace(var1);
   }

   @Override
   String getName() {
      return "Win32";
   }
}
