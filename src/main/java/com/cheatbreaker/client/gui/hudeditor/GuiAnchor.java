package com.cheatbreaker.client.gui.hudeditor;

public enum GuiAnchor {
   LEFT_TOP("LEFT_TOP", 0),
   LEFT_MIDDLE("LEFT_MIDDLE", 1),
   LEFT_BOTTOM("LEFT_BOTTOM", 2),
   MIDDLE_TOP("MIDDLE_TOP", 3),
   MIDDLE_MIDDLE("MIDDLE_MIDDLE", 4),
   MIDDLE_BOTTOM_LEFT("MIDDLE_BOTTOM_LEFT", 5),
   MIDDLE_BOTTOM_RIGHT("MIDDLE_BOTTOM_RIGHT", 6),
   RIGHT_TOP("RIGHT_TOP", 7),
   RIGHT_MIDDLE("RIGHT_MIDDLE", 8),
   RIGHT_BOTTOM("RIGHT_BOTTOM", 9);

   private final String label;
   private final int id;

   private GuiAnchor(String var3, int var4) {
      this.label = var3;
      this.id = var4;
   }

   public String a() {
      return this.label;
   }

   public int b() {
      return this.id;
   }
}
