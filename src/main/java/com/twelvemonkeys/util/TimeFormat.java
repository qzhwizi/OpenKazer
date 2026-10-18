package com.twelvemonkeys.util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.StringTokenizer;
import java.util.Vector;

public class TimeFormat extends Format {
   static final String MINUTE = "m";
   static final String SECOND = "s";
   static final String TIME = "S";
   static final String ESCAPE = "\\";
   private static final TimeFormat DEFAULT_FORMAT = new TimeFormat("m:ss");
   protected String formatString = null;
   protected TimeFormatter[] formatter;

   static void main(String[] var0) {
      Object var1 = null;
      TimeFormat var2 = null;
      TimeFormat var3 = null;
      if (var0.length >= 3) {
         System.out.println("Creating out TimeFormat: \"" + var0[2] + "\"");
         var3 = new TimeFormat(var0[2]);
      }

      if (var0.length >= 2) {
         System.out.println("Creating in TimeFormat: \"" + var0[1] + "\"");
         var2 = new TimeFormat(var0[1]);
      } else {
         System.out.println("Using default format for in");
         var2 = DEFAULT_FORMAT;
      }

      if (var3 == null) {
         var3 = var2;
      }

      if (var0.length >= 1) {
         System.out.println("Parsing: \"" + var0[0] + "\" with format \"" + var2.formatString + "\"");
         var1 = var2.parse(var0[0]);
      } else {
         var1 = new Time();
      }

      System.out.println("Time is \"" + var3.format((Time)var1) + "\" according to format \"" + var3.formatString + "\"");
   }

   public TimeFormat(String var1) {
      this.formatString = var1;
      Vector var2 = new Vector();
      StringTokenizer var3 = new StringTokenizer(var1, "\\msS", true);
      String var4 = null;
      Object var5 = null;
      int var6 = 0;

      while (var3.hasMoreElements()) {
         var5 = var3.nextToken();
         if (var4 != null && var4.equals("\\")) {
            var5 = (var5 != null ? var5 : "") + (var3.hasMoreElements() ? var3.nextToken() : "");
            var4 = null;
            var6 = 0;
         }

         if (var4 != null && !var4.equals(var5)) {
            if (var4.equals("m")) {
               var2.add(new MinutesFormatter(var6));
            } else if (var4.equals("s")) {
               var2.add(new SecondsFormatter(var6));
            } else if (var4.equals("S")) {
               var2.add(new SecondsFormatter(-1));
            } else {
               var2.add(new TextFormatter(var4));
            }

            var6 = 1;
            var4 = (String)var5;
         } else {
            var6++;
            var4 = (String)var5;
         }
      }

      if (var4 != null) {
         if (var4.equals("m")) {
            var2.add(new MinutesFormatter(var6));
         } else if (var4.equals("s")) {
            var2.add(new SecondsFormatter(var6));
         } else if (var4.equals("S")) {
            var2.add(new SecondsFormatter(-1));
         } else {
            var2.add(new TextFormatter(var4));
         }
      }

      this.formatter = (TimeFormatter[]) var2.toArray(new TimeFormatter[var2.size()]);
   }

   public static TimeFormat getInstance() {
      return DEFAULT_FORMAT;
   }

   public String getFormatString() {
      return this.formatString;
   }

   @Override
   public StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3) {
      if (!(var1 instanceof Time)) {
         throw new IllegalArgumentException("Must be instance of " + Time.class);
      } else {
         return var2.append(this.format(var1));
      }
   }

   public String format(Time var1) {
      StringBuilder var2 = new StringBuilder();

      for (int var3 = 0; var3 < this.formatter.length; var3++) {
         var2.append(this.formatter[var3].format(var1));
      }

      return var2.toString();
   }

   @Override
   public Object parseObject(String var1, ParsePosition var2) {
      Time var3 = this.parse(var1);
      var2.setIndex(var1.length());
      return var3;
   }

   public Time parse(String var1) {
      Time var2 = new Time();
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      boolean var7 = false;
      int var8 = 0;

      while (var8 < this.formatter.length && var5 + var6 < var1.length()) {
         label70: {
            var5 += var6;
            if (this.formatter[var8] instanceof MinutesFormatter) {
               if (var8 + 1 < this.formatter.length && this.formatter[var8 + 1] instanceof TextFormatter) {
                  var6 = var1.indexOf(((TextFormatter)this.formatter[var8 + 1]).text, var5);
                  if (var6 < 0) {
                     var6 = var1.length();
                  }
               } else if (var8 + 1 >= this.formatter.length) {
                  var6 = var1.length();
               } else {
                  var6 = this.formatter[var8].digits;
               }

               if (var6 > var5) {
                  var4 = Integer.parseInt(var1.substring(var5, var6));
               }
            } else if (this.formatter[var8] instanceof SecondsFormatter) {
               if (this.formatter[var8].digits == -1) {
                  if (var8 + 1 < this.formatter.length && this.formatter[var8 + 1] instanceof TextFormatter) {
                     var6 = var1.indexOf(((TextFormatter)this.formatter[var8 + 1]).text, var5);
                     break label70;
                  }

                  if (var8 + 1 >= this.formatter.length) {
                     var6 = var1.length();
                     break label70;
                  }

                  var6 = 0;
               } else {
                  if (var8 + 1 < this.formatter.length && this.formatter[var8 + 1] instanceof TextFormatter) {
                     var6 = var1.indexOf(((TextFormatter)this.formatter[var8 + 1]).text, var5);
                  } else if (var8 + 1 >= this.formatter.length) {
                     var6 = var1.length();
                  } else {
                     var6 = this.formatter[var8].digits;
                  }

                  var3 = Integer.parseInt(var1.substring(var5, var6));
               }
            } else if (this.formatter[var8] instanceof TextFormatter) {
               var6 = this.formatter[var8].digits;
            }

            var8++;
            continue;
         }

         var3 = Integer.parseInt(var1.substring(var5, var6));
         var7 = true;
         break;
      }

      if (!var7) {
         var2.setMinutes(var4);
      }

      var2.setSeconds(var3);
      return var2;
   }
}
