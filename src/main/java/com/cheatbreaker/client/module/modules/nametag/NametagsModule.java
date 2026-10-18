package com.cheatbreaker.client.module.modules.nametag;

public final class NametagsModule extends com.cheatbreaker.client.module.Module {
   public final com.cheatbreaker.client.settings.Setting textShadow;
   public final com.cheatbreaker.client.settings.Setting thirdPersonNametag;
   public final com.cheatbreaker.client.settings.Setting displayToggleMessage;
   public final com.cheatbreaker.client.settings.Setting hideInFirstPerson;
   public final com.cheatbreaker.client.settings.Setting opacity;
   public final com.cheatbreaker.client.settings.Setting replaceOwnColor;
   public final com.cheatbreaker.client.settings.Setting showBadgeInTab;
   public final com.cheatbreaker.client.settings.Setting showNametagIcon;

   public NametagsModule() {
      super("Nametags");
      this.c(true);
      this.textShadow = new com.cheatbreaker.client.settings.Setting(this, "Nametag Text Shadow").a(false);
      this.thirdPersonNametag = new com.cheatbreaker.client.settings.Setting(this, "Third Person Nametag").a(true);
      this.displayToggleMessage = new com.cheatbreaker.client.settings.Setting(this, "Display Toggle Nametags Message").a(true);
      this.hideInFirstPerson = new com.cheatbreaker.client.settings.Setting(this, "Hide Nametags in F1").a(false);
      this.opacity = new com.cheatbreaker.client.settings.Setting(this, "Nametag Opacity").a(Float.valueOf(1.0F)).a(0.1F, 1.0F);
      this.replaceOwnColor = new com.cheatbreaker.client.settings.Setting(this, "Replace Own Nametag Color").a(false);
      this.showBadgeInTab = new com.cheatbreaker.client.settings.Setting(this, "Show Kazer Icon in TAB").a(true);
      this.showNametagIcon = new com.cheatbreaker.client.settings.Setting(this, "Show Nametag Icon").a(true);
      this.a("NAME", 1.6F);
   }
}
