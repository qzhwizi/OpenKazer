package com.twelvemonkeys.imageio.util;

public final class SequenceSupport {
   private int index = -1;

   public void reset() {
      this.index = -1;
   }

   public void start() {
      if (this.index >= 0) {
         throw new IllegalStateException("prepareWriteSequence already invoked");
      } else {
         this.index = 0;
      }
   }

   public int advance() {
      if (this.index < 0) {
         throw new IllegalStateException("prepareWriteSequence not invoked");
      } else {
         return this.index++;
      }
   }

   public int current() {
      return this.index;
   }

   public int end() {
      if (this.index < 0) {
         throw new IllegalStateException("prepareWriteSequence not invoked");
      } else {
         int var1 = this.index;
         this.index = -1;
         return var1;
      }
   }
}
