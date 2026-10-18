package com.cheatbreaker.client.gui.hudeditor;

public enum AnchorAlignment {
   BOTTOM("BOTTOM"),
   TOP("TOP"),
   CENTER("CENTER"),
   LEFT("LEFT"),
   RIGHT("RIGHT");

   private final String identifier;

   private AnchorAlignment(String var3) {
      this.identifier = var3;
   }

   public String a() {
      return this.identifier;
   }
}
