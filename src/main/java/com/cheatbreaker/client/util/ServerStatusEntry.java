package com.cheatbreaker.client.util;

public class ServerStatusEntry {
   private String type;
   private String url;
   private com.cheatbreaker.client.util.ServerStatusEntry.a status = com.cheatbreaker.client.util.ServerStatusEntry.a.UNKNOWN;

   public ServerStatusEntry(String var1, String var2) {
      this.type = var1;
      this.url = var2;
   }

   public com.cheatbreaker.client.util.ServerStatusEntry.a a() {
      return this.status;
   }

   public void a(com.cheatbreaker.client.util.ServerStatusEntry.a var1) {
      this.status = var1;
   }

   public String b() {
      return this.type;
   }

   public String c() {
      return this.url;
   }

   public static enum a {
      UP("UP", 0, "green"),
      DOWN("DOWN", 1, "red"),
      BUSY("BUSY", 2, "yellow"),
      UNKNOWN("UNKNOWN", 3, "unknown");

      private final String type;
      private final int id;
      private final String color;

      public String a() {
         return this.color;
      }

      public String b() {
         return this.type;
      }

      public int c() {
         return this.id;
      }

      private a(String var3, int var4, String var5) {
         this.type = var3;
         this.id = var4;
         this.color = var5;
      }

      public static com.cheatbreaker.client.util.ServerStatusEntry.a a(String var0) {
         for (com.cheatbreaker.client.util.ServerStatusEntry.a var4 : values()) {
            if (var4.a().equalsIgnoreCase(var0)) {
               return var4;
            }
         }

         return null;
      }
   }
}
