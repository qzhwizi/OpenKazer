package com.twelvemonkeys.io.ole2;

import com.twelvemonkeys.io.FileUtil;
import com.twelvemonkeys.io.LittleEndianDataInputStream;
import com.twelvemonkeys.io.LittleEndianRandomAccessFile;
import com.twelvemonkeys.io.MemoryCacheSeekableStream;
import com.twelvemonkeys.io.Seekable;
import com.twelvemonkeys.io.SeekableInputStream;
import com.twelvemonkeys.lang.StringUtil;
import com.twelvemonkeys.lang.Validate;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import javax.imageio.stream.ImageInputStream;

public final class CompoundDocument implements AutoCloseable {
   static final byte[] MAGIC = new byte[]{-48, -49, 17, -32, -95, -79, 26, -31};
   private static final int FREE_SID = -1;
   private static final int END_OF_CHAIN_SID = -2;
   private static final int SAT_SECTOR_SID = -3;
   private static final int MSAT_SECTOR_SID = -4;
   public static final int HEADER_SIZE = 512;
   public static final long EPOCH_OFFSET = -11644477200000L;
   private final DataInput input;
   private UUID uUID;
   private int sectorSize;
   private int shortSectorSize;
   private int directorySId;
   private int minStreamSize;
   private int shortSATSId;
   private int shortSATSize;
   private int[] masterSAT;
   private int[] SAT;
   private int[] shortSAT;
   private Entry rootEntry;
   private SIdChain shortStreamSIdChain;
   private SIdChain directorySIdChain;

   public CompoundDocument(File var1) throws IOException {
      this.input = new LittleEndianRandomAccessFile(FileUtil.resolve(var1), "r");
      this.readHeader();
   }

   public CompoundDocument(InputStream var1) throws IOException {
      this((SeekableInputStream)(new MemoryCacheSeekableStream(var1)));
   }

   CompoundDocument(SeekableInputStream var1) throws IOException {
      this.input = new CompoundDocument.SeekableLittleEndianDataInputStream(var1);
      this.readHeader();
   }

   public CompoundDocument(ImageInputStream var1) throws IOException {
      this.input = Validate.notNull(var1, "input");
      var1.setByteOrder(ByteOrder.LITTLE_ENDIAN);
      this.readHeader();
   }

   @Override
   public void close() throws IOException {
      if (this.input instanceof RandomAccessFile) {
         ((RandomAccessFile)this.input).close();
      } else if (this.input instanceof LittleEndianRandomAccessFile) {
         ((LittleEndianRandomAccessFile)this.input).close();
      }
   }

   public static boolean canRead(DataInput var0) {
      return canRead(var0, true);
   }

   private static boolean canRead(DataInput var0, boolean var1) {
      long var2 = -1L;
      if (var1) {
         try {
            if (var0 instanceof InputStream && ((InputStream)var0).markSupported()) {
               ((InputStream)var0).mark(8);
            } else if (var0 instanceof ImageInputStream) {
               ((ImageInputStream)var0).mark();
            } else if (var0 instanceof RandomAccessFile) {
               var2 = ((RandomAccessFile)var0).getFilePointer();
            } else {
               if (!(var0 instanceof LittleEndianRandomAccessFile)) {
                  return false;
               }

               var2 = ((LittleEndianRandomAccessFile)var0).getFilePointer();
            }
         } catch (IOException var18) {
            return false;
         }
      }

      try {
         byte[] var4 = new byte[8];
         var0.readFully(var4);
         return Arrays.equals(var4, MAGIC);
      } catch (IOException var16) {
      } finally {
         if (var1) {
            try {
               if (var0 instanceof InputStream && ((InputStream)var0).markSupported()) {
                  ((InputStream)var0).reset();
               } else if (var0 instanceof ImageInputStream) {
                  ((ImageInputStream)var0).reset();
               } else if (var0 instanceof RandomAccessFile) {
                  ((RandomAccessFile)var0).seek(var2);
               } else if (var0 instanceof LittleEndianRandomAccessFile) {
                  ((LittleEndianRandomAccessFile)var0).seek(var2);
               }
            } catch (IOException var15) {
               var15.printStackTrace();
            }
         }
      }

      return false;
   }

   private void readHeader() throws IOException {
      if (this.masterSAT == null) {
         if (!canRead(this.input, false)) {
            throw new CorruptDocumentException("Not an OLE 2 Compound Document");
         } else {
            this.uUID = new UUID(this.input.readLong(), this.input.readLong());
            this.input.readUnsignedShort();
            this.input.readUnsignedShort();
            int var1 = this.input.readUnsignedShort();
            if (var1 == 65535) {
               throw new CorruptDocumentException("Cannot read big endian OLE 2 Compound Documents");
            } else if (var1 != 65534) {
               throw new CorruptDocumentException(String.format("Unknown byte order marker: 0x%04x, expected 0xfffe or 0xffff", var1));
            } else {
               this.sectorSize = 1 << this.input.readUnsignedShort();
               this.shortSectorSize = 1 << this.input.readUnsignedShort();
               if (this.skipBytesFully(10) != 10) {
                  throw new CorruptDocumentException();
               } else {
                  int var2 = this.input.readInt();
                  this.directorySId = this.input.readInt();
                  if (this.skipBytesFully(4) != 4) {
                     throw new CorruptDocumentException();
                  } else {
                     this.minStreamSize = this.input.readInt();
                     this.shortSATSId = this.input.readInt();
                     this.shortSATSize = this.input.readInt();
                     int var3 = this.input.readInt();
                     int var4 = this.input.readInt();
                     this.masterSAT = new int[var2];
                     int var5 = Math.min(var2, 109);

                     for (int var6 = 0; var6 < var5; var6++) {
                        this.masterSAT[var6] = this.input.readInt();
                     }

                     if (var3 == -2) {
                        int var10 = 436 - var2 * 4;
                        if (this.skipBytesFully(var10) != var10) {
                           throw new CorruptDocumentException();
                        }
                     } else {
                        this.seekToSId(var3, -1L);
                        int var11 = var5;

                        for (int var7 = 0; var7 < var4; var7++) {
                           for (int var8 = 0; var8 < 127; var8++) {
                              int var9 = this.input.readInt();
                              switch (var9) {
                                 default:
                                    this.masterSAT[var11++] = var9;
                                    break;
                                 case -1:
                              }
                           }

                           int var12 = this.input.readInt();
                           if (var12 == -2) {
                              break;
                           }

                           this.seekToSId(var12, -1L);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private int skipBytesFully(int var1) throws IOException {
      int var2 = var1;

      while (var2 > 0) {
         int var3 = this.input.skipBytes(var1);
         if (var3 <= 0) {
            break;
         }

         var2 -= var3;
      }

      return var1 - var2;
   }

   private void readSAT() throws IOException {
      if (this.SAT == null) {
         int var1 = this.sectorSize / 4;
         this.SAT = new int[this.masterSAT.length * var1];

         for (int var2 = 0; var2 < this.masterSAT.length; var2++) {
            this.seekToSId(this.masterSAT[var2], -1L);

            for (int var3 = 0; var3 < var1; var3++) {
               int var4 = this.input.readInt();
               int var5 = var3 + var2 * var1;
               this.SAT[var5] = var4;
            }
         }

         SIdChain var7 = this.getSIdChain(this.shortSATSId, -1L);
         this.shortSAT = new int[this.shortSATSize * var1];

         for (int var8 = 0; var8 < this.shortSATSize; var8++) {
            this.seekToSId(var7.get(var8), -1L);

            for (int var9 = 0; var9 < var1; var9++) {
               int var10 = this.input.readInt();
               int var6 = var9 + var8 * var1;
               this.shortSAT[var6] = var10;
            }
         }
      }
   }

   private SIdChain getSIdChain(int var1, long var2) throws IOException {
      SIdChain var4 = new SIdChain();
      int[] var5 = this.isShortStream(var2) ? this.shortSAT : this.SAT;

      for (int var6 = var1; var6 != -2 && var6 != -1; var6 = var5[var6]) {
         var4.addSID(var6);
      }

      return var4;
   }

   private boolean isShortStream(long var1) {
      return var1 != -1L && var1 < this.minStreamSize;
   }

   private void seekToSId(int var1, long var2) throws IOException {
      long var4;
      if (this.isShortStream(var2)) {
         Entry var6 = this.getRootEntry();
         if (this.shortStreamSIdChain == null) {
            this.shortStreamSIdChain = this.getSIdChain(var6.startSId, var6.streamSize);
         }

         int var7 = this.sectorSize / this.shortSectorSize;
         int var8 = var1 / var7;
         int var9 = var1 - var8 * var7;
         var4 = 512L + (long)this.shortStreamSIdChain.get(var8) * this.sectorSize + (long)var9 * this.shortSectorSize;
      } else {
         var4 = 512L + (long)var1 * this.sectorSize;
      }

      if (this.input instanceof LittleEndianRandomAccessFile) {
         ((LittleEndianRandomAccessFile)this.input).seek(var4);
      } else if (this.input instanceof ImageInputStream) {
         ((ImageInputStream)this.input).seek(var4);
      } else {
         ((CompoundDocument.SeekableLittleEndianDataInputStream)this.input).seek(var4);
      }
   }

   private void seekToDId(int var1) throws IOException {
      if (this.directorySIdChain == null) {
         this.directorySIdChain = this.getSIdChain(this.directorySId, -1L);
      }

      int var2 = this.sectorSize / 128;
      int var3 = var1 / var2;
      int var4 = var1 - var3 * var2;
      int var5 = this.directorySIdChain.get(var3);
      this.seekToSId(var5, -1L);
      if (this.input instanceof LittleEndianRandomAccessFile) {
         LittleEndianRandomAccessFile var6 = (LittleEndianRandomAccessFile)this.input;
         var6.seek(var6.getFilePointer() + var4 * 128);
      } else if (this.input instanceof ImageInputStream) {
         ImageInputStream var7 = (ImageInputStream)this.input;
         var7.seek(var7.getStreamPosition() + var4 * 128);
      } else {
         CompoundDocument.SeekableLittleEndianDataInputStream var8 = (CompoundDocument.SeekableLittleEndianDataInputStream)this.input;
         var8.seek(var8.getStreamPosition() + var4 * 128);
      }
   }

   SeekableInputStream getInputStreamForSId(int var1, int var2) throws IOException {
      SIdChain var3 = this.getSIdChain(var1, var2);
      int var4 = var2 < this.minStreamSize ? this.shortSectorSize : this.sectorSize;
      return new MemoryCacheSeekableStream(new CompoundDocument.Stream(var3, var2, var4, this));
   }

   private InputStream getDirectoryStreamForDId(int var1) throws IOException {
      byte[] var2 = new byte[128];
      this.seekToDId(var1);
      this.input.readFully(var2);
      return new ByteArrayInputStream(var2);
   }

   Entry getEntry(int var1, Entry var2) throws IOException {
      Entry var3 = Entry.readEntry(new LittleEndianDataInputStream(this.getDirectoryStreamForDId(var1)));
      var3.parent = var2;
      var3.document = this;
      return var3;
   }

   SortedSet<Entry> getEntries(int var1, Entry var2) throws IOException {
      return this.getEntriesRecursive(var1, var2, new TreeSet<>());
   }

   private SortedSet<Entry> getEntriesRecursive(int var1, Entry var2, SortedSet<Entry> var3) throws IOException {
      Entry var4 = this.getEntry(var1, var2);
      if (!var3.add(var4)) {
         throw new CorruptDocumentException("Cyclic chain reference for entry: " + var1);
      } else {
         if (var4.prevDId != -1) {
            this.getEntriesRecursive(var4.prevDId, var2, var3);
         }

         if (var4.nextDId != -1) {
            this.getEntriesRecursive(var4.nextDId, var2, var3);
         }

         return var3;
      }
   }

   Entry getEntry(String var1) throws IOException {
      if (!StringUtil.isEmpty(var1) && var1.startsWith("/")) {
         Entry var2 = this.getRootEntry();
         if (var1.equals("/")) {
            return var2;
         } else {
            String[] var3 = StringUtil.toStringArray(var1, "/");

            for (String var7 : var3) {
               var2 = var2.getChildEntry(var7);
               if (var2 == null) {
                  break;
               }
            }

            return var2;
         }
      } else {
         throw new IllegalArgumentException("Path must be absolute, and contain a valid path: " + var1);
      }
   }

   public Entry getRootEntry() throws IOException {
      if (this.rootEntry == null) {
         this.readSAT();
         this.rootEntry = this.getEntry(0, null);
         if (this.rootEntry.type != 5) {
            throw new CorruptDocumentException("Invalid root storage type: " + this.rootEntry.type);
         }
      }

      return this.rootEntry;
   }

   @Override
   public String toString() {
      return String.format(
         "%s[uuid: %s, sector size: %d/%d bytes, directory SID: %d, master SAT: %s entries]",
         this.getClass().getSimpleName(),
         this.uUID,
         this.sectorSize,
         this.shortSectorSize,
         this.directorySId,
         this.masterSAT.length
      );
   }

   public static long toJavaTimeInMillis(long var0) {
      return var0 == 0L ? 0L : (var0 >> 1) / 5000L + -11644477200000L;
   }

   static class SeekableLittleEndianDataInputStream extends LittleEndianDataInputStream implements Seekable {
      private final SeekableInputStream seekable;

      public SeekableLittleEndianDataInputStream(SeekableInputStream var1) {
         super(var1);
         this.seekable = var1;
      }

      @Override
      public void seek(long var1) throws IOException {
         this.seekable.seek(var1);
      }

      @Override
      public boolean isCachedFile() {
         return this.seekable.isCachedFile();
      }

      @Override
      public boolean isCachedMemory() {
         return this.seekable.isCachedMemory();
      }

      @Override
      public boolean isCached() {
         return this.seekable.isCached();
      }

      @Override
      public long getStreamPosition() throws IOException {
         return this.seekable.getStreamPosition();
      }

      @Override
      public long getFlushedPosition() throws IOException {
         return this.seekable.getFlushedPosition();
      }

      @Override
      public void flushBefore(long var1) throws IOException {
         this.seekable.flushBefore(var1);
      }

      @Override
      public void flush() throws IOException {
         this.seekable.flush();
      }

      @Override
      public void reset() throws IOException {
         this.seekable.reset();
      }

      @Override
      public void mark() {
         this.seekable.mark();
      }
   }

   static class Stream extends InputStream {
      private final SIdChain chain;
      private final CompoundDocument document;
      private final long length;
      private long streamPos;
      private int nextSectorPos;
      private byte[] buffer;
      private int bufferPos;

      public Stream(SIdChain var1, int var2, int var3, CompoundDocument var4) {
         this.chain = var1;
         this.length = var2;
         this.buffer = new byte[var3];
         this.bufferPos = this.buffer.length;
         this.document = var4;
      }

      @Override
      public int available() throws IOException {
         return (int)Math.min((long)(this.buffer.length - this.bufferPos), this.length - this.streamPos);
      }

      @Override
      public int read() throws IOException {
         if (this.available() <= 0 && !this.fillBuffer()) {
            return -1;
         } else {
            this.streamPos++;
            return this.buffer[this.bufferPos++] & 0xFF;
         }
      }

      private boolean fillBuffer() throws IOException {
         if (this.streamPos < this.length && this.nextSectorPos < this.chain.length()) {
            synchronized (this.document) {
               this.document.seekToSId(this.chain.get(this.nextSectorPos), this.length);
               this.document.input.readFully(this.buffer);
            }

            this.nextSectorPos++;
            this.bufferPos = 0;
            return true;
         } else {
            return false;
         }
      }

      @Override
      public int read(byte[] var1, int var2, int var3) throws IOException {
         if (this.available() <= 0 && !this.fillBuffer()) {
            return -1;
         } else {
            int var4 = Math.min(var3, this.available());
            System.arraycopy(this.buffer, this.bufferPos, var1, var2, var4);
            this.bufferPos += var4;
            this.streamPos += var4;
            return var4;
         }
      }

      @Override
      public void close() throws IOException {
         this.buffer = null;
      }
   }
}
