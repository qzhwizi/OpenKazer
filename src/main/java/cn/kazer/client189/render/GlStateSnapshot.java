package cn.kazer.client189.render;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.GlStateManager;

final class GlStateSnapshot {
   private static final cn.kazer.client189.render.GlStateSnapshot.Binding[] BINDINGS = c();
   private final int[] values = new int[BINDINGS.length];

   void a() {
      this.a(false);
   }

   void b() {
      this.a(true);
   }

   private void a(boolean var1) {
      try {
         for (int var2 = 0; var2 < BINDINGS.length; var2++) {
            cn.kazer.client189.render.GlStateSnapshot.Binding var3 = BINDINGS[var2];
            Class var4 = var3.field.getType();
            if (var4 == boolean.class) {
               if (var1) {
                  var3.field.setBoolean(var3.owner, this.values[var2] != 0);
               } else {
                  this.values[var2] = var3.field.getBoolean(var3.owner) ? 1 : 0;
               }
            } else if (var4 == int.class) {
               if (var1) {
                  var3.field.setInt(var3.owner, this.values[var2]);
               } else {
                  this.values[var2] = var3.field.getInt(var3.owner);
               }
            } else if (var4 == float.class) {
               if (var1) {
                  var3.field.setFloat(var3.owner, Float.intBitsToFloat(this.values[var2]));
               } else {
                  this.values[var2] = Float.floatToRawIntBits(var3.field.getFloat(var3.owner));
               }
            }
         }
      } catch (IllegalAccessException var5) {
         throw new IllegalStateException("Cannot synchronize GL state cache", var5);
      }
   }

   private static cn.kazer.client189.render.GlStateSnapshot.Binding[] c() {
      ArrayList var0 = new ArrayList();
      Set var1 = Collections.newSetFromMap(new IdentityHashMap());

      try {
         a(null, GlStateManager.class, var0, var1);
      } catch (IllegalAccessException var3) {
         throw new ExceptionInInitializerError(var3);
      }

      return (cn.kazer.client189.render.GlStateSnapshot.Binding[]) var0.toArray(new cn.kazer.client189.render.GlStateSnapshot.Binding[var0.size()]);
   }

   private static void a(Object var0, Class<?> var1, List<cn.kazer.client189.render.GlStateSnapshot.Binding> var2, Set<Object> var3) throws IllegalAccessException {
      if (var0 == null || var3.add(var0)) {
         for (Field var7 : var1.getDeclaredFields()) {
            if (!var7.isSynthetic() && var0 == null == Modifier.isStatic(var7.getModifiers())) {
               Class var8 = var7.getType();
               var7.setAccessible(true);
               if ((var8 == boolean.class || var8 == int.class || var8 == float.class) && !Modifier.isFinal(var7.getModifiers())) {
                  var2.add(new cn.kazer.client189.render.GlStateSnapshot.Binding(var7, var0));
               } else if (!var8.isPrimitive()) {
                  Object var9 = var7.get(var0);
                  if (var9 != null) {
                     if (var8.isArray() && !var8.getComponentType().isPrimitive()) {
                        for (Object var13 : (Object[])var9) {
                           if (var13 != null && var13.getClass().getName().startsWith(GlStateManager.class.getName() + "$")) {
                              a(var13, var13.getClass(), var2, var3);
                           }
                        }
                     } else if (var9.getClass().getName().startsWith(GlStateManager.class.getName() + "$")) {
                        a(var9, var9.getClass(), var2, var3);
                     }
                  }
               }
            }
         }
      }
   }

   private static final class Binding {
      final Field field;
      final Object owner;

      Binding(Field var1, Object var2) {
         this.field = var1;
         this.owner = var2;
      }
   }
}
