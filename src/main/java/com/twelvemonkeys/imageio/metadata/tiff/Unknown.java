package com.twelvemonkeys.imageio.metadata.tiff;

final class Unknown {
   private final short type;
   private final int count;
   private final long pos;

   public Unknown(short var1, int var2, long var3) {
      this.type = var1;
      this.count = var2;
      this.pos = var3;
   }

   @Override
   public int hashCode() {
      return (int)(this.pos ^ this.pos >>> 32) + this.count * 37 + this.type * 97;
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         Unknown var2 = (Unknown)var1;
         return this.pos == var2.pos && this.type == var2.type && this.count == var2.count;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return this.count == 1 ? String.format("Unknown(%d)@%08x", this.type, this.pos) : String.format("Unknown(%d)[%d]@%08x", this.type, this.count, this.pos);
   }
}
