package com.cheatbreaker.client.module.modules;

import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ClockFormatter {
   private static final DateTimeFormatter TWENTY_FOUR_HOUR_WITH_SECONDS = DateTimeFormatter.ofPattern("HH:mm:ss");
   private static final DateTimeFormatter TWENTY_FOUR_HOUR = DateTimeFormatter.ofPattern("HH:mm");
   private static final DateTimeFormatter TWELVE_HOUR_WITH_SECONDS = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.ENGLISH);
   private static final DateTimeFormatter TWELVE_HOUR = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
   private final Clock clock;
   private long cachedSecond;
   private boolean cachedTwentyFourHour;
   private boolean cachedSeconds;
   private String cachedText;

   public ClockFormatter(Clock var1) {
      this.clock = var1;
   }

   public String a(boolean var1, boolean var2) {
      long var3 = Math.floorDiv(this.clock.millis(), 1000L);
      if (this.cachedText != null && var3 == this.cachedSecond && this.cachedTwentyFourHour == var1 && this.cachedSeconds == var2) {
         return this.cachedText;
      } else {
         DateTimeFormatter var5;
         if (var1) {
            var5 = var2 ? TWENTY_FOUR_HOUR_WITH_SECONDS : TWENTY_FOUR_HOUR;
         } else {
            var5 = var2 ? TWELVE_HOUR_WITH_SECONDS : TWELVE_HOUR;
         }

         this.cachedSecond = var3;
         this.cachedTwentyFourHour = var1;
         this.cachedSeconds = var2;
         return this.cachedText = Instant.ofEpochSecond(var3).atZone(this.clock.getZone()).toLocalTime().format(var5);
      }
   }
}
