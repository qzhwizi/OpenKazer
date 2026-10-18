package com.cheatbreaker.client.kazerui.notifications;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public final class UiSettings {
   private final File file;
   private final Properties values = new Properties();
   private boolean changed;

   public UiSettings(File var1) {
      this.file = var1;
   }

   public void a() {
      if (this.file.isFile()) {
         try (InputStreamReader var1 = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
            this.values.load(var1);
         } catch (IOException var14) {
            throw new IllegalStateException("Cannot read Kazer UI settings", var14);
         }
      }
   }

   public boolean a(String var1, String var2) {
      return this.values.containsKey(var1 + "." + var2);
   }

   public boolean a(String var1) {
      for (String var3 : this.values.stringPropertyNames()) {
         if (var3.startsWith(var1 + ".")) {
            return true;
         }
      }

      return false;
   }

   public com.cheatbreaker.client.kazerui.notifications.UiSettings.aInner b(String var1) {
      return new com.cheatbreaker.client.kazerui.notifications.UiSettings.aInner(var1);
   }

   public com.cheatbreaker.client.kazerui.notifications.UiSettings.b a(String var1, String var2, String var3) {
      String var4 = var1 + "." + var2;
      if (!this.values.containsKey(var4)) {
         this.values.setProperty(var4, var3);
         this.changed = true;
      }

      return new com.cheatbreaker.client.kazerui.notifications.UiSettings.b(var4);
   }

   public boolean a(String var1, String var2, boolean var3, String var4) {
      return Boolean.parseBoolean(this.a(var2, var1, Boolean.toString(var3)).a());
   }

   public boolean b() {
      return this.changed;
   }

   public void c() {
      if (this.changed) {
         Path var1 = this.file.toPath().toAbsolutePath();
         Path var2 = null;

         try {
            Files.createDirectories(var1.getParent());
            var2 = Files.createTempFile(var1.getParent(), "kazer-ui-", ".tmp");

            try (OutputStreamWriter var3 = new OutputStreamWriter(Files.newOutputStream(var2), StandardCharsets.UTF_8)) {
               this.values.store(var3, "Kazer UI schema 1 - shared by Minecraft 1.7.10 and 1.8.9");
            }

            try {
               Files.move(var2, var1, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException var29) {
               Files.move(var2, var1, StandardCopyOption.REPLACE_EXISTING);
            }

            this.changed = false;
         } catch (IOException var32) {
            throw new IllegalStateException("Cannot save Kazer UI settings", var32);
         } finally {
            if (var2 != null) {
               try {
                  Files.deleteIfExists(var2);
               } catch (IOException var27) {
               }
            }
         }
      }
   }

   public final class aInner {
      private final String prefix;

      private aInner(String var2) {
         this.prefix = var2 + ".";
      }

      public com.cheatbreaker.client.kazerui.notifications.UiSettings.b a(String var1) {
         return UiSettings.this.new b(this.prefix + var1);
      }

      public Set<String> a() {
         LinkedHashSet var1 = new LinkedHashSet();

         for (String var3 : UiSettings.this.values.stringPropertyNames()) {
            if (var3.startsWith(this.prefix)) {
               var1.add(var3.substring(this.prefix.length()));
            }
         }

         return var1;
      }

      public void b(String var1) {
         if (UiSettings.this.values.remove(this.prefix + var1) != null) {
            UiSettings.this.changed = true;
         }
      }
   }

   public final class b {
      private final String key;

      private b(String var2) {
         this.key = var2;
      }

      public String a() {
         return UiSettings.this.values.getProperty(this.key);
      }

      public void a(String var1) {
         if (!Objects.equals(UiSettings.this.values.getProperty(this.key), var1)) {
            UiSettings.this.values.setProperty(this.key, var1);
            UiSettings.this.changed = true;
         }
      }
   }
}
