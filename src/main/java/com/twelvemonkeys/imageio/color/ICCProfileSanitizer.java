package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.lang.SystemUtil;
import java.awt.color.ICC_Profile;

interface ICCProfileSanitizer {
   void fixProfile(ICC_Profile var1);

   boolean validationAltersProfileHeader();

   public static class Factory {
      static ICCProfileSanitizer get() {
         String var1 = System.getProperty("sun.java2d.cmm");
         Object var0;
         if ("sun.java2d.cmm.kcms.KcmsServiceProvider".equals(var1) && SystemUtil.isClassAvailable("sun.java2d.cmm.kcms.CMM")) {
            var0 = new KCMSSanitizerStrategy();
         } else if ("sun.java2d.cmm.lcms.LcmsServiceProvider".equals(var1) && SystemUtil.isClassAvailable("sun.java2d.cmm.lcms.LCMS")) {
            var0 = new LCMSSanitizerStrategy();
         } else if (!SystemUtil.isClassAvailable("java.util.stream.Stream")
            && (!SystemUtil.isClassAvailable("java.lang.invoke.CallSite") || SystemUtil.isClassAvailable("sun.java2d.cmm.kcms.CMM"))) {
            var0 = new KCMSSanitizerStrategy();
         } else {
            var0 = new LCMSSanitizerStrategy();
         }

         if (ColorSpaces.DEBUG) {
            System.out.println("ICC ProfileCleaner instance: " + var0.getClass().getName());
         }

         return (ICCProfileSanitizer)var0;
      }
   }
}
