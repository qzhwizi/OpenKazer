package com.twelvemonkeys.image;

import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractImageSource implements ImageProducer {
   private List<ImageConsumer> consumers = new ArrayList<>();
   protected int width;
   protected int height;
   protected int xOff;
   protected int yOff;

   @Override
   public void addConsumer(ImageConsumer var1) {
      if (!this.consumers.contains(var1)) {
         this.consumers.add(var1);

         try {
            this.initConsumer(var1);
            this.sendPixels(var1);
            if (this.isConsumer(var1)) {
               var1.imageComplete(3);
               if (this.isConsumer(var1)) {
                  var1.imageComplete(1);
                  this.removeConsumer(var1);
               }
            }
         } catch (Exception var3) {
            var3.printStackTrace();
            if (this.isConsumer(var1)) {
               var1.imageComplete(1);
            }
         }
      }
   }

   @Override
   public void removeConsumer(ImageConsumer var1) {
      this.consumers.remove(var1);
   }

   @Override
   public void requestTopDownLeftRightResend(ImageConsumer var1) {
   }

   @Override
   public void startProduction(ImageConsumer var1) {
      this.addConsumer(var1);
   }

   @Override
   public boolean isConsumer(ImageConsumer var1) {
      return this.consumers.contains(var1);
   }

   protected abstract void initConsumer(ImageConsumer var1);

   protected abstract void sendPixels(ImageConsumer var1);
}
