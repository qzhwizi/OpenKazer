package com.cheatbreaker.client.gui.uielement.settings;

public class LabelSettingElement extends com.cheatbreaker.client.gui.uielement.UIElement {
   private com.cheatbreaker.client.settings.Setting lIIIIlIIllIIlIIlIIIlIIllI;

   public LabelSettingElement(com.cheatbreaker.client.settings.Setting var1, float var2) {
      super(var2);
      this.lIIIIlIIllIIlIIlIIIlIIllI = var1;
      this.height = 12;
   }

   @Override
   public void a(int var1, int var2, float var3) {
      com.cheatbreaker.client.CheatBreakerClient.N()
         .ubuntuMedium16px
         .a(((String)this.lIIIIlIIllIIlIIlIIIlIIllI.d()).toUpperCase(), (float)(this.x + 2), (float)(this.y + 2), 1862270976);
      cn.kazer.client189.render.DrawRect.a(this.x + 2, this.y + this.height - 1, this.x + this.width / 2 - 20, this.y + this.height, 523185967);
   }

   @Override
   public void a(int var1, int var2, int var3) {
   }

   @Override
   public com.cheatbreaker.client.settings.Setting b() {
      return this.lIIIIlIIllIIlIIlIIIlIIllI;
   }
}
