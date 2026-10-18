package com.twelvemonkeys.imageio.color;

import com.twelvemonkeys.io.FileUtil;
import com.twelvemonkeys.lang.Platform;
import com.twelvemonkeys.lang.SystemUtil;
import com.twelvemonkeys.lang.Validate;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

public final class ColorProfiles {
   private static final ICCProfileSanitizer profileCleaner = ICCProfileSanitizer.Factory.get();
   static final int ICC_PROFILE_MAGIC = 1633907568;
   static final int ICC_PROFILE_HEADER_SIZE = 128;

   private ColorProfiles() {
   }

   static byte[] getProfileHeaderWithProfileId(ICC_Profile var0) {
      return getProfileHeaderWithProfileId(var0.getData());
   }

   static byte[] getProfileHeaderWithProfileId(byte[] var0) {
      byte[] var1 = Arrays.copyOf(var0, 128);
      Arrays.fill(var1, 4, 8, (byte)0);
      Arrays.fill(var1, 40, 44, (byte)0);
      Arrays.fill(var1, 64, 68, (byte)0);
      Arrays.fill(var1, 80, 84, (byte)0);
      Arrays.fill(var1, 84, 100, (byte)0);
      byte[] var2 = computeMD5(var1, var0);
      System.arraycopy(var2, 0, var1, 84, var2.length);
      return var1;
   }

   private static byte[] computeMD5(byte[] var0, byte[] var1) {
      try {
         MessageDigest var2 = MessageDigest.getInstance("MD5");
         var2.update(var0, 0, 128);
         var2.update(var1, 128, var1.length - 128);
         return var2.digest();
      } catch (NoSuchAlgorithmException var3) {
         throw new IllegalStateException("Missing MD5 MessageDigest");
      }
   }

   public static boolean isCS_sRGB(ICC_Profile var0) {
      Validate.notNull(var0, "profile");
      return var0.getColorSpaceType() == 5 && Arrays.equals(getProfileHeaderWithProfileId(var0), ColorProfiles.sRGB.header);
   }

   public static boolean isCS_GRAY(ICC_Profile var0) {
      Validate.notNull(var0, "profile");
      return var0.getColorSpaceType() == 6 && Arrays.equals(getProfileHeaderWithProfileId(var0), ColorProfiles.GRAY.header);
   }

   static boolean isOffendingColorProfile(ICC_Profile var0) {
      Validate.notNull(var0, "profile");
      byte[] var1 = var0.getData(1751474532);
      return var1[64] != 0 || var1[65] != 0 || var1[66] != 0 || var1[67] > 3;
   }

   public static ICC_Profile validateProfile(ICC_Profile var0) {
      profileCleaner.fixProfile(var0);
      ColorSpaces.validateColorSpace(new ICC_ColorSpace(var0));
      return var0;
   }

   public static ICC_Profile readProfileRaw(InputStream var0) throws IOException {
      Validate.notNull(var0, "input");
      return ICC_Profile.getInstance(var0);
   }

   public static ICC_Profile readProfile(InputStream var0) throws IOException {
      Validate.notNull(var0, "input");
      DataInputStream var1 = new DataInputStream(var0);
      byte[] var2 = new byte[128];

      try {
         var1.readFully(var2);
         int var3 = validateHeaderAndGetSize(var2);
         byte[] var4 = Arrays.copyOf(var2, var3);
         var1.readFully(var4, var2.length, var3 - var2.length);
         return createProfile(var4);
      } catch (EOFException var5) {
         throw new IllegalArgumentException("Truncated ICC Profile data", var5);
      }
   }

   public static ICC_Profile createProfileRaw(byte[] var0) {
      int var1 = validateHeaderAndGetSize(var0);
      return ICC_Profile.getInstance(limit(var0, var1));
   }

   public static ICC_Profile createProfile(byte[] var0) {
      int var1 = validateAndGetSize(var0);
      byte[] var2 = getProfileHeaderWithProfileId(var0);
      ICC_Profile var3 = getInternalProfile(var2);
      if (var3 != null) {
         return var3;
      } else {
         ICC_ColorSpace var4 = ColorSpaces.getCachedCS(var2);
         if (var4 != null) {
            return var4.getProfile();
         } else {
            ICC_Profile var5 = ICC_Profile.getInstance(limit(var0, var1));
            return ColorSpaces.createColorSpace(var5).getProfile();
         }
      }
   }

   private static byte[] limit(byte[] var0, int var1) {
      return var0.length == var1 ? var0 : Arrays.copyOf(var0, var1);
   }

   private static int validateAndGetSize(byte[] var0) {
      int var1 = validateHeaderAndGetSize(var0);
      if (var1 >= 0 && var1 <= var0.length) {
         return var1;
      } else {
         throw new IllegalArgumentException("Truncated ICC profile data, length < " + var1 + ": " + var0.length);
      }
   }

   private static int validateHeaderAndGetSize(byte[] var0) {
      Validate.notNull(var0, "input");
      if (var0.length < 128) {
         throw new IllegalArgumentException("Truncated ICC profile data, length < 128: " + var0.length);
      } else {
         int var1 = intBigEndian(var0, 0);
         if (intBigEndian(var0, 36) != 1633907568) {
            throw new IllegalArgumentException("Not an ICC profile, missing file signature");
         } else {
            return var1;
         }
      }
   }

   private static ICC_Profile getInternalProfile(byte[] var0) {
      int var1 = getCsType(var0);
      if (var1 == 5 && Arrays.equals(var0, ColorProfiles.sRGB.header)) {
         return ICC_Profile.getInstance(1000);
      } else if (var1 == 6 && Arrays.equals(var0, ColorProfiles.GRAY.header)) {
         return ICC_Profile.getInstance(1003);
      } else if (var1 == 13 && Arrays.equals(var0, ColorProfiles.PYCC.header)) {
         return ICC_Profile.getInstance(1002);
      } else if (var1 == 5 && Arrays.equals(var0, ColorProfiles.LINEAR_RGB.header)) {
         return ICC_Profile.getInstance(1004);
      } else {
         return var1 == 0 && Arrays.equals(var0, ColorProfiles.CIEXYZ.header) ? ICC_Profile.getInstance(1001) : null;
      }
   }

   private static int intBigEndian(byte[] var0, int var1) {
      return (var0[var1] & 0xFF) << 24 | (var0[var1 + 1] & 0xFF) << 16 | (var0[var1 + 2] & 0xFF) << 8 | var0[var1 + 3] & 0xFF;
   }

   private static int getCsType(byte[] var0) {
      int var1 = intBigEndian(var0, 16);
      switch (var1) {
         case 843271250:
            return 12;
         case 860048466:
            return 13;
         case 876825682:
            return 14;
         case 893602898:
            return 15;
         case 910380114:
            return 16;
         case 927157330:
            return 17;
         case 943934546:
            return 18;
         case 960711762:
            return 19;
         case 1094929490:
            return 20;
         case 1111706706:
            return 21;
         case 1128483922:
            return 22;
         case 1129142560:
            return 11;
         case 1129142603:
            return 9;
         case 1145261138:
            return 23;
         case 1162038354:
            return 24;
         case 1178815570:
            return 25;
         case 1196573017:
            return 6;
         case 1212961568:
            return 8;
         case 1213421088:
            return 7;
         case 1281450528:
            return 1;
         case 1282766368:
            return 2;
         case 1380401696:
            return 5;
         case 1482250784:
            return 0;
         case 1497588338:
            return 3;
         case 1501067552:
            return 4;
         default:
            throw new IllegalArgumentException("Invalid ICC color space signature: " + var1);
      }
   }

   static ICC_Profile readProfileFromClasspathResource(String var0) {
      InputStream var1 = ColorSpaces.class.getResourceAsStream(var0);
      if (var1 != null) {
         if (ColorSpaces.DEBUG) {
            System.out.println("Loading profile from classpath resource: " + var0);
         }

         ICC_Profile var2;
         try {
            var2 = ICC_Profile.getInstance(var1);
         } catch (IOException var6) {
            if (ColorSpaces.DEBUG) {
               var6.printStackTrace();
            }

            return null;
         } finally {
            FileUtil.close(var1);
         }

         return var2;
      } else {
         return null;
      }
   }

   static ICC_Profile readProfileFromPath(String var0) {
      if (var0 != null) {
         if (ColorSpaces.DEBUG) {
            System.out.println("Loading profile from: " + var0);
         }

         try {
            return ICC_Profile.getInstance(var0);
         } catch (IOException | SecurityException var2) {
            if (ColorSpaces.DEBUG) {
               var2.printStackTrace();
            }
         }
      }

      return null;
   }

   static void fixProfile(ICC_Profile var0) {
      profileCleaner.fixProfile(var0);
   }

   static boolean validationAltersProfileHeader() {
      return profileCleaner.validationAltersProfileHeader();
   }

   static {
      ProfileDeferralActivator.activateProfiles();
   }

   static class CIEXYZ {
      static final byte[] header = ColorProfiles.getProfileHeaderWithProfileId(ICC_Profile.getInstance(1001));
   }

   static class GRAY {
      static final byte[] header = ColorProfiles.getProfileHeaderWithProfileId(ICC_Profile.getInstance(1003));
   }

   static class LINEAR_RGB {
      static final byte[] header = ColorProfiles.getProfileHeaderWithProfileId(ICC_Profile.getInstance(1004));
   }

   static class PYCC {
      static final byte[] header = ColorProfiles.getProfileHeaderWithProfileId(ICC_Profile.getInstance(1002));
   }

   static class Profiles {
      private static final Properties PROFILES = loadProfiles();

      private static Properties loadProfiles() {
         Properties var0;
         try {
            var0 = SystemUtil.loadProperties(ColorSpaces.class, "com/twelvemonkeys/imageio/color/icc_profiles_" + Platform.os().id());
         } catch (IOException | SecurityException var4) {
            System.err.printf("Warning: Could not load system default ICC profile locations from %s, will use bundled fallback profiles.\n", var4.getMessage());
            if (ColorSpaces.DEBUG) {
               var4.printStackTrace();
            }

            var0 = null;
         }

         Properties var1 = new Properties(var0);

         try {
            Properties var2 = SystemUtil.loadProperties(ColorSpaces.class, "com/twelvemonkeys/imageio/color/icc_profiles");
            var1.putAll(var2);
         } catch (IOException | SecurityException var3) {
         }

         if (ColorSpaces.DEBUG) {
            System.out.println("User ICC profiles: " + var1);
            System.out.println("System ICC profiles : " + var0);
         }

         return var1;
      }

      static String getPath(String var0) {
         return PROFILES.getProperty(var0);
      }
   }

   static class sRGB {
      static final byte[] header = ColorProfiles.getProfileHeaderWithProfileId(ICC_Profile.getInstance(1000));
   }
}
