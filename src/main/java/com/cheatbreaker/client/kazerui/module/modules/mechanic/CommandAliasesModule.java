package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public final class CommandAliasesModule extends com.cheatbreaker.client.kazerui.module.KazerModule {
   private final List<com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a> aliases = new ArrayList<>();
   private String loadedIds;
   private final com.cheatbreaker.client.kazerui.module.settings.ButtonSetting addButton = this.a(new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting("add", this::q));

   CommandAliasesModule() {
      super("CHAT_COMMAND_ALIASES_CHILD", false);
      this.o();
      com.cheatbreaker.client.kazerui.module.OutgoingChatEvent.a(this::a);
   }

   private List<String> n() {
      ArrayList var1 = new ArrayList();
      String var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.b(), "aliases", "");

      for (String var6 : var2.split(",")) {
         if (!var6.isEmpty()) {
            var1.add(var6);
         }
      }

      return var1;
   }

   private void o() {
      this.loadedIds = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.b(), "aliases", "");

      for (com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a var2 : this.aliases) {
         var2.c();
      }

      this.aliases.clear();

      for (String var4 : this.n()) {
         if (var4.matches("alias[0-9]{1,6}\\.") && this.aliases.size() < 100) {
            this.aliases.add(new com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a(var4));
         }
      }
   }

   @Override
   public void a(com.cheatbreaker.client.kazerui.overlay.OverlayPhase.a var1) {
      if (var1.phase == com.cheatbreaker.client.kazerui.overlay.OverlayPhase.b.END
         && !com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.b(), "aliases", "").equals(this.loadedIds)) {
         this.o();
      }
   }

   private void p() {
      StringBuilder var1 = new StringBuilder();

      for (com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a var3 : this.aliases) {
         if (var1.length() > 0) {
            var1.append(',');
         }

         var1.append(var3.prefix);
      }

      com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(this.b(), "aliases", var1.toString());
      this.loadedIds = var1.toString();
   }

   private void q() {
      if (this.aliases.size() < 100) {
         int var1 = 0;

         for (com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a var3 : this.aliases) {
            var1 = Math.max(var1, Integer.parseInt(var3.prefix.substring(5, var3.prefix.length() - 1)) + 1);
         }

         this.aliases.add(new com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a("alias" + var1 + "."));
         this.p();
      }
   }

   private void a(com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a var1) {
      this.aliases.remove(var1);
      var1.c();
      this.p();
   }

   @Override
   protected void a(com.cheatbreaker.client.kazerui.module.OptionTreeBuilder var1) {
      for (com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a var3 : this.aliases) {
         var1.a(var3.active, var1x -> var1x.a(var3.alias, var3.command, var3.caseSensitive, var3.remove));
      }

      var1.a(this.addButton);
   }

   private static String e(String var0) {
      if (var0 == null) {
         var0 = "";
      }

      var0 = var0.trim();
      if (!var0.isEmpty() && var0.charAt(0) != '/') {
         var0 = "/" + var0;
      }

      return var0.trim();
   }

   private void a(com.cheatbreaker.client.kazerui.module.OutgoingChatEvent var1) {
      if (this.a() && !var1.cancelled && Minecraft.getMinecraft().thePlayer != null && var1.a().startsWith("/")) {
         String var2 = this.f(var1.a());
         if (var2 != null && this.f(var2) == null) {
            var1.a(var2);
         }
      }
   }

   private String f(String var1) {
      for (com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.a var3 : this.aliases) {
         if (var3.active.b()) {
            String var4 = var3.a();
            String var5 = var3.b();
            if (!var4.isEmpty() && !var5.isEmpty()) {
               String var6 = a(var1, var4, var5, var3.caseSensitive.b());
               if (var6 != null) {
                  return var6;
               }
            }
         }
      }

      return null;
   }

   static String a(String var0, String var1, String var2, boolean var3) {
      if (var0 != null && var1 != null && var2 != null && !var1.isEmpty() && !var2.isEmpty() && var0.startsWith("/")) {
         int var4 = 0;

         while (var4 < var0.length() && !Character.isWhitespace(var0.charAt(var4))) {
            var4++;
         }

         return var4 == var1.length() && var0.regionMatches(!var3, 0, var1, 0, var4) ? var2 + var0.substring(var4) : null;
      } else {
         return null;
      }
   }

   private final class a {
      final String prefix;
      final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting active;
      final com.cheatbreaker.client.kazerui.module.settings.StringSetting alias;
      final com.cheatbreaker.client.kazerui.module.settings.StringSetting command;
      final com.cheatbreaker.client.kazerui.module.settings.ToggleSetting caseSensitive;
      final com.cheatbreaker.client.kazerui.module.settings.ButtonSetting remove;

      a(String var2) {
         this.prefix = var2;
         this.alias = CommandAliasesModule.this.a(new com.cheatbreaker.client.kazerui.module.settings.StringSetting(var2 + "alias")).d("alias");
         this.command = CommandAliasesModule.this.a(new com.cheatbreaker.client.kazerui.module.settings.StringSetting(var2 + "command")).d("command");
         this.caseSensitive = CommandAliasesModule.this.a(new com.cheatbreaker.client.kazerui.module.settings.ToggleSetting(var2 + "caseSensitive", false)).d("caseSensitive");
         this.active = CommandAliasesModule.this.a(new com.cheatbreaker.client.kazerui.module.settings.ToggleSetting(var2 + "active", true)).a(this::d);
         this.remove = CommandAliasesModule.this.a(new com.cheatbreaker.client.kazerui.module.settings.ButtonSetting(var2 + "remove", () -> CommandAliasesModule.this.a(this))).d("remove");
      }

      private String d() {
         String var1 = this.a();
         String var2 = this.b();
         if (var2.isEmpty() && var1.isEmpty()) {
            return CommandAliasesModule.this.a("empty", new Object[0]);
         } else {
            String var3 = this.caseSensitive.b() ? " (" + CommandAliasesModule.this.a("caseSensitive", new Object[0]) + ")" : "";
            return var1 + " -> " + var2 + var3;
         }
      }

      String a() {
         return com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.e(this.alias.j());
      }

      String b() {
         return com.cheatbreaker.client.kazerui.module.modules.mechanic.CommandAliasesModule.e(this.command.j());
      }

      void c() {
         CommandAliasesModule.this.b(this.active);
         CommandAliasesModule.this.b(this.alias);
         CommandAliasesModule.this.b(this.command);
         CommandAliasesModule.this.b(this.caseSensitive);
         CommandAliasesModule.this.b(this.remove);
      }
   }
}
