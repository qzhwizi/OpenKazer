package com.twelvemonkeys.image;

import com.twelvemonkeys.lang.Validate;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.awt.image.WritableRaster;
import java.lang.reflect.Array;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BufferedImageFactory {
   private List<BufferedImageFactory.ProgressListener> listeners;
   private int percentageDone;
   private ImageProducer producer;
   private ImageConversionException consumerException;
   private volatile boolean fetching;
   private boolean readColorModelOnly;
   private int x = 0;
   private int y = 0;
   private int width = -1;
   private int height = -1;
   private int xSub = 1;
   private int ySub = 1;
   private int offset;
   private int scanSize;
   private ColorModel sourceColorModel;
   private Hashtable<?, ?> sourceProperties;
   private Object sourcePixels;
   private BufferedImage buffered;
   private ColorModel colorModel;
   private final BufferedImageFactory.Consumer consumer = new BufferedImageFactory.Consumer();

   public BufferedImageFactory(Image var1) {
      this(var1 != null ? var1.getSource() : null);
   }

   public BufferedImageFactory(ImageProducer var1) {
      Validate.notNull(var1, "source");
      this.producer = var1;
   }

   public BufferedImage getBufferedImage() throws ImageConversionException {
      this.doFetch(false);
      return this.buffered;
   }

   public ColorModel getColorModel() throws ImageConversionException {
      this.doFetch(true);
      return this.buffered != null ? this.buffered.getColorModel() : this.colorModel;
   }

   public void dispose() {
      this.freeResources();
      this.buffered = null;
      this.colorModel = null;
   }

   public void abort() {
      this.consumer.imageComplete(4);
   }

   public void setSourceRegion(Rectangle var1) {
      if (this.x != var1.x || this.y != var1.y || this.width != var1.width || this.height != var1.height) {
         this.dispose();
      }

      this.x = var1.x;
      this.y = var1.y;
      this.width = var1.width;
      this.height = var1.height;
   }

   public void setSourceSubsampling(int var1, int var2) {
      if (this.xSub != var1 || this.ySub != var2) {
         this.dispose();
      }

      if (var1 > 1) {
         this.xSub = var1;
      }

      if (var2 > 1) {
         this.ySub = var2;
      }
   }

   private synchronized void doFetch(boolean var1) throws ImageConversionException {
      if (!this.fetching && (!var1 && this.buffered == null || this.buffered == null && this.sourceColorModel == null)) {
         if (!var1 && (this.xSub > 1 || this.ySub > 1)) {
            if (this.width > 0 && this.height > 0) {
               this.width = (this.width + this.xSub - 1) / this.xSub;
               this.height = (this.height + this.ySub - 1) / this.ySub;
               this.x = (this.x + this.xSub - 1) / this.xSub;
               this.y = (this.y + this.ySub - 1) / this.ySub;
            }

            this.producer = new FilteredImageSource(this.producer, new SubsamplingFilter(this.xSub, this.ySub));
         }

         this.fetching = true;
         this.readColorModelOnly = var1;
         this.producer.startProduction(this.consumer);

         while (this.fetching) {
            try {
               this.wait(200L);
            } catch (InterruptedException var7) {
               throw new ImageConversionException("Image conversion aborted: " + var7.getMessage(), var7);
            }
         }

         try {
            if (this.consumerException != null) {
               throw new ImageConversionException("Image conversion failed: " + this.consumerException.getMessage(), this.consumerException);
            }

            if (var1) {
               this.createColorModel();
            } else {
               this.createBuffered();
            }
         } finally {
            this.freeResources();
         }
      }
   }

   private void createColorModel() {
      this.colorModel = this.sourceColorModel;
   }

   private void createBuffered() {
      if (this.width > 0 && this.height > 0) {
         if (this.sourceColorModel != null && this.sourcePixels != null) {
            WritableRaster var1 = ImageUtil.createRaster(this.width, this.height, this.sourcePixels, this.sourceColorModel);
            this.buffered = new BufferedImage(this.sourceColorModel, var1, this.sourceColorModel.isAlphaPremultiplied(), this.sourceProperties);
         } else {
            this.buffered = ImageUtil.createClear(this.width, this.height, null);
         }
      }

      if (this.buffered == null) {
         throw new ImageConversionException("Could not create BufferedImage");
      }
   }

   private void freeResources() {
      this.sourceColorModel = null;
      this.sourcePixels = null;
      this.sourceProperties = null;
   }

   private void processProgress(int var1) {
      if (this.listeners != null) {
         int var2 = 100 * var1 / this.height;
         if (var2 > this.percentageDone) {
            this.percentageDone = var2;

            for (BufferedImageFactory.ProgressListener var4 : this.listeners) {
               var4.progress(this, var2);
            }
         }
      }
   }

   public void addProgressListener(BufferedImageFactory.ProgressListener var1) {
      if (var1 != null) {
         if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList<>();
         }

         this.listeners.add(var1);
      }
   }

   public void removeProgressListener(BufferedImageFactory.ProgressListener var1) {
      if (var1 != null) {
         if (this.listeners != null) {
            this.listeners.remove(var1);
         }
      }
   }

   public void removeAllProgressListeners() {
      if (this.listeners != null) {
         this.listeners.clear();
      }
   }

   private static short[] toShortPixels(int[] var0) {
      short[] var1 = new short[var0.length];

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = (short)(var0[var2] & 65535);
      }

      return var1;
   }

   private class Consumer implements ImageConsumer {
      private Consumer() {
      }

      private void setPixelsImpl(int var1, int var2, int var3, int var4, ColorModel var5, Object var6, int var7, int var8) {
         this.setColorModelOnce(var5);
         if (var6 != null) {
            if (BufferedImageFactory.this.sourcePixels == null) {
               BufferedImageFactory.this.sourcePixels = Array.newInstance(
                  var6.getClass().getComponentType(), BufferedImageFactory.this.width * BufferedImageFactory.this.height
               );
               BufferedImageFactory.this.scanSize = BufferedImageFactory.this.width;
               BufferedImageFactory.this.offset = 0;
            } else if (BufferedImageFactory.this.sourcePixels.getClass() != var6.getClass()) {
               throw new IllegalStateException("Only one pixel type allowed");
            }

            if (var2 < BufferedImageFactory.this.y) {
               int var9 = BufferedImageFactory.this.y - var2;
               if (var9 >= var4) {
                  return;
               }

               var7 += var8 * var9;
               var2 += var9;
               var4 -= var9;
            }

            if (var2 + var4 > BufferedImageFactory.this.y + BufferedImageFactory.this.height) {
               var4 = BufferedImageFactory.this.y + BufferedImageFactory.this.height - var2;
               if (var4 <= 0) {
                  return;
               }
            }

            if (var1 < BufferedImageFactory.this.x) {
               int var11 = BufferedImageFactory.this.x - var1;
               if (var11 >= var3) {
                  return;
               }

               var7 += var11;
               var1 += var11;
               var3 -= var11;
            }

            if (var1 + var3 > BufferedImageFactory.this.x + BufferedImageFactory.this.width) {
               var3 = BufferedImageFactory.this.x + BufferedImageFactory.this.width - var1;
               if (var3 <= 0) {
                  return;
               }
            }

            int var12 = BufferedImageFactory.this.offset
               + (var2 - BufferedImageFactory.this.y) * BufferedImageFactory.this.scanSize
               + (var1 - BufferedImageFactory.this.x);

            for (int var10 = var4; var10 > 0; var10--) {
               System.arraycopy(var6, var7, BufferedImageFactory.this.sourcePixels, var12, var3);
               var7 += var8;
               var12 += BufferedImageFactory.this.scanSize;
            }

            BufferedImageFactory.this.processProgress(var2 + var4);
         }
      }

      public void setPixels(int var1, int var2, int var3, int var4, ColorModel var5, short[] var6, int var7, int var8) {
         this.setPixelsImpl(var1, var2, var3, var4, var5, var6, var7, var8);
      }

      private void setColorModelOnce(ColorModel var1) {
         if (BufferedImageFactory.this.sourceColorModel != var1) {
            if (BufferedImageFactory.this.sourcePixels != null) {
               throw new IllegalStateException("Change of ColorModel after pixel delivery not supported");
            }

            BufferedImageFactory.this.sourceColorModel = var1;
         }

         if (BufferedImageFactory.this.readColorModelOnly) {
            BufferedImageFactory.this.consumer.imageComplete(4);
         }
      }

      @Override
      public void imageComplete(int var1) {
         BufferedImageFactory.this.fetching = false;
         if (BufferedImageFactory.this.producer != null) {
            BufferedImageFactory.this.producer.removeConsumer(this);
         }

         if (var1 == 1) {
            BufferedImageFactory.this.consumerException = new ImageConversionException("ImageConsumer.IMAGEERROR");
         }

         synchronized (BufferedImageFactory.this) {
            BufferedImageFactory.this.notifyAll();
         }
      }

      @Override
      public void setColorModel(ColorModel var1) {
         this.setColorModelOnce(var1);
      }

      @Override
      public void setDimensions(int var1, int var2) {
         if (BufferedImageFactory.this.width < 0) {
            BufferedImageFactory.this.width = var1 - BufferedImageFactory.this.x;
         }

         if (BufferedImageFactory.this.height < 0) {
            BufferedImageFactory.this.height = var2 - BufferedImageFactory.this.y;
         }

         if (BufferedImageFactory.this.width <= 0 || BufferedImageFactory.this.height <= 0) {
            this.imageComplete(3);
         }
      }

      @Override
      public void setHints(int var1) {
      }

      @Override
      public void setPixels(int var1, int var2, int var3, int var4, ColorModel var5, byte[] var6, int var7, int var8) {
         this.setPixelsImpl(var1, var2, var3, var4, var5, var6, var7, var8);
      }

      @Override
      public void setPixels(int var1, int var2, int var3, int var4, ColorModel var5, int[] var6, int var7, int var8) {
         if (var5.getTransferType() == 1) {
            this.setPixelsImpl(var1, var2, var3, var4, var5, BufferedImageFactory.toShortPixels(var6), var7, var8);
         } else {
            this.setPixelsImpl(var1, var2, var3, var4, var5, var6, var7, var8);
         }
      }

      @Override
      public void setProperties(Hashtable var1) {
         BufferedImageFactory.this.sourceProperties = var1;
      }
   }

   public interface ProgressListener extends EventListener {
      void progress(BufferedImageFactory var1, float var2);
   }
}
