package com.cheatbreaker.client.module.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ActionFeedbackState {
   public static final long NOTIFICATION_DURATION_MILLIS = 1500L;
   public static final long NOTIFICATION_FADE_DURATION_MILLIS = 250L;
   private static final int NOTIFICATION_HEIGHT = 20;
   private static final int NOTIFICATION_GAP = 2;
   private static final int MAX_NOTIFICATIONS = 3;

   private final List<com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification> notifications = new ArrayList<>();
   private boolean visibilityNotificationsEnabled = true;
   private boolean copyNotificationsEnabled = true;
   private long copyGeneration;

   public boolean a(boolean var1, long var2) {
      this.d(var2);
      if (this.visibilityNotificationsEnabled) {
         this.a(var1 ? "Chat hidden." : "Chat shown.", com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind.INFO, com.cheatbreaker.client.module.modules.ActionFeedbackState.Category.VISIBILITY, var2);
      }

      return true;
   }

   public boolean a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Result var1, long var2) {
      return this.a(var1, this.copyGeneration, var2);
   }

   public long a() {
      return this.copyGeneration;
   }

   public boolean a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Result var1, long var2, long var4) {
      this.d(var4);
      if (var2 != this.copyGeneration) {
         return false;
      } else if (var1 == com.cheatbreaker.client.module.modules.ActionFeedbackState.Result.NO_MESSAGE) {
         return false;
      } else {
         boolean var6 = var1 == com.cheatbreaker.client.module.modules.ActionFeedbackState.Result.SUCCESS;
         if (this.copyNotificationsEnabled) {
            this.a(var6 ? "Chat copied." : "Failed to copy chat message.", var6 ? com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind.INFO : com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind.ERROR, com.cheatbreaker.client.module.modules.ActionFeedbackState.Category.COPY, var4);
         }

         return var6;
      }
   }

   public List<com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification> a(long var1) {
      this.d(var1);
      return Collections.unmodifiableList(new ArrayList<>(this.notifications));
   }

   public boolean b() {
      return this.visibilityNotificationsEnabled;
   }

   public void b(boolean var1, long var2) {
      this.d(var2);
      this.visibilityNotificationsEnabled = var1;
      if (!var1) {
         this.a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Category.VISIBILITY);
      }
   }

   public boolean c() {
      return this.copyNotificationsEnabled;
   }

   public void c(boolean var1, long var2) {
      this.d(var2);
      this.copyNotificationsEnabled = var1;
      if (!var1) {
         this.a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Category.COPY);
      }
   }

   public int b(long var1) {
      this.d(var1);
      return this.notifications.size() * 22;
   }

   public void c(long var1) {
      ++this.copyGeneration;
      this.d(var1);
      this.notifications.clear();
   }

   private void d(long var1) {
      this.notifications.removeIf(var3 -> com.cheatbreaker.client.module.modules.ActionFeedbackState.a(var1, var3));
   }

   private void a(String var1, com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind var2, com.cheatbreaker.client.module.modules.ActionFeedbackState.Category var3, long var4) {
      this.notifications.add(0, new com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification(var1, var2, var3, var4));
      if (this.notifications.size() > 3) {
         this.notifications.remove(this.notifications.size() - 1);
      }
   }

   private void a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Category var1) {
      this.notifications.removeIf(var2 -> com.cheatbreaker.client.module.modules.ActionFeedbackState.a(var1, var2));
   }

   private static boolean a(com.cheatbreaker.client.module.modules.ActionFeedbackState.Category var0, com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification var1) {
      return var1.c() == var0;
   }

   private static boolean a(long var0, com.cheatbreaker.client.module.modules.ActionFeedbackState.Notification var2) {
      return var0 - var2.d() >= 1500L;
   }

   public static final class Notification {
      private final String message;
      private final com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind type;
      private final com.cheatbreaker.client.module.modules.ActionFeedbackState.Category category;
      private final long createdAt;

      Notification(String var1, com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind var2, com.cheatbreaker.client.module.modules.ActionFeedbackState.Category var3, long var4) {
         this.message = var1;
         this.type = var2;
         this.category = var3;
         this.createdAt = var4;
      }

      public String a() {
         return this.message;
      }

      public com.cheatbreaker.client.module.modules.ActionFeedbackState.Kind b() {
         return this.type;
      }

      public com.cheatbreaker.client.module.modules.ActionFeedbackState.Category c() {
         return this.category;
      }

      public long d() {
         return this.createdAt;
      }

      public float a(long var1) {
         long var3 = Math.max(0L, var1 - this.createdAt);
         long var5 = 1250L;
         if (var3 <= var5) {
            return 1.0F;
         } else {
            return var3 >= 1500L ? 0.0F : 1.0F - (float)(var3 - var5) / 250.0F;
         }
      }
   }

   public static enum Result {
      SUCCESS,
      FAILURE,
      NO_MESSAGE;
   }

   public static enum Category {
      VISIBILITY,
      COPY;
   }

   public static enum Kind {
      INFO,
      ERROR;
   }
}
