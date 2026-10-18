package com.twelvemonkeys.imageio.metadata.jpeg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

public final class JPEGSegment implements Serializable {
   final int marker;
   final byte[] data;
   private final int length;
   private transient String id;

   JPEGSegment(int var1, byte[] var2, int var3) {
      this.marker = var1;
      this.data = var2;
      this.length = var3;
   }

   public int segmentLength() {
      return this.length;
   }

   public InputStream segmentData() {
      return this.data != null ? new ByteArrayInputStream(this.data) : null;
   }

   public int marker() {
      return this.marker;
   }

   public String identifier() {
      if (this.id == null && isAppSegmentMarker(this.marker)) {
         this.id = JPEGSegmentUtil.asNullTerminatedAsciiString(this.data, 0);
      }

      return this.id;
   }

   static boolean isAppSegmentMarker(int var0) {
      return var0 >= 65504 && var0 <= 65519;
   }

   public InputStream data() {
      return this.data != null ? new ByteArrayInputStream(this.data, this.offset(), this.length()) : null;
   }

   public int length() {
      return this.data != null ? this.data.length - this.offset() : 0;
   }

   int offset() {
      String var1 = this.identifier();
      return var1 == null ? 0 : var1.length() + 1;
   }

   @Override
   public String toString() {
      String var1 = this.identifier();
      return var1 != null
         ? String.format("JPEGSegment[%04x/%s size: %d]", this.marker, var1, this.segmentLength())
         : String.format("JPEGSegment[%04x size: %d]", this.marker, this.segmentLength());
   }

   @Override
   public int hashCode() {
      String var1 = this.identifier();
      return this.marker() << 16 | (var1 != null ? var1.hashCode() : 0) & 65535;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof JPEGSegment && ((JPEGSegment)var1).marker == this.marker && Arrays.equals(((JPEGSegment)var1).data, this.data);
   }
}
