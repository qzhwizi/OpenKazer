package com.cheatbreaker.client.network.gamemode;

public enum GameModeRule {
   COMPETITIVE_GAMEMODE("competitiveGame", Boolean.class);

   private final String ruleName;
   private final Class value;

   public static com.cheatbreaker.client.network.gamemode.GameModeRule a(String var0) {
      com.cheatbreaker.client.network.gamemode.GameModeRule var1 = null;

      for (com.cheatbreaker.client.network.gamemode.GameModeRule var5 : values()) {
         if (var5.a().equals(var0)) {
            var1 = var5;
         }
      }

      return var1;
   }

   private GameModeRule(String var3, Class var4) {
      this.ruleName = var3;
      this.value = var4;
   }

   public String a() {
      return this.ruleName;
   }

   public Class b() {
      return this.value;
   }
}
