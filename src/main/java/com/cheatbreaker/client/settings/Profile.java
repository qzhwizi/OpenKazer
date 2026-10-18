package com.cheatbreaker.client.settings;

public class Profile {
   private int index = 0;
   private final boolean editable;
   private String name;

   public Profile(String var1, boolean var2) {
      this.name = var1;
      this.editable = var2;
   }

   public int a() {
      return this.index;
   }

   public boolean b() {
      return this.editable;
   }

   public String c() {
      return this.name;
   }

   public void a(int var1) {
      this.index = var1;
   }

   public void a(String var1) {
      this.name = var1;
   }
}
