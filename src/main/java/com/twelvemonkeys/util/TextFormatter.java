package com.twelvemonkeys.util;

class TextFormatter extends TimeFormatter {
   String text = null;

   TextFormatter(String var1) {
      this.text = var1;
      if (var1 != null) {
         this.digits = var1.length();
      }
   }

   @Override
   String format(Time var1) {
      return this.text;
   }
}
