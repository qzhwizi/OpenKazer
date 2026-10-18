package com.twelvemonkeys.imageio;

import java.util.Arrays;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import org.w3c.dom.Node;

public abstract class AbstractMetadata extends IIOMetadata implements Cloneable {
   protected AbstractMetadata(boolean var1, String var2, String var3, String[] var4, String[] var5) {
      super(var1, var2, var3, var4, var5);
   }

   protected AbstractMetadata() {
      super(true, null, null, null, null);
   }

   @Override
   public boolean isReadOnly() {
      return true;
   }

   @Override
   public Node getAsTree(String var1) {
      this.validateFormatName(var1);
      if (var1.equals(this.nativeMetadataFormatName)) {
         return this.getNativeTree();
      } else {
         return var1.equals("javax_imageio_1.0") ? this.getStandardTree() : null;
      }
   }

   protected Node getNativeTree() {
      throw new UnsupportedOperationException("getNativeTree");
   }

   @Override
   public void mergeTree(String var1, Node var2) throws IIOInvalidTreeException {
      this.assertMutable();
      this.validateFormatName(var1);
      if (!var2.getNodeName().equals(var1)) {
         throw new IIOInvalidTreeException("Root must be " + var1, var2);
      }
   }

   @Override
   public void reset() {
      this.assertMutable();
   }

   protected final void assertMutable() {
      if (this.isReadOnly()) {
         throw new IllegalStateException("Metadata is read-only");
      }
   }

   protected final void validateFormatName(String var1) {
      String[] var2 = this.getMetadataFormatNames();
      if (var2 != null) {
         for (String var6 : var2) {
            if (var6.equals(var1)) {
               return;
            }
         }
      }

      throw new IllegalArgumentException(String.format("Unsupported format name: \"%s\". Expected one of %s", var1, Arrays.toString((Object[])var2)));
   }

   protected static String toListString(short[] var0) {
      String var1 = Arrays.toString(var0);
      return var1.substring(1, var1.length() - 1);
   }
}
