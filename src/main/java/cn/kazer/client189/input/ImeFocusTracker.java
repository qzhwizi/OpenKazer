package cn.kazer.client189.input;

public final class ImeFocusTracker {
   private final ImeFocusTracker.a bridge;
   private Object owner;
   private boolean reported;
   private boolean known;

   public ImeFocusTracker(ImeFocusTracker.a var1) {
      this.bridge = var1;
   }

   public void a() {
      this.reported = false;
   }

   public boolean a(Object var1) {
      this.reported = true;
      boolean var2 = this.owner != var1;
      if (var2 && this.owner != null) {
         this.bridge.a();
      }

      if (!this.known || this.owner == null) {
         this.bridge.a(true);
         this.known = true;
      }

      this.owner = var1;
      return var2;
   }

   public boolean b() {
      if (this.reported) {
         return false;
      } else if (this.known && this.owner == null) {
         return false;
      } else {
         this.bridge.a(false);
         this.known = true;
         this.owner = null;
         return true;
      }
   }

   public void c() {
      this.owner = null;
      this.known = false;
      this.reported = false;
   }

   public interface a {
      void a(boolean var1);

      void a();
   }
}
