package com.cheatbreaker.client.module.modules;

public final class ScreenshotModule extends com.cheatbreaker.client.module.Module {
   public static final String AUTO_COPY_IMAGE = "IMAGE";
   public static final String AUTO_COPY_FILE = "FILE";
   public static final String AUTO_COPY_OFF = "OFF";
   private final com.cheatbreaker.client.settings.Setting autoCopy;
   private final com.cheatbreaker.client.settings.Setting screenshotSound;

   public ScreenshotModule() {
      super("Screenshot");
      this.c(true);
      this.g(false);
      this.isEditable = false;
      this.a("Screenshot", 1.0F);
      this.autoCopy = new com.cheatbreaker.client.settings.Setting(this, "Auto Copy").a("IMAGE").a("IMAGE", "FILE", "OFF");
      this.screenshotSound = new com.cheatbreaker.client.settings.Setting(this, "Screenshot Sound").a(true);
   }

   public com.cheatbreaker.client.settings.Setting a() {
      return this.autoCopy;
   }

   public com.cheatbreaker.client.settings.Setting z() {
      return this.screenshotSound;
   }

   public static String a(String var0) {
      if ("IMAGE".equalsIgnoreCase(var0)) {
         return "copy";
      } else {
         return "FILE".equalsIgnoreCase(var0) ? "copy_file" : null;
      }
   }
}
