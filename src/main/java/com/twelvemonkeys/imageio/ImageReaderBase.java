package com.twelvemonkeys.imageio;

import com.twelvemonkeys.image.BufferedImageIcon;
import com.twelvemonkeys.image.ImageUtil;
import com.twelvemonkeys.imageio.util.IIOUtil;
import com.twelvemonkeys.lang.Validate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public abstract class ImageReaderBase extends ImageReader {
   private static final Point ORIGIN = new Point(0, 0);
   private static final String MAX_IMAGE_BYTES_PROPERTY = "com.twelvemonkeys.imageio.maxImageBytes";
   private static final long MAX_IMAGE_BYTES = resolveMaxImageBytes();
   protected ImageInputStream imageInput;

   protected ImageReaderBase(ImageReaderSpi var1) {
      super(var1);
   }

   @Override
   public void setInput(Object var1, boolean var2, boolean var3) {
      this.resetMembers();
      super.setInput(var1, var2, var3);
      if (var1 instanceof ImageInputStream) {
         this.imageInput = (ImageInputStream)var1;
      } else {
         this.imageInput = null;
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

   protected abstract void resetMembers();

   @Override
   public IIOMetadata getImageMetadata(int var1) throws IOException {
      return null;
   }

   @Override
   public IIOMetadata getStreamMetadata() throws IOException {
      return null;
   }

   @Override
   public int getNumImages(boolean var1) throws IOException {
      this.assertInput();
      return 1;
   }

   protected void checkBounds(int var1) throws IOException {
      this.assertInput();
      if (var1 < this.getMinIndex()) {
         throw new IndexOutOfBoundsException("index < minIndex");
      } else {
         int var2 = this.getNumImages(false);
         if (var2 != -1 && var1 >= var2) {
            throw new IndexOutOfBoundsException("index >= numImages (" + var1 + " >= " + var2 + ")");
         }
      }
   }

   protected void assertInput() {
      if (this.getInput() == null) {
         throw new IllegalStateException("getInput() == null");
      }
   }

   public static BufferedImage getDestination(ImageReadParam var0, Iterator<ImageTypeSpecifier> var1, int var2, int var3) throws IIOException {
      if (var1 != null && var1.hasNext()) {
         ImageTypeSpecifier var4 = null;
         if (var0 != null) {
            BufferedImage var5 = var0.getDestination();
            if (var5 != null) {
               boolean var19 = false;

               while (var1.hasNext()) {
                  ImageTypeSpecifier var20 = (ImageTypeSpecifier)var1.next();
                  int var21 = var20.getBufferedImageType();
                  if (var21 != 0 && var21 == var5.getType()) {
                     var19 = true;
                     break;
                  }

                  if (var20.getSampleModel().getTransferType() == var5.getSampleModel().getTransferType()
                     && Arrays.equals(var20.getSampleModel().getSampleSize(), var5.getSampleModel().getSampleSize())
                     && var20.getNumBands() <= var5.getSampleModel().getNumBands()) {
                     var19 = true;
                     break;
                  }
               }

               if (!var19) {
                  throw new IIOException(String.format("Destination image from ImageReadParam does not match legal imageTypes from reader: %s", var5));
               }

               return var5;
            }

            var4 = var0.getDestinationType();
         }

         if (var4 == null) {
            var4 = (ImageTypeSpecifier)var1.next();
         } else {
            boolean var16 = false;

            while (var1.hasNext()) {
               ImageTypeSpecifier var6 = (ImageTypeSpecifier)var1.next();
               if (var6.equals(var4)) {
                  var16 = true;
                  break;
               }
            }

            if (!var16) {
               throw new IIOException(String.format("Destination type from ImageReadParam does not match legal imageTypes from reader: %s", var4));
            }
         }

         Rectangle var17 = new Rectangle(0, 0, 0, 0);
         Rectangle var18 = new Rectangle(0, 0, 0, 0);
         computeRegions(var0, var2, var3, null, var17, var18);
         int var7 = var18.x + var18.width;
         int var8 = var18.y + var18.height;
         long var9 = (long)var7 * var8;
         if (var9 > 2147483647L) {
            throw new IIOException(String.format("destination width * height > Integer.MAX_VALUE: %d", var9));
         } else {
            SampleModel var11 = var4.getSampleModel();
            long var12 = var9 * var11.getNumDataElements();
            if (var12 > 2147483647L) {
               throw new IIOException(String.format("destination width * height * samplesPerPixel > Integer.MAX_VALUE: %d", var12));
            } else {
               try {
                  return var4.createBufferedImage(var7, var8);
               } catch (OutOfMemoryError var15) {
                  throw new IIOException("Unable to allocate destination image, out of memory", var15);
               }
            }
         }
      } else {
         throw new IllegalArgumentException("imageTypes null or empty!");
      }
   }

   static long computeByteSize(ImageTypeSpecifier var0, int var1, int var2) {
      Validate.notNull(var0, "specifier");
      Validate.isTrue(var1 > 0 && var2 > 0, "width > 0, height > 0");
      SampleModel var3 = var0.getSampleModel();
      int var4 = DataBuffer.getDataTypeSize(var3.getDataType());
      int var5 = var4 / 8;
      if (var3 instanceof ComponentSampleModel) {
         int var9 = var3.getNumDataElements();
         return (long)var2 * var1 * var9 * var5;
      } else if (var3 instanceof SinglePixelPackedSampleModel) {
         return (long)var2 * var1 * var5;
      } else if (var3 instanceof MultiPixelPackedSampleModel) {
         MultiPixelPackedSampleModel var6 = (MultiPixelPackedSampleModel)var3;
         long var7 = ((long)var1 * var6.getPixelBitStride() + var4 - 1L) / var4;
         return var2 * var7 * var5;
      } else {
         throw new AssertionError("Unknown SampleModel type: " + var3.getClass());
      }
   }

   public static void validateSourceSize(ImageTypeSpecifier var0, int var1, int var2, long var3, int var5) throws IIOException {
      Validate.isTrue(var5 >= 1, "maxExpansionRatio must be >= 1");
      long var6 = computeByteSize(var0, var1, var2);
      validateSourceSize(var6, var3, var5);
   }

   public static void validateSourceSize(long var0, long var2, int var4) throws IIOException {
      validateSourceSize(var0, var2, var4, MAX_IMAGE_BYTES);
   }

   static void validateSourceSize(long var0, long var2, int var4, long var5) throws IIOException {
      long var7;
      String var9;
      if (var2 >= 0L) {
         var7 = saturatedMultiply(var2, var4);
         var9 = var4 == 1 ? String.format("%d bytes of input", var2) : String.format("%d bytes of input at a maximum expansion ratio of %d:1", var2, var4);
      } else {
         var7 = var5;
         var9 = String.format("the maximum %d bytes for input of unknown length (override with -D%s)", var5, "com.twelvemonkeys.imageio.maxImageBytes");
      }

      if (var0 > var7) {
         throw new IIOException(String.format("Image dimensions imply an allocation of %d bytes, exceeding %s", var0, var9));
      }
   }

   private static long resolveMaxImageBytes() {
      long var0 = Long.getLong("com.twelvemonkeys.imageio.maxImageBytes", -1L);
      return var0 > 0L ? var0 : defaultMaxImageBytes();
   }

   static long defaultMaxImageBytes() {
      long var0 = Runtime.getRuntime().maxMemory();
      long var2 = 536870912L;
      long var4 = 67108864L;
      return Math.max(var4, Math.min(var2, var0 / 2L));
   }

   private static long saturatedMultiply(long var0, long var2) {
      if (var0 > 0L && var2 > 0L) {
         return var0 > Long.MAX_VALUE / var2 ? Long.MAX_VALUE : var0 * var2;
      } else {
         return 0L;
      }
   }

   protected static BufferedImage fakeAOI(BufferedImage var0, ImageReadParam var1) {
      return IIOUtil.fakeAOI(var0, getSourceRegion(var1, var0.getWidth(), var0.getHeight()));
   }

   protected static Image fakeSubsampling(Image var0, ImageReadParam var1) {
      return IIOUtil.fakeSubsampling(var0, var1);
   }

   protected static boolean hasExplicitDestination(ImageReadParam var0) {
      return var0 != null && (var0.getDestination() != null || var0.getDestinationType() != null || !ORIGIN.equals(var0.getDestinationOffset()));
   }

   public static void main(String[] var0) throws IOException {
      BufferedImage var1 = ImageIO.read(new File(var0[0]));
      if (var1 == null) {
         System.err.println("Supported formats: " + Arrays.toString((Object[])IIOUtil.getNormalizedReaderFormatNames()));
         System.exit(1);
      }

      showIt(var1, var0[0]);
   }

   protected static void showIt(final BufferedImage var0, final String var1) {
      try {
         SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
               JFrame var1x = new JFrame(var1);
               var1x.getRootPane().getActionMap().put("window-close", new AbstractAction() {
                  @Override
                  public void actionPerformed(ActionEvent var1x) {
                     Window var2 = SwingUtilities.getWindowAncestor((Component)var1x.getSource());
                     var2.setVisible(false);
                     var2.dispose();
                  }
               });
               var1x.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(87, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "window-close");
               var1x.addWindowListener(new ImageReaderBase.ExitIfNoWindowPresentHandler());
               var1x.setDefaultCloseOperation(2);
               var1x.setLocationByPlatform(true);
               JPanel var2 = new JPanel(new BorderLayout());
               JScrollPane var3x = new JScrollPane((Component)(var0 != null ? new ImageReaderBase.ImageLabel(var0) : new JLabel("(no image data)", 0)));
               var3x.setBorder(null);
               var2.add(var3x);
               var1x.setContentPane(var2);
               var1x.pack();
               var1x.setVisible(true);
            }
         });
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
      } catch (InvocationTargetException var4) {
         if (var4.getCause() instanceof RuntimeException) {
            throw (RuntimeException)var4.getCause();
         }

         throw new RuntimeException(var4);
      }
   }

   private static class ExitIfNoWindowPresentHandler extends WindowAdapter {
      private ExitIfNoWindowPresentHandler() {
      }

      @Override
      public void windowClosed(WindowEvent var1) {
         Window[] var2 = Window.getWindows();
         if (var2 == null || var2.length == 0) {
            System.exit(0);
         }
      }
   }

   private static class ImageLabel extends JLabel {
      static final String ZOOM_IN = "zoom-in";
      static final String ZOOM_OUT = "zoom-out";
      static final String ZOOM_ACTUAL = "zoom-actual";
      static final String ZOOM_FIT = "zoom-fit";
      private BufferedImage image;
      Paint backgroundPaint;
      final Paint checkeredBG;
      final Color defaultBG;

      public ImageLabel(BufferedImage var1) {
         super(new BufferedImageIcon(var1));
         this.setOpaque(false);
         this.setCursor(Cursor.getPredefinedCursor(1));
         this.image = var1;
         this.checkeredBG = createTexture();
         this.defaultBG = getDefaultBackground(var1);
         this.backgroundPaint = (Paint)(this.defaultBG != null ? this.defaultBG : this.checkeredBG);
         this.setupActions();
         this.setComponentPopupMenu(this.createPopupMenu());
         this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent var1) {
               if (var1.isPopupTrigger()) {
                  ImageLabel.this.getComponentPopupMenu().show(ImageLabel.this, var1.getX(), var1.getY());
               }
            }
         });
         this.setTransferHandler(new TransferHandler() {
            @Override
            public int getSourceActions(JComponent var1) {
               return 1;
            }

            @Override
            protected Transferable createTransferable(JComponent var1) {
               return new ImageReaderBase.ImageLabel.ImageTransferable(ImageLabel.this.image);
            }

            @Override
            public boolean importData(JComponent var1, Transferable var2) {
               if (this.canImport(var1, var2.getTransferDataFlavors())) {
                  try {
                     Image var3 = (Image)var2.getTransferData(DataFlavor.imageFlavor);
                     ImageLabel.this.image = ImageUtil.toBuffered(var3);
                     ImageLabel.this.setIcon(new BufferedImageIcon(ImageLabel.this.image));
                     return true;
                  } catch (IOException | UnsupportedFlavorException var4) {
                  }
               }

               return false;
            }

            @Override
            public boolean canImport(JComponent var1, DataFlavor[] var2) {
               for (DataFlavor var6 : var2) {
                  if (var6.equals(DataFlavor.imageFlavor)) {
                     return true;
                  }
               }

               return false;
            }
         });
      }

      private void setupActions() {
         this.bindAction(
            new ImageReaderBase.ImageLabel.ZoomAction("Zoom in", 2.0),
            "zoom-in",
            KeyStroke.getKeyStroke('+'),
            KeyStroke.getKeyStroke(521, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
            KeyStroke.getKeyStroke(107, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
         );
         this.bindAction(
            new ImageReaderBase.ImageLabel.ZoomAction("Zoom out", 0.5),
            "zoom-out",
            KeyStroke.getKeyStroke('-'),
            KeyStroke.getKeyStroke(45, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
            KeyStroke.getKeyStroke(109, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
         );
         this.bindAction(
            new ImageReaderBase.ImageLabel.ZoomAction("Zoom actual"),
            "zoom-actual",
            KeyStroke.getKeyStroke('0'),
            KeyStroke.getKeyStroke(48, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
         );
         this.bindAction(
            new ImageReaderBase.ImageLabel.ZoomToFitAction("Zoom fit"),
            "zoom-fit",
            KeyStroke.getKeyStroke('9'),
            KeyStroke.getKeyStroke(57, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
         );
         this.bindAction(
            TransferHandler.getCopyAction(),
            (String)TransferHandler.getCopyAction().getValue("Name"),
            KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
         );
         this.bindAction(
            TransferHandler.getPasteAction(),
            (String)TransferHandler.getPasteAction().getValue("Name"),
            KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
         );
      }

      private void bindAction(Action var1, String var2, KeyStroke... var3) {
         for (KeyStroke var7 : var3) {
            this.getInputMap(2).put(var7, var2);
         }

         this.getActionMap().put(var2, var1);
      }

      private JPopupMenu createPopupMenu() {
         JPopupMenu var1 = new JPopupMenu();
         var1.add(this.getActionMap().get("zoom-fit"));
         var1.add(this.getActionMap().get("zoom-actual"));
         var1.add(this.getActionMap().get("zoom-in"));
         var1.add(this.getActionMap().get("zoom-out"));
         var1.addSeparator();
         ButtonGroup var2 = new ButtonGroup();
         JMenu var3 = new JMenu("Background");
         var1.add(var3);
         ImageReaderBase.ImageLabel.ChangeBackgroundAction var4 = new ImageReaderBase.ImageLabel.ChangeBackgroundAction("Checkered", this.checkeredBG);
         var4.putValue("SwingSelectedKey", this.backgroundPaint == this.checkeredBG);
         this.addCheckBoxItem(var4, var3, var2);
         var3.addSeparator();
         this.addCheckBoxItem(new ImageReaderBase.ImageLabel.ChangeBackgroundAction("White", Color.WHITE), var3, var2);
         this.addCheckBoxItem(new ImageReaderBase.ImageLabel.ChangeBackgroundAction("Light", Color.LIGHT_GRAY), var3, var2);
         this.addCheckBoxItem(new ImageReaderBase.ImageLabel.ChangeBackgroundAction("Gray", Color.GRAY), var3, var2);
         this.addCheckBoxItem(new ImageReaderBase.ImageLabel.ChangeBackgroundAction("Dark", Color.DARK_GRAY), var3, var2);
         this.addCheckBoxItem(new ImageReaderBase.ImageLabel.ChangeBackgroundAction("Black", Color.BLACK), var3, var2);
         var3.addSeparator();
         ImageReaderBase.ImageLabel.ChooseBackgroundAction var5 = new ImageReaderBase.ImageLabel.ChooseBackgroundAction(
            "Choose...", this.defaultBG != null ? this.defaultBG : new Color(16737792)
         );
         var5.putValue("SwingSelectedKey", this.backgroundPaint == this.defaultBG);
         this.addCheckBoxItem(var5, var3, var2);
         return var1;
      }

      private void addCheckBoxItem(Action var1, JMenu var2, ButtonGroup var3) {
         JCheckBoxMenuItem var4 = new JCheckBoxMenuItem(var1);
         var3.add(var4);
         var2.add((JMenuItem)var4);
      }

      private static Color getDefaultBackground(BufferedImage var0) {
         if (var0.getColorModel() instanceof IndexColorModel) {
            IndexColorModel var1 = (IndexColorModel)var0.getColorModel();
            int var2 = var1.getTransparentPixel();
            if (var2 >= 0) {
               return new Color(var1.getRGB(var2), false);
            }
         }

         return null;
      }

      private static Paint createTexture() {
         GraphicsConfiguration var0 = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
         BufferedImage var1 = var0.createCompatibleImage(20, 20);
         Graphics2D var2 = var1.createGraphics();

         try {
            var2.setColor(Color.LIGHT_GRAY);
            var2.fillRect(0, 0, var1.getWidth(), var1.getHeight());
            var2.setColor(Color.GRAY);
            var2.fillRect(0, 0, var1.getWidth() / 2, var1.getHeight() / 2);
            var2.fillRect(var1.getWidth() / 2, var1.getHeight() / 2, var1.getWidth() / 2, var1.getHeight() / 2);
         } finally {
            var2.dispose();
         }

         return new TexturePaint(var1, new Rectangle(var1.getWidth(), var1.getHeight()));
      }

      @Override
      protected void paintComponent(Graphics var1) {
         Graphics2D var2 = (Graphics2D)var1;
         var2.setPaint(this.backgroundPaint);
         var2.fillRect(0, 0, this.getWidth(), this.getHeight());
         super.paintComponent(var1);
      }

      private class ChangeBackgroundAction extends AbstractAction {
         protected Paint paint;

         public ChangeBackgroundAction(String var2, Paint var3) {
            super(var2);
            this.paint = var3;
         }

         @Override
         public void actionPerformed(ActionEvent var1) {
            ImageLabel.this.backgroundPaint = this.paint;
            ImageLabel.this.repaint();
         }
      }

      private class ChooseBackgroundAction extends ImageReaderBase.ImageLabel.ChangeBackgroundAction {
         public ChooseBackgroundAction(String var2, Color var3) {
            super(var2, var3);
            this.putValue("SmallIcon", new Icon() {
               @Override
               public void paintIcon(Component var1, Graphics var2x, int var3x, int var4) {
                  Graphics var5 = var2x.create();
                  var5.setColor((Color)ChooseBackgroundAction.this.paint);
                  var5.fillRoundRect(var3x, var4, 16, 16, 3, 3);
                  var5.dispose();
               }

               @Override
               public int getIconWidth() {
                  return 16;
               }

               @Override
               public int getIconHeight() {
                  return 16;
               }
            });
         }

         @Override
         public void actionPerformed(ActionEvent var1) {
            JColorChooser var2 = new JColorChooser((Color)this.paint);
            var2.setOpaque(false);

            for (AbstractColorChooserPanel var6 : var2.getChooserPanels()) {
               if (var6.getClass().getSimpleName().contains("Swatch")) {
                  var2.removeChooserPanel(var6);
                  var2.addChooserPanel(var6);
               }
            }

            ImageReaderBase.ImageLabel.ColorTracker var7 = new ImageReaderBase.ImageLabel.ColorTracker(var2);
            JDialog var8 = JColorChooser.createDialog(ImageLabel.this, "Choose background", true, var2, var7, null);
            var8.setDefaultCloseOperation(2);
            var8.setVisible(true);
            Color var9 = var7.getColor();
            if (var9 != null) {
               this.paint = var9;
               var2.setColor(var9);
               super.actionPerformed(var1);
            }
         }
      }

      static class ColorTracker implements ActionListener, Serializable {
         JColorChooser chooser;
         Color color;

         public ColorTracker(JColorChooser var1) {
            this.chooser = var1;
         }

         @Override
         public void actionPerformed(ActionEvent var1) {
            this.color = this.chooser.getColor();
         }

         public Color getColor() {
            return this.color;
         }
      }

      private static class ImageTransferable implements Transferable {
         private final BufferedImage image;

         public ImageTransferable(BufferedImage var1) {
            this.image = var1;
         }

         @Override
         public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
         }

         @Override
         public boolean isDataFlavorSupported(DataFlavor var1) {
            return DataFlavor.imageFlavor.equals(var1);
         }

         @Override
         public Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException {
            if (this.isDataFlavorSupported(var1)) {
               return this.image;
            } else {
               throw new UnsupportedFlavorException(var1);
            }
         }
      }

      private class ZoomAction extends AbstractAction {
         private final double zoomFactor;

         public ZoomAction(String var2, double var3) {
            super(var2);
            this.zoomFactor = var3;
         }

         public ZoomAction(String var2) {
            this(var2, 0.0);
         }

         @Override
         public void actionPerformed(ActionEvent var1) {
            if (this.zoomFactor <= 0.0) {
               ImageLabel.this.setIcon(new BufferedImageIcon(ImageLabel.this.image));
            } else {
               Icon var2 = ImageLabel.this.getIcon();
               int var3 = Math.max(
                  Math.min((int)(var2.getIconWidth() * this.zoomFactor), ImageLabel.this.image.getWidth() * 16), ImageLabel.this.image.getWidth() / 16
               );
               int var4 = Math.max(
                  Math.min((int)(var2.getIconHeight() * this.zoomFactor), ImageLabel.this.image.getHeight() * 16), ImageLabel.this.image.getHeight() / 16
               );
               ImageLabel.this.setIcon(
                  new BufferedImageIcon(
                     ImageLabel.this.image,
                     Math.max(var3, 2),
                     Math.max(var4, 2),
                     var3 > ImageLabel.this.image.getWidth() || var4 > ImageLabel.this.image.getHeight()
                  )
               );
            }
         }
      }

      private class ZoomToFitAction extends ImageReaderBase.ImageLabel.ZoomAction {
         public ZoomToFitAction(String var2) {
            super(var2, -1.0);
         }

         @Override
         public void actionPerformed(ActionEvent var1) {
            JComponent var2 = (JComponent)var1.getSource();
            if (var2 instanceof JMenuItem) {
               JPopupMenu var3 = (JPopupMenu)SwingUtilities.getAncestorOfClass(JPopupMenu.class, var2);
               var2 = (JComponent)var3.getInvoker();
            }

            Container var12 = SwingUtilities.getAncestorOfClass(JViewport.class, var2);
            double var4 = (double)var12.getWidth() / ImageLabel.this.image.getWidth();
            double var6 = (double)var12.getHeight() / ImageLabel.this.image.getHeight();
            double var8 = Math.min(var4, var6);
            int var10 = Math.max(
               Math.min((int)(ImageLabel.this.image.getWidth() * var8), ImageLabel.this.image.getWidth() * 16), ImageLabel.this.image.getWidth() / 16
            );
            int var11 = Math.max(
               Math.min((int)(ImageLabel.this.image.getHeight() * var8), ImageLabel.this.image.getHeight() * 16), ImageLabel.this.image.getHeight() / 16
            );
            ImageLabel.this.setIcon(new BufferedImageIcon(ImageLabel.this.image, var10, var11, var8 > 1.0));
         }
      }
   }
}
