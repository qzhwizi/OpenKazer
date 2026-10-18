package cn.kazer.client189.animation;

import java.util.ArrayList;
import java.util.List;

public final class LegacyVisualsLayout {
   public final List<cn.kazer.client189.animation.LegacyVisualsLayout.a> rows = new ArrayList<>();
   public int height;

   public LegacyVisualsLayout(boolean var1, boolean var2) {
      this.a("Animations");
      if (var1) {
         this.a("Red Armor", "Remove Health Bar Flashing");
         this.b("SNEAKING");
         this.a("Smooth Sneaking", "Longer Unsneak");
      }

      this.height += 6;
      this.a("Items");
      if (var2) {
         this.b("ITEM TRANSFORMS");
         this.a("1.7 Item Positions", "1.7 Block Animation");
         this.a("1.7 3rd Person Block Animation");
         this.a("1.7 Bow Pullback", "1.7 Rod Position");
         this.a("Consume Animation", "Item Switching Animation");
         this.b("BLOCK HITTING");
         this.a("Block-Hitting Animation", "Punching During Usage");
      }
   }

   private void a(String var1) {
      this.rows.add(new cn.kazer.client189.animation.LegacyVisualsLayout.a(var1, this.height, 0, false, true));
      this.height += 21;
   }

   private void a(String var1, String var2) {
      this.rows.add(new cn.kazer.client189.animation.LegacyVisualsLayout.a(var1, this.height, 0, false, false));
      if (var2 != null) {
         this.rows.add(new cn.kazer.client189.animation.LegacyVisualsLayout.a(var2, this.height, 1, false, false));
      }

      this.height += 21;
   }

   private void b(String var1) {
      this.rows.add(new cn.kazer.client189.animation.LegacyVisualsLayout.a(var1, this.height, 0, true, true));
      this.height += 15;
   }

   public static final class a {
      public final String label;
      public final int y;
      public final int column;
      public final boolean heading;
      public final boolean wide;

      a(String var1, int var2, int var3, boolean var4, boolean var5) {
         this.label = var1;
         this.y = var2;
         this.column = var3;
         this.heading = var4;
         this.wide = var5;
      }
   }
}
