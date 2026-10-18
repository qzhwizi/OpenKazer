package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.io.FileUtil;
import com.twelvemonkeys.lang.StringUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

public final class WriterFileSuffixFilter extends FileFilter implements java.io.FileFilter {
   private final String description;
   private Map<String, Boolean> knownSuffixes = new HashMap<>(32);

   public WriterFileSuffixFilter() {
      this("Images (all supported output formats)");
   }

   public WriterFileSuffixFilter(String var1) {
      this.description = var1;
   }

   @Override
   public boolean accept(File var1) {
      if (var1.isDirectory()) {
         return true;
      } else {
         String var2 = FileUtil.getExtension(var1);
         return !StringUtil.isEmpty(var2) && this.hasWriterForSuffix(var2);
      }
   }

   private boolean hasWriterForSuffix(String var1) {
      if (this.knownSuffixes.get(var1) == Boolean.TRUE) {
         return true;
      } else {
         try {
            Iterator var2 = ImageIO.getImageWritersBySuffix(var1);
            if (var2.hasNext()) {
               this.knownSuffixes.put(var1, Boolean.TRUE);
               return true;
            } else {
               this.knownSuffixes.put(var1, Boolean.FALSE);
               return false;
            }
         } catch (IllegalArgumentException var3) {
            return false;
         }
      }
   }

   @Override
   public String getDescription() {
      return this.description;
   }
}
