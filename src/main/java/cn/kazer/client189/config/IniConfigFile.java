package cn.kazer.client189.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IniConfigFile {
   private final List<String> lines;

   private IniConfigFile(List<String> var1) {
      this.lines = new ArrayList<>(var1);
   }

   public static cn.kazer.client189.config.IniConfigFile a(Path var0) throws IOException {
      return new cn.kazer.client189.config.IniConfigFile(Files.exists(var0) ? Files.readAllLines(var0, Charset.defaultCharset()) : Collections.emptyList());
   }

   public static cn.kazer.client189.config.IniConfigFile a(List<String> var0) {
      return new cn.kazer.client189.config.IniConfigFile(var0);
   }

   public String a(String var1, String var2) {
      String var3 = "";

      for (String var5 : this.lines) {
         String var6 = b(var5);
         if (var6 != null) {
            var3 = var6;
         } else if (var3.equalsIgnoreCase(a(var1)) && !var5.startsWith("#")) {
            int var7 = var5.indexOf(61);
            if (var7 > 0 && var5.substring(0, var7).equalsIgnoreCase(var2)) {
               return var5.substring(var7 + 1);
            }
         }
      }

      return null;
   }

   public void a(String var1, String var2, String var3) {
      if (var2 != null
         && !var2.isEmpty()
         && var2.indexOf(61) < 0
         && var2.indexOf(10) < 0
         && var2.indexOf(13) < 0
         && var3 != null
         && var3.indexOf(10) < 0
         && var3.indexOf(13) < 0) {
         String var4 = a(var1);
         String var5 = "";
         int var6 = this.lines.size();
         boolean var7 = var4.isEmpty();

         for (int var8 = 0; var8 < this.lines.size(); var8++) {
            String var9 = this.lines.get(var8);
            String var10 = b(var9);
            if (var10 != null) {
               if (var5.equalsIgnoreCase(var4)) {
                  var6 = var8;
               }

               var5 = var10;
               if (var10.equalsIgnoreCase(var4)) {
                  var7 = true;
               }
            } else if (var5.equalsIgnoreCase(var4) && !var9.startsWith("#")) {
               int var11 = var9.indexOf(61);
               if (var11 > 0 && var9.substring(0, var11).equalsIgnoreCase(var2)) {
                  this.lines.set(var8, var2 + "=" + var3);
                  return;
               }
            }
         }

         if (!var7) {
            if (!this.lines.isEmpty() && !this.lines.get(this.lines.size() - 1).isEmpty()) {
               this.lines.add("");
            }

            this.lines.add("[" + var4 + "]");
            this.lines.add(var2 + "=" + var3);
         } else {
            this.lines.add(var6, var2 + "=" + var3);
         }
      } else {
         throw new IllegalArgumentException("Invalid config entry");
      }
   }

   public List<String> a() {
      return Collections.unmodifiableList(this.lines);
   }

   public void b(Path var1) throws IOException {
      Files.createDirectories(var1.toAbsolutePath().getParent());
      Path var2 = Files.createTempFile(var1.toAbsolutePath().getParent(), "kazer-config-", ".tmp");

      try {
         Files.write(var2, this.lines, Charset.defaultCharset());

         try {
            Files.move(var2, var1, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
         } catch (AtomicMoveNotSupportedException var7) {
            Files.move(var2, var1, StandardCopyOption.REPLACE_EXISTING);
         }
      } finally {
         Files.deleteIfExists(var2);
      }
   }

   private static String a(String var0) {
      return var0 == null ? "" : var0;
   }

   private static String b(String var0) {
      String var1 = var0.trim();
      return var1.length() >= 2 && var1.startsWith("[") && var1.endsWith("]") ? var1.substring(1, var1.length() - 1) : null;
   }
}
