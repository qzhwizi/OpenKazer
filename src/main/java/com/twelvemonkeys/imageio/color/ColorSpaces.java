package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.lang.Validate;
import com.twelvemonkeys.util.LRUHashMap;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;

public final class ColorSpaces {
   static final boolean DEBUG = "true".equalsIgnoreCase(System.getProperty("com.twelvemonkeys.imageio.color.debug"));
   public static final int CS_ADOBE_RGB_1998 = 5000;
   public static final int CS_GENERIC_CMYK = 5001;
   private static WeakReference<ICC_Profile> adobeRGB1998 = new WeakReference<>(null);
   private static WeakReference<ICC_Profile> genericCMYK = new WeakReference<>(null);
   private static final Map<ColorSpaces.Key, ICC_ColorSpace> cache = new LRUHashMap<>(16);

   private ColorSpaces() {
   }

   public static ICC_ColorSpace createColorSpace(ICC_Profile var0) {
      Validate.notNull(var0, "profile");
      ColorProfiles.fixProfile(var0);
      byte[] var1 = ColorProfiles.getProfileHeaderWithProfileId(var0);
      ICC_ColorSpace var2 = getInternalCS(var0.getColorSpaceType(), var1);
      return var2 != null ? var2 : getCachedOrCreateCS(var0, var1);
   }

   static ICC_ColorSpace getInternalCS(int var0, byte[] var1) {
      if (var0 == 5 && Arrays.equals(var1, ColorProfiles.sRGB.header)) {
         return (ICC_ColorSpace)ColorSpace.getInstance(1000);
      } else if (var0 == 6 && Arrays.equals(var1, ColorProfiles.GRAY.header)) {
         return (ICC_ColorSpace)ColorSpace.getInstance(1003);
      } else if (var0 == 13 && Arrays.equals(var1, ColorProfiles.PYCC.header)) {
         return (ICC_ColorSpace)ColorSpace.getInstance(1002);
      } else if (var0 == 5 && Arrays.equals(var1, ColorProfiles.LINEAR_RGB.header)) {
         return (ICC_ColorSpace)ColorSpace.getInstance(1004);
      } else {
         return var0 == 0 && Arrays.equals(var1, ColorProfiles.CIEXYZ.header) ? (ICC_ColorSpace)ColorSpace.getInstance(1001) : null;
      }
   }

   private static ICC_ColorSpace getCachedOrCreateCS(ICC_Profile var0, byte[] var1) {
      ColorSpaces.Key var2 = new ColorSpaces.Key(var1);
      synchronized (cache) {
         ICC_ColorSpace var4 = getCachedCS(var2);
         if (var4 == null) {
            var4 = new ICC_ColorSpace(var0);
            validateColorSpace(var4);
            cache.put(var2, var4);
            if (ColorProfiles.validationAltersProfileHeader()) {
               cache.put(new ColorSpaces.Key(ColorProfiles.getProfileHeaderWithProfileId(var4.getProfile())), var4);
            }
         }

         return var4;
      }
   }

   private static ICC_ColorSpace getCachedCS(ColorSpaces.Key var0) {
      synchronized (cache) {
         return cache.get(var0);
      }
   }

   static ICC_ColorSpace getCachedCS(byte[] var0) {
      return getCachedCS(new ColorSpaces.Key(var0));
   }

   static void validateColorSpace(ICC_ColorSpace var0) {
      var0.fromRGB(new float[]{0.999F, 0.5F, 0.001F});
      var0.getProfile().getData();
   }

   @Deprecated
   public static boolean isCS_sRGB(ICC_Profile var0) {
      return ColorProfiles.isCS_sRGB(var0);
   }

   @Deprecated
   public static boolean isCS_GRAY(ICC_Profile var0) {
      return ColorProfiles.isCS_GRAY(var0);
   }

   @Deprecated
   public static ICC_Profile validateProfile(ICC_Profile var0) {
      return ColorProfiles.validateProfile(var0);
   }

   public static ColorSpace getColorSpace(int var0) {
      switch (var0) {
         case 5000:
            ICC_Profile var7;
            synchronized (ColorSpaces.class) {
               var7 = adobeRGB1998.get();
               if (var7 == null) {
                  var7 = ColorProfiles.readProfileFromPath(ColorProfiles.Profiles.getPath("ADOBE_RGB_1998"));
                  if (var7 == null) {
                     var7 = ColorProfiles.readProfileFromClasspathResource("/profiles/ClayRGB1998.icc");
                     if (var7 == null) {
                        throw new IllegalStateException("Could not read AdobeRGB1998 profile");
                     }
                  }

                  if (var7.getColorSpaceType() != 5) {
                     throw new IllegalStateException("Configured AdobeRGB1998 profile is not TYPE_RGB");
                  }

                  adobeRGB1998 = new WeakReference<>(var7);
               }
            }

            return createColorSpace(var7);
         case 5001:
            ICC_Profile var1;
            synchronized (ColorSpaces.class) {
               var1 = genericCMYK.get();
               if (var1 == null) {
                  var1 = ColorProfiles.readProfileFromPath(ColorProfiles.Profiles.getPath("GENERIC_CMYK"));
                  if (var1 == null) {
                     if (DEBUG) {
                        System.out.println("Using fallback profile");
                     }

                     return CMYKColorSpace.getInstance();
                  }

                  if (var1.getColorSpaceType() != 9) {
                     throw new IllegalStateException("Configured Generic CMYK profile is not TYPE_CMYK");
                  }

                  genericCMYK = new WeakReference<>(var1);
               }
            }

            return createColorSpace(var1);
         default:
            return ColorSpace.getInstance(var0);
      }
   }

   static {
      ProfileDeferralActivator.activateProfiles();
   }

   private static final class Key {
      private final byte[] data;

      Key(byte[] var1) {
         this.data = var1;
      }

      @Override
      public boolean equals(Object var1) {
         return var1 instanceof ColorSpaces.Key && Arrays.equals(this.data, ((ColorSpaces.Key)var1).data);
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode(this.data);
      }

      @Override
      public String toString() {
         return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
      }
   }
}
