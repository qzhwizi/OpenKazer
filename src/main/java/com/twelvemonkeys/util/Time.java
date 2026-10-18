package com.twelvemonkeys.util;

public class Time {
   private int time = -1;
   public static final int SECONDS_IN_MINUTE = 60;

   public Time() {
      this(0);
   }

   public Time(int var1) {
      this.setTime(var1);
   }

   public void setTime(int var1) {
      if (var1 < 0) {
         throw new IllegalArgumentException("Time argument must be 0 or positive!");
      } else {
         this.time = var1;
      }
   }

   public int getTime() {
      return this.time;
   }

   public long getTimeInMillis() {
      return this.time * 1000L;
   }

   public void setSeconds(int var1) {
      this.time = this.getMinutes() * 60 + var1;
   }

   public int getSeconds() {
      return this.time % 60;
   }

   public void setMinutes(int var1) {
      this.time = var1 * 60 + this.getSeconds();
   }

   public int getMinutes() {
      return this.time / 60;
   }

   @Override
   public String toString() {
      return this.getMinutes() + ":" + (this.getSeconds() < 10 ? "0" : "") + this.getSeconds();
   }

   @Deprecated
   public String toString(String var1) {
      TimeFormat var2 = new TimeFormat(var1);
      return var2.format(this);
   }

   @Deprecated
   public static Time parseTime(String var0) {
      TimeFormat var1 = TimeFormat.getInstance();
      return var1.parse(var0);
   }
}
