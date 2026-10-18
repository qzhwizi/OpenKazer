package com.twelvemonkeys.imageio.spi;

import com.twelvemonkeys.lang.Validate;

public class ProviderInfo {
   private final String title;
   private final String vendorName;
   private final String version;

   public ProviderInfo(Package var1) {
      Validate.notNull(var1, "package");
      String var2 = var1.getImplementationTitle();
      this.title = var2 != null ? var2 : var1.getName();
      String var3 = var1.getImplementationVendor();
      this.vendorName = var3 != null ? var3 : fakeVendor(var1);
      String var4 = var1.getImplementationVersion();
      this.version = var4 != null ? var4 : this.fakeVersion(var1);
   }

   private static String fakeVendor(Package var0) {
      String var1 = var0.getName();
      return var1.startsWith("com.twelvemonkeys") ? "TwelveMonkeys" : var1;
   }

   private String fakeVersion(Package var1) {
      String var2 = var1.getName();
      return var2.startsWith("com.twelvemonkeys") ? "DEV" : "Unspecified";
   }

   final String getImplementationTitle() {
      return this.title;
   }

   public final String getVendorName() {
      return this.vendorName;
   }

   public final String getVersion() {
      return this.version;
   }

   @Override
   public String toString() {
      return this.title + ", " + this.version + " by " + this.vendorName;
   }
}
