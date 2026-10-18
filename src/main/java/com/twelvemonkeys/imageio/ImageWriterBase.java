package com.twelvemonkeys.imageio;

import com.twelvemonkeys.imageio.util.IIOUtil;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

public abstract class ImageWriterBase extends ImageWriter {
   protected ImageOutputStream imageOutput;

   protected ImageWriterBase(ImageWriterSpi var1) {
      super(var1);
   }

   public String getFormatName() throws IOException {
      return this.getOriginatingProvider().getFormatNames()[0];
   }

   @Override
   public void setOutput(Object var1) {
      this.resetMembers();
      super.setOutput(var1);
      if (var1 instanceof ImageOutputStream) {
         this.imageOutput = (ImageOutputStream)var1;
      } else {
         this.imageOutput = null;
      }
   }

   protected void assertOutput() {
      if (this.getOutput() == null) {
         throw new IllegalStateException("getOutput() == null");
      }
   }

   @Override
   public void dispose() {
      this.resetMembers();
      super.dispose();
   }

   @Override
   public void reset() {
      this.resetMembers();
      super.reset();
   }

   protected void resetMembers() {
   }

   @Override
   public IIOMetadata getDefaultStreamMetadata(ImageWriteParam var1) {
      return null;
   }

   @Override
   public IIOMetadata convertStreamMetadata(IIOMetadata var1, ImageWriteParam var2) {
      return null;
   }

   protected static Rectangle getSourceRegion(ImageWriteParam var0, int var1, int var2) {
      return IIOUtil.getSourceRegion(var0, var1, var2);
   }

   protected static BufferedImage fakeAOI(BufferedImage var0, ImageWriteParam var1) {
      return IIOUtil.fakeAOI(var0, getSourceRegion(var1, var0.getWidth(), var0.getHeight()));
   }

   protected static Image fakeSubsampling(Image var0, ImageWriteParam var1) {
      return IIOUtil.fakeSubsampling(var0, var1);
   }
}
