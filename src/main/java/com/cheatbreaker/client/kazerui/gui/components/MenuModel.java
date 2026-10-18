package com.cheatbreaker.client.kazerui.gui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class MenuModel {
   public final com.cheatbreaker.client.kazerui.gui.components.SettingsStore store;
   public String tab = "MODS";
   public String category = "ALL";
   public String settingsTab = "GENERAL";
   public String selected = "";
   public String search = "";
   public String input = "";
   public String toast = "";
   public com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory cosmeticCategory = com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.CLOAKS;
   public int cosmeticPreview = 900001;
   public boolean searchFocused;
   public boolean searchSelected;
   public boolean closeRequested;
   public boolean editHudRequested;
   public boolean captureKey;
   public String captureTarget = "menu";
   public final com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics scroller = new com.cheatbreaker.client.kazerui.gui.components.ScrollPhysics();
   public long revision;
   public long toastUntil;
   public final List<String> profiles = new ArrayList<>();
   public String keybindPopup = "";
   public String resetBindingRequested = "";
   public String keyListening = "";
   public String textEditing = "";
   public int textMax;
   public boolean creatingProfile;
   public int profileOffset;
   public String hexEditing = "";
   public boolean hexAlpha = true;
   public String sliderHeld = "";
   public boolean inWorld;
   public float clickX;
   public float clickY;
   public String editor = "";
   public String editorName = "";
   public String editorServer = "";
   public String editorIcon = "";
   public String editorFocus = "";
   public boolean editorNameError;
   public boolean editorServerError;
   public float[] iconMenu;
   public String confirm = "";
   public static final String DEFAULT_PROFILE = "Default";
   private static final List<String> OLD_PRESETS = Arrays.asList("UHC", "Hypixel Skyblock", "Arena PvP");
   public static final String[][] PROFILE_ICONS = new String[][]{
      {"", "None"}, {"clock", "Clock"}, {"apple", "Apple"}, {"hypixel", "Hypixel"}, {"crossed-swords", "Swords"}, {"mouse", "Mouse"}
   };
   public static final String[] SORTS = new String[]{"last_modified", "alphabetical", "custom"};
   private static final String[] SORT_KEYS = new String[]{"lastModified", "alphabetical", "custom"};
   public final Set<String> expanded = new HashSet<>();
   private long modulesRevision = Long.MIN_VALUE;
   private String modulesSearch;
   private String modulesCategory;
   private String modulesProfile;
   private List<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a> modulesCache;

   public MenuModel(com.cheatbreaker.client.kazerui.gui.components.SettingsStore var1) {
      this.store = var1;
      String[] var2 = var1.a("profiles", "Default").split("\\|", -1);

      for (String var6 : var2) {
         if (!var6.trim().isEmpty() && !this.profiles.contains(var6)) {
            this.profiles.add(var6);
         }
      }

      if (!this.profiles.contains("Default")) {
         this.profiles.add(0, "Default");
      }

      for (String var10 : OLD_PRESETS) {
         if (!var1.a("bundled." + var10, "").isEmpty()) {
            var1.a("bundled." + var10);
            if (this.profiles.remove(var10)) {
               this.d(var10);

               for (String var8 : new String[]{"profileDisplay.", "profileIcon.", "profileServer."}) {
                  var1.a(var8 + var10);
               }
            }
         }
      }

      this.i();
      if (!this.profiles.contains(this.a())) {
         var1.b("activeProfile", "Default");
      }
   }

   public String a() {
      return this.store.a("activeProfile", "Default");
   }

   public String a(String var1) {
      return this.store.a("profileDisplay." + var1, var1);
   }

   public String b(String var1) {
      return this.store.a("profileIcon." + var1, "");
   }

   public String c(String var1) {
      return this.store.a("profileServer." + var1, "");
   }

   private void i() {
      this.store.b("profiles", String.join("|", this.profiles));
   }

   public void d(String var1) {
      String var2 = "profile." + var1 + ".";

      for (String var4 : new ArrayList<>(this.store.a())) {
         if (var4.startsWith(var2)) {
            this.store.a(var4);
         }
      }

      this.revision++;
   }

   private void o(String var1) {
      this.creatingProfile = false;
      this.editor = var1;
      this.editorName = this.a(var1);
      this.editorServer = this.c(var1);
      this.editorIcon = this.b(var1);
      this.editorFocus = "";
      this.editorNameError = false;
      this.editorServerError = false;
      this.iconMenu = null;
      this.confirm = "";
   }

   private void j() {
      this.editor = "";
      this.editorFocus = "";
      this.iconMenu = null;
      this.confirm = "";
      this.creatingProfile = false;
   }

   private void k() {
      int var1 = 1;

      while (this.profiles.contains("Profile " + var1)) {
         var1++;
      }

      this.o("Profile " + var1);
      this.creatingProfile = true;
      this.editorName = this.editor;
      this.editorFocus = "name";
   }

   public void a(int var1) {
      this.profileOffset = Math.max(0, Math.min(Math.max(0, this.profiles.size() - 13), this.profileOffset + var1));
      this.revision++;
   }

   public boolean e(String var1) {
      return !var1.equals("Default");
   }

   private void a(boolean var1) {
      String var2 = this.confirm;
      this.confirm = "";
      if (var2.equals("backConfirm")) {
         this.j();
      } else if (var1) {
         String var3 = this.editor;
         if (var2.equals("deleteConfirm")) {
            if (var3.equals(this.a())) {
               this.store.b("activeProfile", "Default");
            }

            this.d(var3);

            for (String var7 : new String[]{"profileDisplay.", "profileIcon.", "profileServer."}) {
               this.store.a(var7 + var3);
            }

            this.profiles.remove(var3);
            this.i();
            this.j();
            this.tab = "MODS";
            this.selected = "";
         } else if (var2.equals("resetProfile")) {
            this.d(var3);
            this.j();
            this.tab = "MODS";
            this.selected = "";
         }
      }
   }

   public void b() {
      this.toast = "";
      this.toastUntil = 0L;
      this.tab = "MODS";
      this.category = "ALL";
      this.settingsTab = "GENERAL";
      this.selected = "";
      this.cosmeticCategory = com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.CLOAKS;
      this.j();
      this.expanded.clear();
      this.keybindPopup = "";
      this.keyListening = "";
      this.textEditing = "";
      this.hexEditing = "";
      this.sliderHeld = "";
      this.h();
      this.revision++;
   }

   public boolean c() {
      return this.tab.equals("COSMETICS") && this.selected.isEmpty() && this.editor.isEmpty() && this.keybindPopup.isEmpty();
   }

   public String a(String var1, String var2) {
      if ((!var1.equals("global") || !var2.equals("showTabIcon")) && (!var1.equals("tab") || !var2.equals("tabLogo"))) {
         if (var1.equals("global") && var2.equals("showNametagIcon")) {
            return "profile." + this.a() + ".nametags.legacy_show_nametag_icon";
         } else if (var1.equals("zoom") && var2.equals("zoomKeybind")) {
            return "zoomKeyName";
         } else {
            return var1.equals("global") ? "settings." + var2 : "profile." + this.a() + "." + var1 + "." + var2;
         }
      } else {
         return "profile." + this.a() + ".nametags.legacy_show_kazer_icon_in_tab";
      }
   }

   public String a(String var1, String var2, String var3) {
      return this.store.a(this.a(var1, var2), var3);
   }

   public boolean a(String var1, String var2, boolean var3) {
      return Boolean.parseBoolean(this.a(var1, var2, "" + var3));
   }

   public float a(String var1, String var2, float var3) {
      try {
         float var4 = Float.parseFloat(this.a(var1, var2, "" + var3));
         return !Float.isNaN(var4) && !Float.isInfinite(var4) ? var4 : var3;
      } catch (NumberFormatException var5) {
         return var3;
      }
   }

   public void b(String var1, String var2, String var3) {
      this.store.b(this.a(var1, var2), var3);
      this.revision++;
      com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(var1, var2);
   }

   public boolean a(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1) {
      com.cheatbreaker.client.kazerui.module.KazerModule var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var1.id);
      if (var2 != null) {
         return var2.a();
      } else {
         var1.getClass();
         return this.a(var1.id, "enabled", com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a(var1.id));
      }
   }

   public boolean b(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1) {
      return Boolean.parseBoolean(this.store.a("favorite." + var1.id, "false"));
   }

   public boolean d() {
      return Boolean.parseBoolean(this.store.a("compact", "false"));
   }

   public int e() {
      String var1 = this.store.a("sortingOptions", "custom");

      for (int var2 = 0; var2 < SORTS.length; var2++) {
         if (SORTS[var2].equals(var1)) {
            return var2;
         }
      }

      return 2;
   }

   public static String b(int var0) {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", SORT_KEYS[var0]);
   }

   public void f(String var1) {
      this.toast = var1;
      this.toastUntil = System.currentTimeMillis() + 4500L;
      this.revision++;
   }

   public List<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a> f() {
      if (this.modulesCache != null
         && this.modulesRevision == this.revision
         && this.search.equals(this.modulesSearch)
         && this.category.equals(this.modulesCategory)
         && this.a().equals(this.modulesProfile)) {
         return this.modulesCache;
      } else {
         this.modulesRevision = this.revision;
         this.modulesSearch = this.search;
         this.modulesCategory = this.category;
         this.modulesProfile = this.a();
         this.modulesCache = Collections.unmodifiableList(this.l());
         return this.modulesCache;
      }
   }

   private List<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a> l() {
      ArrayList var1 = new ArrayList<>(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.ALL);
      int var2 = this.a("global", "disableModSorting", false) ? 2 : this.e();
      Collections.sort(var1, (var2x, var3x) -> {
         int var4x = Boolean.compare(this.b((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a) var3x), this.b((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a) var2x));
         if (var4x != 0) {
            return var4x;
         } else if (var2 == 2) {
            return 0;
         } else {
            return var2 == 1 ? d((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a) var2x).compareTo(d((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a) var3x)) : Long.compare(this.e((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a) var3x), this.e((com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a) var2x));
         }
      });
      ArrayList var3 = new ArrayList();
      if (this.search.isEmpty()) {
         for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var9 : (Iterable<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a>) var1) {
            if (this.c(var9)) {
               var3.add(var9);
            }
         }

         return var3;
      } else {
         String var4 = com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(this.search);

         for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var6 : (Iterable<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a>) var1) {
            if (this.c(var6) && a(var6, var4)) {
               var3.add(var6);
            }
         }

         if (Boolean.parseBoolean(this.store.a("searchOptions", "true"))) {
            for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var10 : (Iterable<com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a>) var1) {
               if (this.c(var10) && !var3.contains(var10) && b(var10, var4)) {
                  var3.add(var10);
               }
            }
         }

         return var3;
      }
   }

   private boolean c(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1) {
      return this.category.equals("ALL") || this.category.equals(var1.category);
   }

   private static String d(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var0) {
      return com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var0.id, var0.name);
   }

   private static boolean a(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var0, String var1) {
      String var2 = d(var0);
      if (!com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(var2).startsWith(var1) && !com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(var0.id).startsWith(var1)) {
         for (String var6 : var2.split(" ")) {
            if (com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(var6).startsWith(var1)) {
               return true;
            }
         }

         for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var10 : a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0.id).options)) {
            for (String var8 : com.cheatbreaker.client.kazerui.gui.components.KazerLang.b(var10.key, var10.key).split(" ")) {
               if (com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(var8).startsWith(var1)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static boolean b(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var0, String var1) {
      ArrayList var2 = new ArrayList<>(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0.id).options));

      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var4 : a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0.id).options)) {
         var2.addAll(com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var4.children));
      }

      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var10 : (Iterable<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a>) var2) {
         for (String var8 : (var10.text == null ? com.cheatbreaker.client.kazerui.gui.components.KazerLang.a("settings", var10.key) : var10.text).split(" ")) {
            if (com.cheatbreaker.client.kazerui.gui.components.MenuRenderer.a(var8).startsWith(var1)) {
               return true;
            }
         }
      }

      return false;
   }

   private static List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> a(List<com.cheatbreaker.client.kazerui.gui.components.OptionTree.a> var0) {
      ArrayList var1 = new ArrayList();

      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 : var0) {
         if (var3.type.equals("child")) {
            var1.add(var3);
         } else {
            var1.addAll(a(var3.children));
         }
      }

      return var1;
   }

   private long e(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var1) {
      try {
         return Long.parseLong(this.store.a("modified." + var1.id, "0"));
      } catch (NumberFormatException var3) {
         return 0L;
      }
   }

   void g(String var1) {
      this.tab = "MODS";
      this.selected = "";
      this.expanded.clear();
      this.keybindPopup = "";
      com.cheatbreaker.client.kazerui.module.KazerModule var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var1);

      ArrayList var3;
      for (var3 = new ArrayList(); var2 != null && var2.j() != null; var2 = var2.j()) {
         var3.add(var2.j().b() + "." + var2.id);
      }

      String var4 = var2 != null ? var2.b() : var1;

      for (com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var6 : com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.ALL) {
         if (var6.id.equals(var4)) {
            this.selected = var4;
            this.expanded.addAll(var3);
            return;
         }
      }
   }

   public void h(String var1) {
      com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.a var2 = com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.b(var1);
      var2.getClass();
      com.cheatbreaker.client.kazerui.module.KazerModule var3 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(var1);
      if (var3 != null) {
         var3.a(!var3.a());
      } else {
         this.b(var1, "enabled", "" + !this.a(var2));
      }

      this.p(var1);
      this.store.b();
   }

   private void p(String var1) {
      this.store.b("modified." + var1, "" + System.currentTimeMillis());
   }

   public void i(String var1) {
      this.revision++;
      if (!this.editor.isEmpty()) {
         this.q(var1);
         this.store.b();
      } else if (var1.equals("close")) {
         this.closeRequested = true;
      } else if (var1.equals("search")) {
         this.searchFocused = true;
      } else {
         this.searchFocused = false;
         if (var1.startsWith("tab:")) {
            this.tab = var1.substring(4);
            this.selected = "";
            this.h();
         } else if (var1.startsWith("category:")) {
            this.category = var1.substring(9);
            this.scroller.f();
         } else if (var1.startsWith("settings:")) {
            this.settingsTab = var1.substring(9);
            this.h();
         } else if (var1.startsWith("toggle:")) {
            this.h(var1.substring(7));
         } else if (var1.startsWith("options:")) {
            this.g(var1.substring(8));
            this.h();
         } else if (var1.startsWith("favorite:")) {
            String var2 = var1.substring(9);
            this.store.b("favorite." + var2, "" + !this.b(com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.b(var2)));
         } else if (var1.equals("compact")) {
            this.store.b("compact", "" + !this.d());
            this.scroller.f();
         } else if (var1.equals("inventoryBrand") || var1.equals("inventoryBrand:prev")) {
            String[] var25 = new String[]{"above", "bottomRight", "hidden"};
            String var31 = this.a("global", "inventoryBrand", "above");
            int var36 = 0;

            for (int var39 = 0; var39 < var25.length; var39++) {
               if (var25[var39].equals(var31)) {
                  var36 = var39;
               }
            }

            this.b("global", "inventoryBrand", var25[(var36 + (var1.endsWith(":prev") ? var25.length - 1 : 1)) % var25.length]);
         } else if (var1.equals("sort")) {
            this.store.b("sortingOptions", SORTS[(this.e() + 1) % SORTS.length]);
            this.scroller.f();
         } else if (var1.equals("sort:prev")) {
            this.store.b("sortingOptions", SORTS[(this.e() + SORTS.length - 1) % SORTS.length]);
            this.scroller.f();
         } else if (var1.equals("compact:prev")) {
            this.store.b("compact", "" + !this.d());
            this.scroller.f();
         } else if (var1.equals("back")) {
            this.selected = "";
            this.h();
            this.expanded.clear();
            this.keybindPopup = "";
         } else if (var1.startsWith("expand:")) {
            String var11 = var1.substring(7);
            if (!this.expanded.remove(var11)) {
               this.expanded.add(var11);
            }
         } else if (var1.startsWith("profile:")) {
            this.store.b("activeProfile", var1.substring(8));
         } else if (var1.startsWith("editProfile:")) {
            this.o(var1.substring(12));
         } else if (var1.equals("newProfile")) {
            this.k();
         } else if (var1.equals("editHud")) {
            this.editHudRequested = true;
         } else if (var1.startsWith("cosmeticCategory:")) {
            this.cosmeticCategory = com.cheatbreaker.client.kazerui.concurrent.CosmeticCategory.valueOf(var1.substring(17));
            this.h();
         } else if (var1.startsWith("previewCosmetic:")) {
            this.cosmeticPreview = Integer.parseInt(var1.substring(16));
         } else if (var1.startsWith("cosmetic:")) {
            try {
               com.cheatbreaker.client.kazerui.concurrent.CosmeticEntry var12 = com.cheatbreaker.client.kazerui.concurrent.CosmeticCatalog.a(Integer.parseInt(var1.substring(9)));
               if (var12 != null) {
                  this.cosmeticPreview = var12.id;
                  com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.b(var12);
               }
            } catch (NumberFormatException var10) {
            }
         } else if (var1.equals("cosmeticsClear")) {
            com.cheatbreaker.client.kazerui.concurrent.EquippedCosmetics.d();
         } else if (var1.startsWith("set:")) {
            String[] var13 = var1.split(":", 4);
            this.b(var13[1], var13[2], var13[3]);
            this.p(var13[1]);
         } else if (var1.startsWith("bool:")) {
            String[] var14 = var1.split(":");
            this.b(var14[1], var14[2], "" + !this.a(var14[1], var14[2], Boolean.parseBoolean(var14[3])));
            this.p(var14[1]);
         } else if (var1.startsWith("boolOpen:")) {
            String[] var15 = var1.split(":");
            boolean var3 = !this.a(var15[1], var15[2], Boolean.parseBoolean(var15[3]));
            this.b(var15[1], var15[2], "" + var3);
            this.p(var15[1]);
            if (var3) {
               this.expanded.add(var15[1] + "." + var15[2]);
            } else {
               this.expanded.remove(var15[1] + "." + var15[2]);
            }
         } else if (var1.startsWith("enum:")) {
            String[] var16 = var1.split(":");
            com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var26 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var16[1], var16[2]);
            if (var26 != null && !var26.values.isEmpty()) {
               int var4 = 0;
               int var5 = var26.values.size();
               String var6 = this.a(var16[1], var16[2], var26.value);

               for (int var7 = 0; var7 < var5; var7++) {
                  if (var26.values.get(var7)[0].equals(var6)) {
                     var4 = var7;
                  }
               }

               var4 = var16[3].equals("prev") ? (var4 + var5 - 1) % var5 : (var4 + 1) % var5;
               this.b(var16[1], var16[2], var26.values.get(var4)[0]);
               this.p(var16[1]);
            }
         } else if (var1.startsWith("button:")) {
            String[] var17 = var1.split(":");
            if (com.cheatbreaker.client.kazerui.gui.components.OptionTree.buttons != null) {
               com.cheatbreaker.client.kazerui.gui.components.OptionTree.buttons.accept(var17[1], var17[2]);
            }
         } else if (var1.startsWith("listenKey:")) {
            this.keyListening = var1.substring(10);
         } else if (var1.startsWith("editText:")) {
            String[] var18 = var1.split(":");
            this.textEditing = var18[1] + ":" + var18[2];
            this.textMax = Integer.parseInt(var18[3]);
            com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var27 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var18[1], var18[2]);
            this.input = this.a(var18[1], var18[2], var27 == null ? "" : var27.value);
         } else if (var1.startsWith("keybindPopup:")) {
            this.keybindPopup = var1.substring(13);
         } else if (var1.equals("keybindPopupDone")) {
            this.keybindPopup = "";
            this.keyListening = "";
         } else if (var1.startsWith("colorOpen:")) {
            String var19 = "color|" + var1.substring(10).replace(':', '.');
            if (!this.expanded.remove(var19)) {
               this.expanded.add(var19);
            }
         } else if (var1.startsWith("hex:")) {
            String[] var20 = var1.split(":");
            this.hexEditing = var20[1] + ":" + var20[2];
            this.hexAlpha = var20.length < 4 || var20[3].equals("a");
            com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var28 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var20[1], var20[2]);
            this.input = this.c(var20[1], var20[2], var28 == null ? "FFFFFFFF" : var28.value);
         } else if (var1.startsWith("swatch:")) {
            String[] var21 = var1.split(":");
            this.d(var21[1], var21[2], var21[3]);
            this.m(var21[3]);
            this.p(var21[1]);
         } else if (var1.startsWith("colorFav:")) {
            String[] var22 = var1.split(":");
            String var29 = this.c(var22[1], var22[2], var22[3]);
            List var33 = this.l("favoriteColors");
            if (!var33.remove(var29)) {
               if (var33.size() >= 12) {
                  var33.remove(0);
               }

               var33.add(var29);
            }

            this.store.b("favoriteColors", String.join("|", var33));
         } else if (var1.startsWith("chromaType:")) {
            String[] var23 = var1.split(":");
            this.b(var23[1], var23[2] + "ChromaType", this.a(var23[1], var23[2] + "ChromaType", "WAVE").equals("WAVE") ? "SHIFT" : "WAVE");
         } else if (var1.equals("searchOptions")) {
            this.store.b("searchOptions", "" + !Boolean.parseBoolean(this.store.a("searchOptions", "true")));
         } else if (var1.equals("rebind")) {
            this.captureKey = true;
            this.captureTarget = "menu";
         } else if (var1.equals("rebindFreelook")) {
            this.captureKey = true;
            this.captureTarget = "freelook";
         } else if (var1.equals("rebindZoom")) {
            this.captureKey = true;
            this.captureTarget = "zoom";
         } else if (var1.startsWith("resetBinding:")) {
            this.resetBindingRequested = var1.substring(13);
         } else if (var1.equals("resetModule")) {
            this.j(this.selected);
         } else if (var1.equals("resetPosition")) {
            ArrayList var24 = new ArrayList<>(Collections.singletonList(this.selected));
            com.cheatbreaker.client.kazerui.module.KazerModule var30 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.a(this.selected);
            if (var30 != null) {
               for (com.cheatbreaker.client.kazerui.module.KazerModule var37 : var30.i()) {
                  var24.add(var37.b());
               }
            }

            for (String var38 : (Iterable<String>) var24) {
               for (String var9 : new String[]{"anchor", "x", "y"}) {
                  this.store.a(this.a(var38, var9));
               }
            }
         }

         this.store.b();
      }
   }

   public void j(String var1) {
      for (com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 : com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(com.cheatbreaker.client.kazerui.gui.components.OptionTree.b(var1))) {
         this.store.a(this.a(var1, var3.key));

         for (String var7 : new String[]{"Chroma", "ChromaSpeed", "ChromaType", "Hue"}) {
            this.store.a(this.a(var1, var3.key + var7));
         }
      }

      this.revision++;
   }

   private void q(String var1) {
      if (!this.confirm.isEmpty()) {
         if (var1.equals("confirmYes")) {
            this.a(true);
         } else if (var1.equals("confirmNo")) {
            this.a(false);
         }
      } else if (this.iconMenu != null) {
         if (var1.startsWith("editorIcon:")) {
            this.editorIcon = var1.substring(11);
         }

         this.iconMenu = null;
      } else {
         this.editorFocus = "";
         if (var1.equals("editorName")) {
            this.editorFocus = "name";
         } else if (var1.equals("editorServer")) {
            this.editorFocus = "server";
         } else if (var1.equals("editorIconMenu")) {
            this.iconMenu = new float[]{this.clickX, this.clickY};
         } else if (var1.equals("editorSave")) {
            this.editorName = this.editorName.trim();
            if (this.editorName.isEmpty()) {
               this.editorNameError = true;
               return;
            }

            for (String var3 : this.profiles) {
               if (!var3.equals(this.editor) && this.a(var3).equalsIgnoreCase(this.editorName)) {
                  this.editorNameError = true;
                  return;
               }
            }

            if (this.creatingProfile) {
               String var6 = "profile." + this.a() + ".";
               String var7 = "profile." + this.editor + ".";

               for (String var5 : new ArrayList<>(this.store.a())) {
                  if (var5.startsWith(var6)) {
                     this.store.b(var7 + var5.substring(var6.length()), this.store.a(var5, ""));
                  }
               }

               this.profiles.add(this.editor);
               this.i();
               this.store.b("activeProfile", this.editor);
               this.profileOffset = Math.max(0, this.profiles.size() - 13);
            }

            this.store.b("profileDisplay." + this.editor, this.editorName);
            this.store.b("profileIcon." + this.editor, this.editorIcon);
            this.store.b("profileServer." + this.editor, this.editorServer);
            this.j();
         } else if (var1.equals("editorBack")) {
            if (this.editorName.equalsIgnoreCase(this.a(this.editor)) && this.editorIcon.equalsIgnoreCase(this.b(this.editor))) {
               this.j();
            } else {
               this.confirm = "backConfirm";
            }
         } else if (var1.equals("editorDelete") && !this.creatingProfile && this.e(this.editor)) {
            this.confirm = "deleteConfirm";
         } else if (var1.equals("editorReset") && !this.creatingProfile && this.inWorld) {
            this.confirm = "resetProfile";
         }
      }
   }

   public void k(String var1) {
      if (this.editorFocus.equals("name")) {
         this.editorName = var1.length() > 20 ? var1.substring(0, 20) : var1;
         this.editorNameError = false;
      } else if (this.editorFocus.equals("server")) {
         this.editorServer = var1.length() > 60 ? var1.substring(0, 60) : var1;
         this.editorServerError = false;
      }

      this.revision++;
   }

   public void g() {
      if (!this.confirm.isEmpty()) {
         this.a(false);
      } else if (this.iconMenu != null) {
         this.iconMenu = null;
      } else {
         this.q("editorBack");
      }

      this.revision++;
   }

   public static String b(String var0, String var1) {
      com.cheatbreaker.client.kazerui.module.modulerender.HudElement var2 = com.cheatbreaker.client.kazerui.module.ModuleRegistry.b(var0);
      if (var2 != null && var1.equals("anchor")) {
         return var2.anchor.id;
      } else if (var2 != null && var1.equals("x")) {
         return "" + var2.d();
      } else if (var2 != null && var1.equals("y")) {
         return "" + var2.e();
      } else if (var1.equals("anchor")) {
         return "";
      } else if (var1.equals("x")) {
         return "6";
      } else if (var1.equals("y")) {
         return "" + (6 + 14 * Math.max(0, com.cheatbreaker.client.kazerui.gui.components.ModuleCatalog.PORTED.indexOf(var0)));
      } else {
         com.cheatbreaker.client.kazerui.gui.components.OptionTree.a var3 = com.cheatbreaker.client.kazerui.gui.components.OptionTree.a(var0, var1);
         return var3 == null ? "" : var3.value;
      }
   }

   public String c(String var1, String var2, String var3) {
      String var4 = this.a(var1, var2, var3).toUpperCase(Locale.ROOT);
      return var4.matches("[0-9A-F]{8}") ? var4 : var3.toUpperCase(Locale.ROOT);
   }

   public void d(String var1, String var2, String var3) {
      this.b(var1, var2, var3.toUpperCase(Locale.ROOT));
   }

   public float a(String var1, String var2, int var3) {
      float[] var4 = Color.RGBtoHSB(var3 >> 16 & 0xFF, var3 >> 8 & 0xFF, var3 & 0xFF, null);
      return var4[1] > 0.0F && var4[2] > 0.0F ? var4[0] : this.a(var1, var2 + "Hue", var4[0]);
   }

   public void a(String var1, String var2, float var3, float var4, float var5, int var6) {
      int var7 = Color.HSBtoRGB(var3, var4, var5) & 16777215;
      this.b(var1, var2 + "Hue", "" + var3);
      this.d(var1, var2, String.format(Locale.ROOT, "%08X", var6 << 24 | var7));
   }

   public List<String> l(String var1) {
      ArrayList var2 = new ArrayList();

      for (String var6 : this.store.a(var1, "").split("\\|")) {
         if (var6.matches("[0-9A-F]{8}")) {
            var2.add(var6);
         }
      }

      return var2;
   }

   public void m(String var1) {
      List var2 = this.l("recentColors");
      if (var2.isEmpty() || !((String)var2.get(var2.size() - 1)).equals(var1)) {
         if (var2.size() >= 6) {
            var2.remove(0);
         }

         var2.add(var1);
         this.store.b("recentColors", String.join("|", var2));
      }
   }

   public void h() {
      this.search = "";
      this.scroller.f();
      this.searchFocused = false;
      this.searchSelected = false;
   }

   public void n(String var1) {
      this.search = var1;
      this.scroller.f();
      this.revision++;
   }
}
