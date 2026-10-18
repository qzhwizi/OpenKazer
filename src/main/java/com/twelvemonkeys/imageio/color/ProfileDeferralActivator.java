package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.imageio.util.IIOUtil;
import java.io.File;
import java.util.Locale;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageInputStream;

final class ProfileDeferralActivator {
   private static void activateProfilesInternal() {
      try {
         Class.forName("java.awt.image.ColorConvertOp");
      } catch (Throwable var1) {
         System.err.println("ProfileDeferralMgr.activateProfiles() failed. ICC Color Profiles may not work properly, see stack trace below.");
         System.err.println("For more information, see https://bugs.openjdk.java.net/browse/JDK-6986863");
         System.err.println("Please upgrade to Java 17 or later where this bug is fixed, or ask your JRE provider to backport the fix.");
         System.err.println();
         System.err.println("If you can't update to Java 17, a possible workaround is to add");
         System.err.println("\tClass.forName(\"java.awt.image.ColorConvertOp\");");
         System.err.println("*early* in your application startup code, to force profile activation before profiles are accessed.");
         System.err.println();
         var1.printStackTrace();
      }
   }

   static void activateProfiles() {
   }

   static {
      activateProfilesInternal();
   }

   public static final class Spi extends ImageInputStreamSpi {
      @Override
      public void onRegistration(ServiceRegistry var1, Class<?> var2) {
         ProfileDeferralActivator.activateProfiles();
         IIOUtil.deregisterProvider(var1, this, var2);
      }

      @Override
      public String getDescription(Locale var1) {
         return this.getClass().getName();
      }

      @Override
      public ImageInputStream createInputStreamInstance(Object var1, boolean var2, File var3) {
         throw new UnsupportedOperationException();
      }
   }
}
