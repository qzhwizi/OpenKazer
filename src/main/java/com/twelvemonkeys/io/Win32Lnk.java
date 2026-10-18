package com.twelvemonkeys.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

final class Win32Lnk extends File {
   private static final byte[] LNK_MAGIC = new byte[]{76, 0, 0, 0};
   private static final byte[] LNK_GUID = new byte[]{1, 20, 2, 0, 0, 0, 0, 0, -64, 0, 0, 0, 0, 0, 0, 70};
   private final File target;
   private static final int FLAG_ITEM_ID_LIST = 1;
   private static final int FLAG_FILE_LOC_INFO = 2;
   private static final int FLAG_DESC_STRING = 4;
   private static final int FLAG_REL_PATH_STRING = 8;
   private static final int FLAG_WORKING_DIRECTORY = 16;
   private static final int FLAG_COMMAND_LINE_ARGS = 32;
   private static final int FLAG_ICON_FILENAME = 64;
   private static final int FLAG_ADDITIONAL_INFO = 128;

   private Win32Lnk(String var1) throws IOException {
      super(var1);
      File var2 = parse(this);
      if (var2 == this) {
         var2 = new File(var1);
      }

      this.target = var2;
   }

   Win32Lnk(File var1) throws IOException {
      this(var1.getPath());
   }

   static File parse(File var0) throws IOException {
      if (!var0.getName().endsWith(".lnk")) {
         return var0;
      } else {
         File var1 = var0;
         LittleEndianDataInputStream var2 = new LittleEndianDataInputStream(new BufferedInputStream(new FileInputStream(var0)));

         try {
            byte[] var3 = new byte[4];
            var2.readFully(var3);
            byte[] var4 = new byte[16];
            var2.readFully(var4);
            if (!Arrays.equals(LNK_MAGIC, var3) || !Arrays.equals(LNK_GUID, var4)) {
               return var0;
            }

            int var5 = var2.readInt();
            var2.readInt();
            var2.skipBytes(48);
            if ((var5 & 1) != 0) {
               short var6 = var2.readShort();
               var2.skipBytes(var6);
            }

            if ((var5 & 2) != 0) {
               int var20 = var2.readInt();
               var2.readInt();
               int var7 = var2.readInt();
               if ((var7 & 1) != 0) {
               }

               if ((var7 & 2) != 0) {
               }

               var2.skipBytes(4);
               int var8 = var2.readInt();
               var2.skipBytes(var8 - 20);
               byte[] var9 = new byte[var20 - var8 - 1];
               var2.readFully(var9, 0, var9.length);
               String var10 = new String(var9, 0, var9.length - 1);

               try {
                  var1 = parse(new File(var10));
               } catch (StackOverflowError var17) {
                  throw new IOException("Cannot resolve cyclic link: " + var17.getMessage());
               }
            }

            if ((var5 & 4) != 0) {
               short var21 = var2.readShort();
               byte[] var23 = new byte[var21];
               var2.readFully(var23, 0, var21);
            }

            if ((var5 & 8) != 0) {
               short var22 = var2.readShort();
               byte[] var24 = new byte[var22];
               var2.readFully(var24, 0, var22);
               String var25 = new String(var24, 0, var22);
               if (var1 == var0) {
                  try {
                     var1 = parse(new File(var0.getParentFile(), var25));
                  } catch (StackOverflowError var16) {
                     throw new IOException("Cannot resolve cyclic link: " + var16.getMessage());
                  }
               }
            }

            if ((var5 & 16) != 0) {
            }

            if ((var5 & 32) != 0) {
               var1 = var0;
            }

            if ((var5 & 64) != 0) {
            }

            if ((var5 & 128) != 0) {
            }
         } finally {
            var2.close();
         }

         return var1;
      }
   }

   public File getTarget() {
      return this.target;
   }

   @Override
   public boolean isDirectory() {
      return this.target.isDirectory();
   }

   @Override
   public boolean canRead() {
      return this.target.canRead();
   }

   @Override
   public boolean canWrite() {
      return this.target.canWrite();
   }

   @Override
   public boolean exists() {
      return this.target.exists();
   }

   @Override
   public File getCanonicalFile() throws IOException {
      return this.target.getCanonicalFile();
   }

   @Override
   public String getCanonicalPath() throws IOException {
      return this.target.getCanonicalPath();
   }

   @Override
   public boolean isFile() {
      return this.target.isFile();
   }

   @Override
   public boolean isHidden() {
      return this.target.isHidden();
   }

   @Override
   public long lastModified() {
      return this.target.lastModified();
   }

   @Override
   public long length() {
      return this.target.length();
   }

   @Override
   public String[] list() {
      return this.target.list();
   }

   @Override
   public String[] list(FilenameFilter var1) {
      return this.target.list(var1);
   }

   @Override
   public File[] listFiles() {
      return Win32File.wrap(this.target.listFiles());
   }

   @Override
   public File[] listFiles(FileFilter var1) {
      return Win32File.wrap(this.target.listFiles(var1));
   }

   @Override
   public File[] listFiles(FilenameFilter var1) {
      return Win32File.wrap(this.target.listFiles(var1));
   }

   @Override
   public boolean setLastModified(long var1) {
      return this.target.setLastModified(var1);
   }

   @Override
   public boolean setReadOnly() {
      return this.target.setReadOnly();
   }

   @Override
   public String toString() {
      return this.target.equals(this) ? super.toString() : super.toString() + " -> " + this.target.toString();
   }
}
