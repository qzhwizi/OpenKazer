package com.twelvemonkeys.io;

import com.twelvemonkeys.util.StringTokenIterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

final class UnixFileSystem extends FileSystem {
   @Override
   long getFreeSpace(File var1) {
      try {
         return this.getNumber(var1, 3);
      } catch (IOException var3) {
         return 0L;
      }
   }

   @Override
   long getTotalSpace(File var1) {
      try {
         return this.getNumber(var1, 5);
      } catch (IOException var3) {
         return 0L;
      }
   }

   private long getNumber(File var1, int var2) throws IOException {
      BufferedReader var3 = exec(new String[]{"df", "-k", var1.getAbsolutePath()});
      String var4 = null;

      String var5;
      try {
         while ((var5 = var3.readLine()) != null) {
            var4 = var5;
         }
      } finally {
         FileUtil.close(var3);
      }

      if (var4 != null) {
         String var6 = null;
         StringTokenIterator var7 = new StringTokenIterator(var4, " ", -1);

         for (int var8 = 0; var8 < var2 && var7.hasNext(); var8++) {
            var6 = var7.nextToken();
         }

         if (var6 != null) {
            try {
               return Long.parseLong(var6) * 1024L;
            } catch (NumberFormatException var12) {
            }
         }
      }

      return 0L;
   }

   @Override
   String getName() {
      return "Unix";
   }
}
