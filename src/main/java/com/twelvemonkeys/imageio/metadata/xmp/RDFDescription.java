package com.twelvemonkeys.imageio.metadata.xmp;

import com.twelvemonkeys.imageio.metadata.AbstractDirectory;
import com.twelvemonkeys.imageio.metadata.Entry;
import java.util.Collection;

final class RDFDescription extends AbstractDirectory {
   private final String namespace;

   public RDFDescription(Collection<? extends Entry> var1) {
      this(null, var1);
   }

   public RDFDescription(String var1, Collection<? extends Entry> var2) {
      super(var2);
      this.namespace = var1;
   }

   @Override
   public String toString() {
      return this.namespace != null
         ? super.toString()
            .replaceAll(
               "^RDFDescription\\[", String.format("%s[%s|%s, ", this.getClass().getSimpleName(), XMP.DEFAULT_NS_MAPPING.get(this.namespace), this.namespace)
            )
         : super.toString();
   }
}
