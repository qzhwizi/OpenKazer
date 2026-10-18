package com.twelvemonkeys.lang;

import java.util.Date;
import java.util.TimeZone;

public final class DateUtil {
   public static final long SECOND = 1000L;
   public static final long MINUTE = 60000L;
   public static final long HOUR = 3600000L;
   public static final long DAY = 86400000L;
   public static final long CALENDAR_YEAR = 31556952000L;

   private DateUtil() {
   }

   public static long delta(long var0) {
      return System.currentTimeMillis() - var0;
   }

   public static long delta(Date var0) {
      return System.currentTimeMillis() - var0.getTime();
   }

   public static long currentTimeSecond() {
      return roundToSecond(System.currentTimeMillis());
   }

   public static long currentTimeMinute() {
      return roundToMinute(System.currentTimeMillis());
   }

   public static long currentTimeHour() {
      return roundToHour(System.currentTimeMillis());
   }

   public static long currentTimeDay() {
      return roundToDay(System.currentTimeMillis());
   }

   public static long roundToSecond(long var0) {
      return var0 / 1000L * 1000L;
   }

   public static long roundToMinute(long var0) {
      return var0 / 60000L * 60000L;
   }

   public static long roundToHour(long var0) {
      return roundToHour(var0, TimeZone.getDefault());
   }

   public static long roundToHour(long var0, TimeZone var2) {
      int var3 = var2.getOffset(var0);
      return var0 / 3600000L * 3600000L - var3;
   }

   public static long roundToDay(long var0) {
      return roundToDay(var0, TimeZone.getDefault());
   }

   public static long roundToDay(long var0, TimeZone var2) {
      int var3 = var2.getOffset(var0);
      return (var0 + var3) / 86400000L * 86400000L - var3;
   }
}
