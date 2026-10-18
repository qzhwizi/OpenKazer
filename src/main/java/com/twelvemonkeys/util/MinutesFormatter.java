package com.twelvemonkeys.util;

import com.twelvemonkeys.lang.StringUtil;

class MinutesFormatter extends TimeFormatter {
   MinutesFormatter(int var1) {
      this.digits = var1;
   }

   @Override
   String format(Time var1) {
      return var1.getMinutes() >= Math.pow(10.0, this.digits)
         ? Integer.toString(var1.getMinutes())
         : StringUtil.pad(String.valueOf(var1.getMinutes()), this.digits, "0", true);
   }
}
