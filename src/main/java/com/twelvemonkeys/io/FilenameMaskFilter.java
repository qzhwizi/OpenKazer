package com.twelvemonkeys.io;

import com.twelvemonkeys.util.regex.WildcardStringParser;
import java.io.File;
import java.io.FilenameFilter;

@Deprecated
public class FilenameMaskFilter implements FilenameFilter {
   private String[] filenameMasksForInclusion;
   private String[] filenameMasksForExclusion;
   private boolean inclusion = true;

   public FilenameMaskFilter() {
   }

   public FilenameMaskFilter(String var1) {
      String[] var2 = new String[]{var1};
      this.setFilenameMasksForInclusion(var2);
   }

   public FilenameMaskFilter(String[] var1) {
      this(var1, false);
   }

   public FilenameMaskFilter(String var1, boolean var2) {
      String[] var3 = new String[]{var1};
      if (var2) {
         this.setFilenameMasksForExclusion(var3);
      } else {
         this.setFilenameMasksForInclusion(var3);
      }
   }

   public FilenameMaskFilter(String[] var1, boolean var2) {
      if (var2) {
         this.setFilenameMasksForExclusion(var1);
      } else {
         this.setFilenameMasksForInclusion(var1);
      }
   }

   public void setFilenameMasksForInclusion(String[] var1) {
      this.filenameMasksForInclusion = var1;
   }

   public String[] getFilenameMasksForInclusion() {
      return (String[])this.filenameMasksForInclusion.clone();
   }

   public void setFilenameMasksForExclusion(String[] var1) {
      this.filenameMasksForExclusion = var1;
      this.inclusion = false;
   }

   public String[] getFilenameMasksForExclusion() {
      return (String[])this.filenameMasksForExclusion.clone();
   }

   @Override
   public boolean accept(File var1, String var2) {
      if (this.inclusion) {
         for (String var12 : this.filenameMasksForInclusion) {
            WildcardStringParser var8 = new WildcardStringParser(var12);
            if (var8.parseString(var2)) {
               return true;
            }
         }

         return false;
      } else {
         for (String var7 : this.filenameMasksForExclusion) {
            WildcardStringParser var3 = new WildcardStringParser(var7);
            if (var3.parseString(var2)) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      if (this.inclusion) {
         if (this.filenameMasksForInclusion == null) {
            var1.append("No filename masks set - property filenameMasksForInclusion is null!");
         } else {
            var1.append(this.filenameMasksForInclusion.length);
            var1.append(" filename mask(s) - ");

            for (int var2 = 0; var2 < this.filenameMasksForInclusion.length; var2++) {
               var1.append("\"");
               var1.append(this.filenameMasksForInclusion[var2]);
               var1.append("\", \"");
            }
         }
      } else if (this.filenameMasksForExclusion == null) {
         var1.append("No filename masks set - property filenameMasksForExclusion is null!");
      } else {
         var1.append(this.filenameMasksForExclusion.length);
         var1.append(" exclusion filename mask(s) - ");

         for (int var3 = 0; var3 < this.filenameMasksForExclusion.length; var3++) {
            var1.append("\"");
            var1.append(this.filenameMasksForExclusion[var3]);
            var1.append("\", \"");
         }
      }

      return var1.toString();
   }
}
