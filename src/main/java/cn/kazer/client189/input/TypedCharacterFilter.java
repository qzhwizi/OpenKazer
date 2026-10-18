package cn.kazer.client189.input;

public final class TypedCharacterFilter {
   private TypedCharacterFilter() {
   }

   public static boolean a(boolean var0, int var1, char var2) {
      return var0 || var1 == 0 && var2 != 0 && !Character.isISOControl(var2) && !Character.isSurrogate(var2) && Character.isDefined(var2);
   }
}
