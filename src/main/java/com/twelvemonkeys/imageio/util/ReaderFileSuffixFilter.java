package com.twelvemonkeys.imageio.util;

import com.twelvemonkeys.io.FileUtil;
import com.twelvemonkeys.lang.StringUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

public final class ReaderFileSuffixFilter extends FileFilter implements java.io.FileFilter {
   private final String description;
   private final Map<String, Boolean> knownSuffixes = new HashMap<>(32);

   public ReaderFileSuffixFilter() {
      this("Images (all supported input formats)");
   }

   public ReaderFileSuffixFilter(String var1) {
      this.description = var1;
   }

   @Override
   public boolean accept(File var1) {
      if (var1.isDirectory()) {
         return true;
      } else {
         String var2 = FileUtil.getExtension(var1);
         return !StringUtil.isEmpty(var2) && this.hasReaderForSuffix(var2);
      }
   }

   private boolean hasReaderForSuffix(String var1) {
      if (this.knownSuffixes.get(var1) == Boolean.TRUE) {
         return true;
      } else {
         try {
            Iterator var2 = ImageIO.getImageReadersBySuffix(var1);
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
