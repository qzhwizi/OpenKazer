package com.twelvemonkeys.util.regex;

import com.twelvemonkeys.util.AbstractTokenIterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTokenIterator extends AbstractTokenIterator {
   private final Matcher matcher;
   private boolean next = false;

   public RegExTokenIterator(String var1) {
      this(var1, "\\S+");
   }

   public RegExTokenIterator(String var1, String var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("string == null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("pattern == null");
      } else {
         this.matcher = Pattern.compile(var2).matcher(var1);
      }
   }

   @Override
   public void reset() {
      this.matcher.reset();
   }

   @Override
   public boolean hasNext() {
      return this.next || (this.next = this.matcher.find());
   }

   public String next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         this.next = false;
         return this.matcher.group();
      }
   }
}
