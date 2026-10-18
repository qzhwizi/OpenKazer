package com.twelvemonkeys.net;

import com.twelvemonkeys.lang.DateUtil;
import com.twelvemonkeys.lang.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HTTPUtil {
   private static final SimpleDateFormat HTTP_RFC1123_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
   private static final SimpleDateFormat HTTP_RFC850_FORMAT = new SimpleDateFormat("EEE, dd-MMM-yy HH:mm:ss z", Locale.US);
   private static final SimpleDateFormat HTTP_ASCTIME_FORMAT = new SimpleDateFormat("EEE MMM d HH:mm:ss yy", Locale.US);
   private static long sNext50YearWindowChange = DateUtil.currentTimeDay();

   private static void update50YearWindowIfNeeded() {
      long var0 = sNext50YearWindowChange;
      if (var0 < System.currentTimeMillis()) {
         var0 += 86400000L;
         sNext50YearWindowChange = var0;
         Date var2 = new Date(var0 - 1577847600000L);
         synchronized (HTTP_RFC850_FORMAT) {
            HTTP_RFC850_FORMAT.set2DigitYearStart(var2);
         }

         synchronized (HTTP_ASCTIME_FORMAT) {
            HTTP_ASCTIME_FORMAT.set2DigitYearStart(var2);
         }
      }
   }

   private HTTPUtil() {
   }

   public static String formatHTTPDate(long var0) {
      return formatHTTPDate(new Date(var0));
   }

   public static String formatHTTPDate(Date var0) {
      synchronized (HTTP_RFC1123_FORMAT) {
         return HTTP_RFC1123_FORMAT.format(var0);
      }
   }

   public static long parseHTTPDate(String var0) throws NumberFormatException {
      return parseHTTPDateImpl(var0).getTime();
   }

   private static Date parseHTTPDateImpl(String var0) throws NumberFormatException {
      if (var0 == null) {
         throw new IllegalArgumentException("date == null");
      } else if (StringUtil.isEmpty(var0)) {
         throw new NumberFormatException("Invalid HTTP date: \"" + var0 + "\"");
      } else {
         SimpleDateFormat var1;
         if (var0.indexOf(45) >= 0) {
            var1 = HTTP_RFC850_FORMAT;
            update50YearWindowIfNeeded();
         } else if (var0.indexOf(44) < 0) {
            var1 = HTTP_ASCTIME_FORMAT;
            update50YearWindowIfNeeded();
         } else {
            var1 = HTTP_RFC1123_FORMAT;
         }

         Date var2;
         try {
            synchronized (var1) {
               var2 = var1.parse(var0);
            }
         } catch (ParseException var6) {
            NumberFormatException var4 = new NumberFormatException("Invalid HTTP date: \"" + var0 + "\"");
            var4.initCause(var6);
            throw var4;
         }

         if (var2 == null) {
            throw new NumberFormatException("Invalid HTTP date: \"" + var0 + "\"");
         } else {
            return var2;
         }
      }
   }

   static {
      HTTP_RFC1123_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
      HTTP_RFC850_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
      HTTP_ASCTIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
      update50YearWindowIfNeeded();
   }
}
