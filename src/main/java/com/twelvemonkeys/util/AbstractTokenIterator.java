package com.twelvemonkeys.util;

public abstract class AbstractTokenIterator implements TokenIterator {
   @Override
   public void remove() {
      throw new UnsupportedOperationException("remove");
   }

   @Override
   public final boolean hasMoreTokens() {
      return this.hasNext();
   }

   @Override
   public final String nextToken() {
      return this.next();
   }

   @Override
   public final boolean hasMoreElements() {
      return this.hasNext();
   }

   public final String nextElement() {
      return this.next();
   }
}
