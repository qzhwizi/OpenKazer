package com.cheatbreaker.client.module.modules;

public class ItemDisplayEntry {
   public static final float DEFAULT_TOP_OFFSET = 60.0F;
   public String id;
   public boolean enabled;
   public boolean renderHud = true;
   public String anchor = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.MIDDLE_TOP.name();
   public float xTranslation;
   public float yTranslation;
   public float scale = 1.0F;
   public String displayMode;
   public Integer textColor = -1;
   public Boolean showBackground = false;
   public Integer backgroundColor = 1862270976;

   public ItemDisplayEntry() {
   }

   public ItemDisplayEntry(com.cheatbreaker.client.module.modules.TrackableItem var1) {
      this.id = var1.a();
      this.enabled = var1 == com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT || var1 == com.cheatbreaker.client.module.modules.TrackableItem.GOLDEN_APPLE;
      this.displayMode = com.cheatbreaker.client.module.modules.ItemLabelUtil.a(var1).name();
      if (var1 == com.cheatbreaker.client.module.modules.TrackableItem.HEALING_POT) {
         this.anchor = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_TOP.name();
         this.xTranslation = -1.0F;
         this.yTranslation = 11.0F;
      } else if (var1 == com.cheatbreaker.client.module.modules.TrackableItem.GOLDEN_APPLE) {
         this.anchor = com.cheatbreaker.client.gui.hudeditor.GuiAnchor.RIGHT_TOP.name();
         this.xTranslation = -1.0F;
         this.yTranslation = 19.0F;
      } else {
         this.yTranslation = 60.0F;
      }
   }
}
