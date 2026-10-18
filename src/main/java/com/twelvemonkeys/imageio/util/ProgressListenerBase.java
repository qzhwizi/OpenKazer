package com.twelvemonkeys.imageio.util;

import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.event.IIOWriteProgressListener;

public abstract class ProgressListenerBase implements IIOReadProgressListener, IIOWriteProgressListener {
   protected ProgressListenerBase() {
   }

   @Override
   public void imageComplete(ImageReader var1) {
   }

   @Override
   public void imageProgress(ImageReader var1, float var2) {
   }

   @Override
   public void imageStarted(ImageReader var1, int var2) {
   }

   @Override
   public void readAborted(ImageReader var1) {
   }

   @Override
   public void sequenceComplete(ImageReader var1) {
   }

   @Override
   public void sequenceStarted(ImageReader var1, int var2) {
   }

   @Override
   public void thumbnailComplete(ImageReader var1) {
   }

   @Override
   public void thumbnailProgress(ImageReader var1, float var2) {
   }

   @Override
   public void thumbnailStarted(ImageReader var1, int var2, int var3) {
   }

   @Override
   public void imageComplete(ImageWriter var1) {
   }

   @Override
   public void imageProgress(ImageWriter var1, float var2) {
   }

   @Override
   public void imageStarted(ImageWriter var1, int var2) {
   }

   @Override
   public void thumbnailComplete(ImageWriter var1) {
   }

   @Override
   public void thumbnailProgress(ImageWriter var1, float var2) {
   }

   @Override
   public void thumbnailStarted(ImageWriter var1, int var2, int var3) {
   }

   @Override
   public void writeAborted(ImageWriter var1) {
   }
}
