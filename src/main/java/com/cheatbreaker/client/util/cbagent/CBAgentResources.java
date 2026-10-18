package com.cheatbreaker.client.util.cbagent;

public class CBAgentResources {
   @com.cheatbreaker.client.util.cbagent.NativeResourceLoad
   public static native byte[] getBytesNative(String var0);

   @com.cheatbreaker.client.util.cbagent.NativeResourceCheck
   public static native boolean existsBytesNative(String var0);

   public static boolean a(String var0) {
      boolean var1 = false;

      try {
         var1 = existsBytesNative(var0);
      } catch (UnsatisfiedLinkError var3) {
      }

      return var1;
   }
}
