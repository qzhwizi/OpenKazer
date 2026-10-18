package com.twelvemonkeys.net;

import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.SystemUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public final class MIMEUtil {
   private static Map<String, List<String>> sExtToMIME = new HashMap<>();
   private static Map<String, List<String>> sUnmodifiableExtToMIME = Collections.unmodifiableMap(sExtToMIME);
   private static Map<String, List<String>> sMIMEToExt = new HashMap<>();
   private static Map<String, List<String>> sUnmodifiableMIMEToExt = Collections.unmodifiableMap(sMIMEToExt);

   private MIMEUtil() {
   }

   public static String getMIMEType(String var0) {
      List var1 = sExtToMIME.get(StringUtil.toLowerCase(var0));
      return var1 != null && !var1.isEmpty() ? (String)var1.get(0) : null;
   }

   public static List<String> getMIMETypes(String var0) {
      List var1 = sExtToMIME.get(StringUtil.toLowerCase(var0));
      return maskNull(var1);
   }

   public static Map<String, List<String>> getMIMETypeMappings() {
      return sUnmodifiableExtToMIME;
   }

   public static String getExtension(String var0) {
      String var1 = bareMIME(StringUtil.toLowerCase(var0));
      List var2 = sMIMEToExt.get(var1);
      return var2 != null && !var2.isEmpty() ? (String)var2.get(0) : null;
   }

   public static List<String> getExtensions(String var0) {
      String var1 = bareMIME(StringUtil.toLowerCase(var0));
      if (var1.endsWith("/*")) {
         return getExtensionForWildcard(var1);
      } else {
         List var2 = sMIMEToExt.get(var1);
         return maskNull(var2);
      }
   }

   private static List<String> getExtensionForWildcard(String var0) {
      String var1 = var0.substring(0, var0.length() - 1);
      LinkedHashSet var2 = new LinkedHashSet();

      for (Entry var4 : sMIMEToExt.entrySet()) {
         if ("*/".equals(var1) || ((String)var4.getKey()).startsWith(var1)) {
            var2.addAll((Collection)var4.getValue());
         }
      }

      return Collections.unmodifiableList(new ArrayList<>(var2));
   }

   public static Map<String, List<String>> getExtensionMappings() {
      return sUnmodifiableMIMEToExt;
   }

   static boolean includes(String var0, String var1) {
      String var2 = bareMIME(var1);
      return var2.equals(var0) || "*/*".equals(var0) || var0.endsWith("/*") && var0.startsWith(var2.substring(0, var2.indexOf(47)));
   }

   public static String bareMIME(String var0) {
      int var1;
      return var0 != null && (var1 = var0.indexOf(59)) >= 0 ? var0.substring(0, var1) : var0;
   }

   private static List<String> maskNull(List<String> var0) {
      return var0 == null ? Collections.emptyList() : var0;
   }

   public static void main(String[] var0) {
      if (var0.length > 1) {
         String var1 = var0[0];
         String var2 = var0[1];
         boolean var3 = includes(var2, var1);
         System.out.println("Mime type family " + var2 + (var3 ? " includes " : " does not include ") + "type " + var1);
      }

      if (var0.length > 0) {
         String var9 = var0[0];
         if (var9.indexOf(47) >= 0) {
            String var11 = getExtension(var9);
            System.out.println("Default extension for MIME type '" + var9 + "' is " + (var11 != null ? ": '" + var11 + "'" : "unknown") + ".");
            System.out.println("All possible: " + getExtensions(var9));
         } else {
            String var12 = getMIMEType(var9);
            System.out.println("Default MIME type for extension '" + var9 + "' is " + (var12 != null ? ": '" + var12 + "'" : "unknown") + ".");
            System.out.println("All possible: " + getMIMETypes(var9));
         }
      } else {
         Set var7 = sMIMEToExt.keySet();
         String[] var10 = new String[var7.size()];
         int var13 = 0;

         for (String var5 : (Iterable<String>) var7) {
            var10[var13] = var5;
            var13++;
         }

         Arrays.sort((Object[])var10);
         System.out.println("Known MIME types (" + var10.length + "):");

         for (int var15 = 0; var15 < var10.length; var15++) {
            String var17 = var10[var15];
            if (var15 != 0) {
               System.out.print(", ");
            }

            System.out.print(var17);
         }

         System.out.println("\n");
         var7 = sExtToMIME.keySet();
         String[] var16 = new String[var7.size()];
         var13 = 0;

         for (String var6 : (Iterable<String>) var7) {
            var16[var13] = var6;
            var13++;
         }

         Arrays.sort((Object[])var16);
         System.out.println("Known file types (" + var16.length + "):");

         for (int var19 = 0; var19 < var16.length; var19++) {
            String var20 = var16[var19];
            if (var19 != 0) {
               System.out.print(", ");
            }

            System.out.print(var20);
         }

         System.out.println();
      }
   }

   static {
      try {
         Properties var0 = SystemUtil.loadProperties(MIMEUtil.class);

         for (Entry var2 : var0.entrySet()) {
            String var3 = StringUtil.toLowerCase((String)var2.getKey());
            List var4 = Collections.unmodifiableList(Arrays.asList(StringUtil.toStringArray(var3, ";, ")));
            String var5 = StringUtil.toLowerCase((String)var2.getValue());
            List var6 = Collections.unmodifiableList(Arrays.asList(StringUtil.toStringArray(var5, ";, ")));

            for (String var8 : (Iterable<String>) var4) {
               sExtToMIME.put(var8, var6);
            }

            for (String var11 : (Iterable<String>) var6) {
               sMIMEToExt.put(var11, var4);
            }
         }
      } catch (IOException var9) {
         System.err.println("Could not read properties for MIMEUtil: " + var9.getMessage());
         var9.printStackTrace();
      }
   }
}
