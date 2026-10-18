package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

final class ChatFilterPaths {
   static File a(File var0, String var1) {
      String var2 = Base64.getUrlEncoder().withoutPadding().encodeToString(var1.getBytes(StandardCharsets.UTF_8));
      return new File(new File(new File(var0, "chat-filters"), var2), "profanity_filter.txt");
   }

   private ChatFilterPaths() {
   }
}
