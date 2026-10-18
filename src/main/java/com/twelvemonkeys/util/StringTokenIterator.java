package com.twelvemonkeys.util;

import java.util.NoSuchElementException;

public class StringTokenIterator extends AbstractTokenIterator {
   private final String string;
   private final char[] delimiters;
   private int position;
   private final int maxPosition;
   private String next;
   private String nextDelimiter;
   private final boolean includeDelimiters;
   private final boolean includeEmpty;
   private final boolean reverse;
   public static final int FORWARD = 1;
   public static final int REVERSE = -1;
   private final char maxDelimiter;

   public StringTokenIterator(String var1) {
      this(var1, " \t\n\r\f".toCharArray(), 1, false, false);
   }

   public StringTokenIterator(String var1, String var2) {
      this(var1, toCharArray(var2), 1, false, false);
   }

   public StringTokenIterator(String var1, String var2, int var3) {
      this(var1, toCharArray(var2), var3, false, false);
   }

   public StringTokenIterator(String var1, String var2, boolean var3) {
      this(var1, toCharArray(var2), 1, var3, false);
   }

   public StringTokenIterator(String var1, String var2, int var3, boolean var4, boolean var5) {
      this(var1, toCharArray(var2), var3, var4, var5);
   }

   private StringTokenIterator(String var1, char[] var2, int var3, boolean var4, boolean var5) {
      if (var1 == null) {
         throw new IllegalArgumentException("string == null");
      } else {
         this.string = var1;
         this.maxPosition = var1.length();
         this.delimiters = var2;
         this.includeDelimiters = var4;
         this.reverse = var3 == -1;
         this.includeEmpty = var5;
         this.maxDelimiter = initMaxDelimiter(var2);
         this.reset();
      }
   }

   private static char[] toCharArray(String var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("delimiters == null");
      } else {
         return var0.toCharArray();
      }
   }

   private static char initMaxDelimiter(char[] var0) {
      if (var0 == null) {
         return '\u0000';
      } else {
         char var1 = 0;

         for (char var5 : var0) {
            if (var1 < var5) {
               var1 = var5;
            }
         }

         return var1;
      }
   }

   @Override
   public void reset() {
      this.position = 0;
      this.next = null;
      this.nextDelimiter = null;
   }

   @Override
   public boolean hasNext() {
      return this.next != null || this.fetchNext() != null;
   }

   private String fetchNext() {
      if (this.nextDelimiter != null) {
         this.next = this.nextDelimiter;
         this.nextDelimiter = null;
         return this.next;
      } else if (this.position >= this.maxPosition) {
         return null;
      } else {
         return this.reverse ? this.fetchReverse() : this.fetchForward();
      }
   }

   private String fetchReverse() {
      int var1 = this.scanForPrev();
      this.next = this.string.substring(var1 + 1, this.maxPosition - this.position);
      if (this.includeDelimiters && var1 >= 0 && var1 < this.maxPosition) {
         this.nextDelimiter = this.string.substring(var1, var1 + 1);
      }

      this.position = this.maxPosition - var1;
      return this.next.length() == 0 && !this.includeEmpty ? this.fetchNext() : this.next;
   }

   private String fetchForward() {
      int var1 = this.scanForNext();
      this.next = this.string.substring(this.position, var1);
      if (this.includeDelimiters && var1 >= 0 && var1 < this.maxPosition) {
         this.nextDelimiter = this.string.substring(var1, var1 + 1);
      }

      this.position = ++var1;
      return this.next.length() == 0 && !this.includeEmpty ? this.fetchNext() : this.next;
   }

   private int scanForNext() {
      int var1;
      for (var1 = this.position; var1 < this.maxPosition; var1++) {
         char var2 = this.string.charAt(var1);
         if (var2 <= this.maxDelimiter) {
            for (char var6 : this.delimiters) {
               if (var2 == var6) {
                  return var1;
               }
            }
         }
      }

      return var1;
   }

   private int scanForPrev() {
      int var1;
      for (var1 = this.maxPosition - 1 - this.position; var1 >= 0; var1--) {
         char var2 = this.string.charAt(var1);
         if (var2 <= this.maxDelimiter) {
            for (char var6 : this.delimiters) {
               if (var2 == var6) {
                  return var1;
               }
            }
         }
      }

      return var1;
   }

   public String next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         String var1 = this.next;
         this.next = this.fetchNext();
         return var1;
      }
   }
}
