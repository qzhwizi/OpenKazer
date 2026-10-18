package com.cheatbreaker.client.kazerui.module.modules.mechanic;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class ChatClipboard {
   private static final ThreadPoolExecutor WORKER = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), var0 -> {
      Thread var1 = new Thread(var0, "Kazer chat clipboard");
      var1.setDaemon(true);
      return var1;
   });

   static Future<Boolean> a(String var0) {
      return a(() -> {
         try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(var0), null);
            return true;
         } catch (RuntimeException var2) {
            return false;
         }
      });
   }

   static Future<Boolean> a(Callable<Boolean> var0) {
      try {
         return WORKER.submit(var0);
      } catch (RejectedExecutionException var2) {
         return CompletableFuture.completedFuture(false);
      }
   }

   private ChatClipboard() {
   }
}
