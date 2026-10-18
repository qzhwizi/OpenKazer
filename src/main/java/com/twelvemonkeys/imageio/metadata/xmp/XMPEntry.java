package com.twelvemonkeys.imageio.metadata.xmp;

import com.twelvemonkeys.imageio.metadata.AbstractEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class XMPEntry extends AbstractEntry {
   private final String fieldName;

   public XMPEntry(String var1, Object var2) {
      this(var1, null, var2);
   }

   public XMPEntry(String var1, String var2, Object var3) {
      super(var1, var3);
      this.fieldName = var2;
   }

   @Override
   protected String getNativeIdentifier() {
      String var1 = (String)this.getIdentifier();
      String var2 = this.fieldName != null && var1.endsWith(this.fieldName)
         ? XMP.DEFAULT_NS_MAPPING.get(var1.substring(0, var1.length() - this.fieldName.length()))
         : null;
      return var2 != null ? var2 + ":" + this.fieldName : var1;
   }

   @Override
   public String getFieldName() {
      return this.fieldName != null ? this.fieldName : XMP.DEFAULT_NS_MAPPING.get(this.getIdentifier());
   }

   @Override
   public String getTypeName() {
      Object var1 = this.getValue();
      if (var1 instanceof List) {
         return "List";
      } else if (var1 instanceof Set) {
         return "Set";
      } else {
         return var1 instanceof Map ? "Map" : super.getTypeName();
      }
   }

   @Override
   public String toString() {
      String var1 = this.getTypeName();
      String var2 = var1 != null ? " (" + var1 + ")" : "";
      return String.format("%s: %s%s", this.getNativeIdentifier(), this.getValueAsString(), var2);
   }
}
