package com.twelvemonkeys.util;

import com.twelvemonkeys.lang.StringUtil;

class SecondsFormatter extends TimeFormatter {
   SecondsFormatter(int var1) {
      this.digits = var1;
   }

   @Override
   String format(Time var1) {
      if (this.digits < 0) {
         return Integer.toString(var1.getTime());
      } else {
         return var1.getSeconds() >= Math.pow(10.0, this.digits)
            ? Integer.toString(var1.getSeconds())
            : StringUtil.pad(String.valueOf(var1.getSeconds()), this.digits, "0", true);
      }
   }
}
