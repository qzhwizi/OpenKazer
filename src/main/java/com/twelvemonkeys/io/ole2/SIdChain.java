package com.twelvemonkeys.io.ole2;

import java.util.NoSuchElementException;

final class SIdChain {
   int[] chain;
   int size = 0;
   int next = 0;

   public SIdChain() {
      this.chain = new int[16];
   }

   void addSID(int var1) {
      this.ensureCapacity();
      this.chain[this.size++] = var1;
   }

   private void ensureCapacity() {
      if (this.chain.length == this.size) {
         int[] var1 = new int[this.size << 1];
         System.arraycopy(this.chain, 0, var1, 0, this.size);
         this.chain = var1;
      }
   }

   public int[] getChain() {
      int[] var1 = new int[this.size];
      System.arraycopy(this.chain, 0, var1, 0, this.size);
      return var1;
   }

   public void reset() {
      this.next = 0;
   }

   public boolean hasNext() {
      return this.next < this.size;
   }

   public int next() {
      if (this.next >= this.size) {
         throw new NoSuchElementException("No element");
      } else {
         return this.chain[this.next++];
      }
   }

   public int get(int var1) {
      return this.chain[var1];
   }

   public int length() {
      return this.size;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder(this.size * 5);
      var1.append('[');

      for (int var2 = 0; var2 < this.size; var2++) {
         if (var2 != 0) {
            var1.append(',');
         }

         var1.append(this.chain[var2]);
      }

      var1.append(']');
      return var1.toString();
   }
}
