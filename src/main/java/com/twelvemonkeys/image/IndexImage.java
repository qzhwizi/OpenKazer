package com.twelvemonkeys.image;

import com.twelvemonkeys.io.FileUtil;
import com.twelvemonkeys.lang.StringUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;

class IndexImage {
   protected static final int DITHER_MASK = 255;
   public static final int DITHER_DEFAULT = 0;
   public static final int DITHER_NONE = 1;
   public static final int DITHER_DIFFUSION = 2;
   public static final int DITHER_DIFFUSION_ALTSCANS = 3;
   protected static final int COLOR_SELECTION_MASK = 65280;
   public static final int COLOR_SELECTION_DEFAULT = 0;
   public static final int COLOR_SELECTION_FAST = 256;
   public static final int COLOR_SELECTION_QUALITY = 512;
   protected static final int TRANSPARENCY_MASK = 16711680;
   public static final int TRANSPARENCY_DEFAULT = 0;
   public static final int TRANSPARENCY_OPAQUE = 65536;
   public static final int TRANSPARENCY_BITMASK = 131072;
   protected static final int TRANSPARENCY_TRANSLUCENT = 196608;

   private IndexImage() {
   }

   @Deprecated
   public static IndexColorModel getIndexColorModel(Image var0, int var1, boolean var2) {
      return getIndexColorModel(var0, var1, var2 ? 256 : 512);
   }

   public static IndexColorModel getIndexColorModel(Image var0, int var1, int var2) throws ImageConversionException {
      Object var3 = null;
      RenderedImage var4 = null;
      if (var0 instanceof RenderedImage) {
         var4 = (RenderedImage)var0;
         ColorModel var5 = var4.getColorModel();
         if (var5 instanceof IndexColorModel && ((IndexColorModel)var5).getMapSize() <= var1) {
            var3 = (IndexColorModel)var5;
         }
      } else {
         BufferedImageFactory var7 = new BufferedImageFactory(var0);
         ColorModel var6 = var7.getColorModel();
         if (var6 instanceof IndexColorModel && ((IndexColorModel)var6).getMapSize() <= var1) {
            var3 = (IndexColorModel)var6;
         } else {
            var4 = var7.getBufferedImage();
         }
      }

      if (var3 == null) {
         var3 = createIndexColorModel(ImageUtil.toBuffered((RenderedImage)var4), var1, var2);
      } else if (!(var3 instanceof InverseColorMapIndexColorModel)) {
         var3 = new InverseColorMapIndexColorModel((IndexColorModel)var3);
      }

      return (IndexColorModel)var3;
   }

   private static IndexColorModel createIndexColorModel(BufferedImage var0, int var1, int var2) {
      boolean var3 = isTransparent(var2);
      if (var3) {
         var1--;
      }

      int var4 = var0.getWidth();
      int var5 = var0.getHeight();
      List[] var6 = new List[4096];
      int var7 = 1;
      if (isFast(var2)) {
         var7 += var4 * var5 / 16384;
      }

      int var8 = 0;

      for (int var10 = 0; var10 < var4; var10++) {
         label123:
         for (int var11 = var10 % var7; var11 < var5; var11 += var7) {
            var8++;
            int var9 = var0.getRGB(var10, var11) & 16777215;
            int var12 = (var9 & 15728640) >>> 12 | (var9 & 61440) >>> 8 | (var9 & 240) >>> 4;
            List var13 = var6[var12];
            if (var13 == null) {
               ArrayList var21 = new ArrayList();
               var21.add(new IndexImage.Counter(var9));
               var6[var12] = var21;
            } else {
               Iterator var14 = var13.iterator();

               while (var14.hasNext()) {
                  if (((IndexImage.Counter)var14.next()).add(var9)) {
                     continue label123;
                  }
               }

               var13.add(new IndexImage.Counter(var9));
            }
         }
      }

      int var18 = 1;
      int var19 = 0;
      IndexImage.Cube[] var20 = new IndexImage.Cube[var1];
      var20[0] = new IndexImage.Cube(var6, var8);

      while (var18 < var1) {
         while (var20[var19].isDone()) {
            if (++var19 == var18) {
               break;
            }
         }

         if (var19 == var18) {
            break;
         }

         IndexImage.Cube var22 = var20[var19];
         IndexImage.Cube var24 = var22.split();
         if (var24 != null) {
            if (var24.count > var22.count) {
               IndexImage.Cube var15 = var22;
               var22 = var24;
               var24 = var15;
            }

            int var26 = var19;
            int var16 = var22.count;

            for (int var17 = var19 + 1; var17 < var18 && var20[var17].count >= var16; var17++) {
               var20[var26++] = var20[var17];
            }

            var20[var26++] = var22;
            var16 = var24.count;

            while (var26 < var18 && var20[var26].count >= var16) {
               var26++;
            }

            System.arraycopy(var20, var26, var20, var26 + 1, var18 - var26);
            var20[var26] = var24;
            var18++;
         }
      }

      byte[] var23 = new byte[var3 ? var18 + 1 : var18];
      byte[] var25 = new byte[var3 ? var18 + 1 : var18];
      byte[] var28 = new byte[var3 ? var18 + 1 : var18];

      for (int var30 = 0; var30 < var18; var30++) {
         int var32 = var20[var30].averageColor();
         var23[var30] = (byte)(var32 >> 16 & 0xFF);
         var25[var30] = (byte)(var32 >> 8 & 0xFF);
         var28[var30] = (byte)(var32 & 0xFF);
      }

      byte var31 = 8;
      InverseColorMapIndexColorModel var33;
      if (var3) {
         var33 = new InverseColorMapIndexColorModel(var31, var23.length, var23, var25, var28, var23.length - 1);
      } else {
         var33 = new InverseColorMapIndexColorModel(var31, var23.length, var23, var25, var28);
      }

      return var33;
   }

   public static BufferedImage getIndexedImage(BufferedImage var0) {
      return getIndexedImage(var0, 256, 0);
   }

   private static boolean isFast(int var0) {
      return (var0 & 0xFF00) != 512;
   }

   static boolean isTransparent(int var0) {
      return (var0 & 131072) != 0 || (var0 & 196608) != 0;
   }

   public static BufferedImage getIndexedImage(BufferedImage var0, Image var1, Color var2, int var3) throws ImageConversionException {
      return getIndexedImage(var0, getIndexColorModel(var1, 256, var3), var2, var3);
   }

   public static BufferedImage getIndexedImage(BufferedImage var0, int var1, Color var2, int var3) {
      IndexColorModel var4;
      if (var2 != null) {
         var4 = getIndexColorModel(createSolid(var0, var2), var1, var3);
      } else {
         var4 = getIndexColorModel(var0, var1, var3);
      }

      if ((var3 & 0xFF) != 1 && var4.getMapSize() < var1) {
         var3 = var3 & -256 | 1;
      }

      return getIndexedImage(var0, var4, var2, var3);
   }

   public static BufferedImage getIndexedImage(BufferedImage var0, IndexColorModel var1, Color var2, int var3) {
      int var4 = var0.getWidth();
      int var5 = var0.getHeight();
      boolean var6 = isTransparent(var3) && var0.getColorModel().getTransparency() != 1 && var1.getTransparency() != 1;
      BufferedImage var7 = var0;
      if (var2 != null) {
         var7 = createSolid(var0, var2);
      }

      BufferedImage var8;
      if (var1.getMapSize() > 2) {
         var8 = new BufferedImage(var4, var5, 13, var1);
      } else {
         var8 = new BufferedImage(var4, var5, 12, var1);
      }

      switch (var3 & 0xFF) {
         case 0:
         default:
            Graphics2D var11 = var8.createGraphics();

            try {
               RenderingHints var12 = new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
               var11.setRenderingHints(var12);
               var11.drawImage(var7, 0, 0, null);
               break;
            } finally {
               var11.dispose();
            }
         case 1:
            CopyDither var10 = new CopyDither(var1);
            var10.filter(var7, var8);
            break;
         case 2:
         case 3:
            DiffusionDither var9 = new DiffusionDither(var1);
            if ((var3 & 0xFF) == 3) {
               var9.setAlternateScans(true);
            }

            var9.filter(var7, var8);
      }

      if (var6) {
         applyAlpha(var8, var0);
      }

      return var8;
   }

   public static BufferedImage getIndexedImage(BufferedImage var0, int var1, int var2) {
      return getIndexedImage(var0, var1, null, var2);
   }

   public static BufferedImage getIndexedImage(BufferedImage var0, IndexColorModel var1, int var2) {
      return getIndexedImage(var0, var1, null, var2);
   }

   public static BufferedImage getIndexedImage(BufferedImage var0, Image var1, int var2) {
      return getIndexedImage(var0, var1, null, var2);
   }

   private static BufferedImage createSolid(BufferedImage var0, Color var1) {
      BufferedImage var2 = new BufferedImage(var0.getColorModel(), var0.copyData(null), var0.isAlphaPremultiplied(), null);
      Graphics2D var3 = var2.createGraphics();

      try {
         var3.setColor(var1);
         var3.setComposite(AlphaComposite.DstOver);
         var3.fillRect(0, 0, var0.getWidth(), var0.getHeight());
      } finally {
         var3.dispose();
      }

      return var2;
   }

   private static void applyAlpha(BufferedImage var0, BufferedImage var1) {
      for (int var2 = 0; var2 < var1.getHeight(); var2++) {
         for (int var3 = 0; var3 < var1.getWidth(); var3++) {
            if ((var1.getRGB(var3, var2) >> 24 & 0xFF) < 64) {
               var0.setRGB(var3, var2, 16777215);
            }
         }
      }
   }

   public static void main(String[] var0) {
      int var1 = 0;
      int var2 = -1;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      int var6 = 256;
      String var7 = null;
      String var8 = null;
      String var9 = null;
      Color var10 = null;
      boolean var11 = false;
      String var12 = null;
      boolean var13 = false;

      while (var1 < var0.length && var0[var1].charAt(0) == '-' && var0[var1].length() >= 2) {
         if (var0[var1].charAt(1) == 's' || var0[var1].equals("--speedtest")) {
            var1++;
            if (var0.length > var1 && var0[var1].charAt(0) != '-') {
               try {
                  var2 = Integer.parseInt(var0[var1++]);
               } catch (NumberFormatException var31) {
                  var13 = true;
                  break;
               }
            } else {
               var2 = 10;
            }
         } else if (var0[var1].charAt(1) == 'w' || var0[var1].equals("--overwrite")) {
            var3 = true;
            var1++;
         } else if (var0[var1].charAt(1) == 'c' || var0[var1].equals("--colors")) {
            var1++;

            try {
               var6 = Integer.parseInt(var0[var1++]);
            } catch (NumberFormatException var30) {
               var13 = true;
               break;
            }
         } else if (var0[var1].charAt(1) == 'g' || var0[var1].equals("--grayscale")) {
            var1++;
            var5 = true;
         } else if (var0[var1].charAt(1) == 'm' || var0[var1].equals("--monochrome")) {
            var1++;
            var6 = 2;
            var4 = true;
         } else if (var0[var1].charAt(1) == 'd' || var0[var1].equals("--dither")) {
            int var48 = ++var1;
            var1++;
            var7 = var0[var48];
         } else if (var0[var1].charAt(1) == 'p' || var0[var1].equals("--palette")) {
            int var47 = ++var1;
            var1++;
            var12 = var0[var47];
         } else if (var0[var1].charAt(1) == 'q' || var0[var1].equals("--quality")) {
            int var46 = ++var1;
            var1++;
            var8 = var0[var46];
         } else if (var0[var1].charAt(1) == 'b' || var0[var1].equals("--bgcolor")) {
            var1++;

            try {
               var10 = StringUtil.toColor(var0[var1++]);
            } catch (Exception var29) {
               var13 = true;
               break;
            }
         } else if (var0[var1].charAt(1) == 't' || var0[var1].equals("--transparency")) {
            var1++;
            var11 = true;
         } else if (var0[var1].charAt(1) == 'f' || var0[var1].equals("--outputformat")) {
            int var44 = ++var1;
            var1++;
            var9 = StringUtil.toLowerCase(var0[var44]);
         } else if (var0[var1].charAt(1) != 'h' && !var0[var1].equals("--help")) {
            System.err.println("Unknown option \"" + var0[var1++] + "\"");
         } else {
            var1++;
            var13 = true;
         }
      }

      if (var13 || var0.length < var1 + 1) {
         System.err
            .println(
               "Usage: IndexImage [--help|-h] [--speedtest|-s <integer>] [--bgcolor|-b <color>] [--colors|-c <integer> | --grayscale|g | --monochrome|-m | --palette|-p <file>] [--dither|-d (default|diffusion|none)] [--quality|-q (default|high|low)] [--transparency|-t] [--outputformat|-f (gif|jpeg|png|wbmp|...)] [--overwrite|-w] <input> [<output>]"
            );
         System.err.print("Input format names: ");
         String[] var14 = ImageIO.getReaderFormatNames();

         for (int var15 = 0; var15 < var14.length; var15++) {
            System.err.print(var14[var15] + (var15 + 1 < var14.length ? ", " : "\n"));
         }

         System.err.print("Output format names: ");
         String[] var38 = ImageIO.getWriterFormatNames();

         for (int var16 = 0; var16 < var38.length; var16++) {
            System.err.print(var38[var16] + (var16 + 1 < var38.length ? ", " : "\n"));
         }

         System.exit(5);
      }

      File var37 = new File(var0[var1++]);
      if (!var37.exists()) {
         System.err.println("File \"" + var37.getAbsolutePath() + "\" does not exist!");
         System.exit(5);
      }

      File var39 = null;
      if (var12 != null) {
         var39 = new File(var12);
         if (!var39.exists()) {
            System.err.println("File \"" + var37.getAbsolutePath() + "\" does not exist!");
            System.exit(5);
         }
      }

      File var40;
      if (var1 < var0.length) {
         var40 = new File(var0[var1]);
         if (var9 == null) {
            var9 = FileUtil.getExtension(var40);
         }
      } else {
         String var17 = FileUtil.getBasename(var37);
         if (var9 == null) {
            var9 = "png";
         }

         var40 = new File(var17 + '.' + var9);
      }

      if (!var3 && var40.exists()) {
         System.err.println("The file \"" + var40.getAbsolutePath() + "\" allready exists!");
         System.exit(5);
      }

      BufferedImage var41 = null;
      BufferedImage var18 = null;

      try {
         var41 = ImageIO.read(var37);
         if (var41 == null) {
            System.err.println("No reader for image: \"" + var37.getAbsolutePath() + "\"!");
            System.exit(5);
         }

         if (var39 != null) {
            var18 = ImageIO.read(var39);
            if (var18 == null) {
               System.err.println("No reader for image: \"" + var39.getAbsolutePath() + "\"!");
               System.exit(5);
            }
         }
      } catch (IOException var28) {
         var28.printStackTrace(System.err);
         System.exit(5);
      }

      int var19 = 0;
      if ("DIFFUSION".equalsIgnoreCase(var7)) {
         var19 |= 2;
      } else if ("DIFFUSION_ALTSCANS".equalsIgnoreCase(var7)) {
         var19 |= 3;
      } else if ("NONE".equalsIgnoreCase(var7)) {
         var19 |= 1;
      }

      if ("HIGH".equalsIgnoreCase(var8)) {
         var19 |= 512;
      } else if ("LOW".equalsIgnoreCase(var8)) {
         var19 |= 256;
      }

      if (var11) {
         var19 |= 131072;
      }

      if (var10 != null && var18 == null) {
         var18 = createSolid(var41, var10);
      }

      long var20 = 0L;
      if (var2 > 0) {
         System.out.println("Measuring speed!");
         var20 = System.currentTimeMillis();
      }

      BufferedImage var22;
      IndexColorModel var23;
      if (var4) {
         var22 = getIndexedImage(var41, MonochromeColorModel.getInstance(), var10, var19);
         var23 = MonochromeColorModel.getInstance();
      } else if (var5) {
         var41 = ImageUtil.toBuffered(ImageUtil.grayscale(var41));
         var22 = getIndexedImage(var41, var23 = getIndexColorModel(var41, var6, var19), var10, var19);
         if (var2 > 0) {
            var23 = getIndexColorModel(var22, var6, var19);
         }
      } else if (var18 != null) {
         var22 = getIndexedImage(ImageUtil.toBuffered(var41, 2), var23 = getIndexColorModel(var18, var6, var19), var10, var19);
      } else {
         var41 = ImageUtil.toBuffered(var41, 2);
         var22 = getIndexedImage(var41, var23 = getIndexColorModel(var41, var6, var19), var10, var19);
      }

      if (var2 > 0) {
         System.out.println("Color selection + dither: " + (System.currentTimeMillis() - var20) + " ms");
      }

      try {
         if (!ImageIO.write(var22, var9, var40)) {
            System.err.println("No writer for format: \"" + var9 + "\"!");
         }
      } catch (IOException var27) {
         var27.printStackTrace(System.err);
      }

      if (var2 > 0) {
         System.out.println("Measuring speed!");

         for (int var24 = 0; var24 < 10; var24++) {
            getIndexedImage(var41, var23, var10, var19);
         }

         long var43 = 0L;

         for (int var26 = 0; var26 < var2; var26++) {
            var20 = System.currentTimeMillis();
            getIndexedImage(var41, var23, var10, var19);
            var43 += System.currentTimeMillis() - var20;
            System.out.print('.');
            if ((var26 + 1) % 10 == 0) {
               System.out.println("\nAverage (after " + (var26 + 1) + " iterations): " + var43 / (var26 + 1) + "ms");
            }
         }

         System.out.println("\nDither only:");
         System.out.println("Total time (" + var2 + " invocations): " + var43 + "ms");
         System.out.println("Average: " + var43 / var2 + "ms");
      }
   }

   private static class Counter {
      public int val;
      public int count = 1;

      public Counter(int var1) {
         this.val = var1;
      }

      public boolean add(int var1) {
         if (this.val != var1) {
            return false;
         } else {
            this.count++;
            return true;
         }
      }
   }

   private static class Cube {
      int[] min = new int[]{0, 0, 0};
      int[] max = new int[]{255, 255, 255};
      boolean done = false;
      List<IndexImage.Counter>[] colors = null;
      int count = 0;
      static final int RED = 0;
      static final int GRN = 1;
      static final int BLU = 2;

      public Cube(List<IndexImage.Counter>[] var1, int var2) {
         this.colors = var1;
         this.count = var2;
      }

      public boolean isDone() {
         return this.done;
      }

      public IndexImage.Cube split() {
         int var1 = this.max[0] - this.min[0] + 1;
         int var2 = this.max[1] - this.min[1] + 1;
         int var3 = this.max[2] - this.min[2] + 1;
         byte var4;
         byte var5;
         byte var6;
         if (var1 >= var2) {
            var4 = 1;
            if (var1 >= var3) {
               var6 = 0;
               var5 = 2;
            } else {
               var6 = 2;
               var5 = 0;
            }
         } else if (var2 >= var3) {
            var6 = 1;
            var4 = 0;
            var5 = 2;
         } else {
            var6 = 2;
            var4 = 0;
            var5 = 1;
         }

         IndexImage.Cube var7 = this.splitChannel(var6, var4, var5);
         if (var7 != null) {
            return var7;
         } else {
            var7 = this.splitChannel(var4, var6, var5);
            if (var7 != null) {
               return var7;
            } else {
               var7 = this.splitChannel(var5, var6, var4);
               if (var7 != null) {
                  return var7;
               } else {
                  this.done = true;
                  return null;
               }
            }
         }
      }

      public IndexImage.Cube splitChannel(int var1, int var2, int var3) {
         if (this.min[var1] == this.max[var1]) {
            return null;
         } else {
            int var4 = (2 - var1) * 4;
            int var5 = (2 - var2) * 4;
            int var6 = (2 - var3) * 4;
            int var7 = this.count / 2;
            int[] var8 = new int[256];
            int var9 = 0;
            int[] var10 = new int[]{this.min[0] >> 4, this.min[1] >> 4, this.min[2] >> 4};
            int[] var11 = new int[]{this.max[0] >> 4, this.max[1] >> 4, this.max[2] >> 4};
            int var12 = this.min[0];
            int var13 = this.min[1];
            int var14 = this.min[2];
            int var15 = this.max[0];
            int var16 = this.max[1];
            int var17 = this.max[2];
            int[] var19 = new int[]{0, 0, 0};

            for (int var20 = var10[var1]; var20 <= var11[var1]; var20++) {
               int var21 = var20 << var4;

               for (int var22 = var10[var2]; var22 <= var11[var2]; var22++) {
                  int var23 = var21 | var22 << var5;

                  for (int var24 = var10[var3]; var24 <= var11[var3]; var24++) {
                     int var25 = var23 | var24 << var6;
                     List var26 = this.colors[var25];
                     if (var26 != null) {
                        for (IndexImage.Counter var28 : (Iterable<Counter>) var26) {
                           int var18 = var28.val;
                           var19[0] = (var18 & 0xFF0000) >> 16;
                           var19[1] = (var18 & 0xFF00) >> 8;
                           var19[2] = var18 & 0xFF;
                           if (var19[0] >= var12 && var19[0] <= var15 && var19[1] >= var13 && var19[1] <= var16 && var19[2] >= var14 && var19[2] <= var17) {
                              var8[var19[var1]] = var8[var19[var1]] + var28.count;
                              var9 += var28.count;
                           }
                        }
                     }
                  }
               }

               if (var9 >= var7) {
                  break;
               }
            }

            var9 = 0;
            int var30 = -1;
            int var31 = this.min[var1];
            int var32 = this.max[var1];

            for (int var33 = this.min[var1]; var33 <= this.max[var1]; var33++) {
               int var35 = var8[var33];
               if (var35 == 0) {
                  if (var9 == 0 && var33 < this.max[var1]) {
                     this.min[var1] = var33 + 1;
                  }
               } else {
                  if (var9 + var35 >= var7) {
                     if (var7 - var9 <= var9 + var35 - var7) {
                        if (var30 == -1) {
                           if (var35 == this.count) {
                              this.max[var1] = var33;
                              return null;
                           }

                           var31 = var33;
                           var32 = var33 + 1;
                        } else {
                           var31 = var30;
                           var32 = var33;
                        }
                     } else if (var33 == this.max[var1]) {
                        if (var35 == this.count) {
                           return null;
                        }

                        var31 = var30;
                        var32 = var33;
                     } else {
                        var9 += var35;
                        var31 = var33;
                        var32 = var33 + 1;
                     }
                     break;
                  }

                  var30 = var33;
                  var9 += var35;
               }
            }

            IndexImage.Cube var34 = new IndexImage.Cube(this.colors, var9);
            this.count -= var9;
            var34.min[var1] = this.min[var1];
            var34.max[var1] = var31;
            this.min[var1] = var32;
            var34.min[var2] = this.min[var2];
            var34.max[var2] = this.max[var2];
            var34.min[var3] = this.min[var3];
            var34.max[var3] = this.max[var3];
            return var34;
         }
      }

      public int averageColor() {
         if (this.count == 0) {
            return 0;
         } else {
            float var1 = 0.0F;
            float var2 = 0.0F;
            float var3 = 0.0F;
            int var4 = this.min[0];
            int var5 = this.min[1];
            int var6 = this.min[2];
            int var7 = this.max[0];
            int var8 = this.max[1];
            int var9 = this.max[2];
            int[] var10 = new int[]{var4 >> 4, var5 >> 4, var6 >> 4};
            int[] var11 = new int[]{var7 >> 4, var8 >> 4, var9 >> 4};

            for (int var17 = var10[0]; var17 <= var11[0]; var17++) {
               int var18 = var17 << 8;

               for (int var19 = var10[1]; var19 <= var11[1]; var19++) {
                  int var20 = var18 | var19 << 4;

                  for (int var21 = var10[2]; var21 <= var11[2]; var21++) {
                     int var22 = var20 | var21;
                     List var23 = this.colors[var22];
                     if (var23 != null) {
                        for (IndexImage.Counter var25 : (Iterable<Counter>) var23) {
                           int var12 = var25.val;
                           int var13 = (var12 & 0xFF0000) >> 16;
                           int var14 = (var12 & 0xFF00) >> 8;
                           int var15 = var12 & 0xFF;
                           if (var13 >= var4 && var13 <= var7 && var14 >= var5 && var14 <= var8 && var15 >= var6 && var15 <= var9) {
                              float var16 = (float)var25.count / this.count;
                              var1 += var13 * var16;
                              var2 += var14 * var16;
                              var3 += var15 * var16;
                           }
                        }
                     }
                  }
               }
            }

            return (int)(var1 + 0.5F) << 16 | (int)(var2 + 0.5F) << 8 | (int)(var3 + 0.5F);
         }
      }
   }
}
