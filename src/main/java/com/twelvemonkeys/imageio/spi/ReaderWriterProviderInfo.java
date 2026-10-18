package com.twelvemonkeys.imageio.spi;

import com.twelvemonkeys.lang.Validate;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public abstract class ReaderWriterProviderInfo extends ProviderInfo {
   private final String[] formatNames;
   private final String[] suffixes;
   private final String[] mimeTypes;
   private final String readerClassName;
   private final String[] readerSpiClassNames;
   private final Class<?>[] inputTypes = new Class[]{ImageInputStream.class};
   private final String writerClassName;
   private final String[] writerSpiClassNames;
   private final Class<?>[] outputTypes = new Class[]{ImageOutputStream.class};
   private final boolean supportsStandardStreamMetadata;
   private final String nativeStreamMetadataFormatName;
   private final String nativeStreamMetadataFormatClassName;
   private final String[] extraStreamMetadataFormatNames;
   private final String[] extraStreamMetadataFormatClassNames;
   private final boolean supportsStandardImageMetadata;
   private final String nativeImageMetadataFormatName;
   private final String nativeImageMetadataFormatClassName;
   private final String[] extraImageMetadataFormatNames;
   private final String[] extraImageMetadataFormatClassNames;

   protected ReaderWriterProviderInfo(
      Class<? extends ReaderWriterProviderInfo> var1,
      String[] var2,
      String[] var3,
      String[] var4,
      String var5,
      String[] var6,
      String var7,
      String[] var8,
      boolean var9,
      String var10,
      String var11,
      String[] var12,
      String[] var13,
      boolean var14,
      String var15,
      String var16,
      String[] var17,
      String[] var18
   ) {
      super(Validate.notNull(var1).getPackage());
      this.formatNames = var2;
      this.suffixes = var3;
      this.mimeTypes = var4;
      this.readerClassName = var5;
      this.readerSpiClassNames = var6;
      this.writerClassName = var7;
      this.writerSpiClassNames = var8;
      this.supportsStandardStreamMetadata = var9;
      this.nativeStreamMetadataFormatName = var10;
      this.nativeStreamMetadataFormatClassName = var11;
      this.extraStreamMetadataFormatNames = var12;
      this.extraStreamMetadataFormatClassNames = var13;
      this.supportsStandardImageMetadata = var14;
      this.nativeImageMetadataFormatName = var15;
      this.nativeImageMetadataFormatClassName = var16;
      this.extraImageMetadataFormatNames = var17;
      this.extraImageMetadataFormatClassNames = var18;
   }

   public String[] formatNames() {
      return this.formatNames;
   }

   public String[] suffixes() {
      return this.suffixes;
   }

   public String[] mimeTypes() {
      return this.mimeTypes;
   }

   public String readerClassName() {
      return this.readerClassName;
   }

   public String[] readerSpiClassNames() {
      return this.readerSpiClassNames;
   }

   public Class[] inputTypes() {
      return this.inputTypes;
   }

   public String writerClassName() {
      return this.writerClassName;
   }

   public String[] writerSpiClassNames() {
      return this.writerSpiClassNames;
   }

   public Class[] outputTypes() {
      return this.outputTypes;
   }

   public boolean supportsStandardStreamMetadataFormat() {
      return this.supportsStandardStreamMetadata;
   }

   public String nativeStreamMetadataFormatName() {
      return this.nativeStreamMetadataFormatName;
   }

   public String nativeStreamMetadataFormatClassName() {
      return this.nativeStreamMetadataFormatClassName;
   }

   public String[] extraStreamMetadataFormatNames() {
      return this.extraStreamMetadataFormatNames;
   }

   public String[] extraStreamMetadataFormatClassNames() {
      return this.extraStreamMetadataFormatClassNames;
   }

   public boolean supportsStandardImageMetadataFormat() {
      return this.supportsStandardImageMetadata;
   }

   public String nativeImageMetadataFormatName() {
      return this.nativeImageMetadataFormatName;
   }

   public String nativeImageMetadataFormatClassName() {
      return this.nativeImageMetadataFormatClassName;
   }

   public String[] extraImageMetadataFormatNames() {
      return this.extraImageMetadataFormatNames;
   }

   public String[] extraImageMetadataFormatClassNames() {
      return this.extraImageMetadataFormatClassNames;
   }
}
