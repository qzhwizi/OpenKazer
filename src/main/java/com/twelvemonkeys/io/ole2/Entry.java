package com.twelvemonkeys.io.ole2;

import com.twelvemonkeys.io.SeekableInputStream;
import java.io.DataInput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Entry implements Comparable<Entry> {
   String name;
   byte type;
   byte nodeColor;
   int prevDId;
   int nextDId;
   int rootNodeDId;
   long createdTimestamp;
   long modifiedTimestamp;
   int startSId;
   int streamSize;
   CompoundDocument document;
   Entry parent;
   SortedSet<Entry> children;
   public static final int LENGTH = 128;
   static final int EMPTY = 0;
   static final int USER_STORAGE = 1;
   static final int USER_STREAM = 2;
   static final int LOCK_BYTES = 3;
   static final int PROPERTY = 4;
   static final int ROOT_STORAGE = 5;
   private static final SortedSet<Entry> NO_CHILDREN = Collections.unmodifiableSortedSet(new TreeSet<>());

   private Entry() {
   }

   static Entry readEntry(DataInput var0) throws IOException {
      Entry var1 = new Entry();
      var1.read(var0);
      return var1;
   }

   private void read(DataInput var1) throws IOException {
      byte[] var2 = new byte[64];
      var1.readFully(var2);
      short var3 = var1.readShort();
      this.name = new String(var2, 0, var3 - 2, Charset.forName("UTF-16LE"));
      this.type = var1.readByte();
      this.nodeColor = var1.readByte();
      this.prevDId = var1.readInt();
      this.nextDId = var1.readInt();
      this.rootNodeDId = var1.readInt();
      if (var1.skipBytes(20) != 20) {
         throw new CorruptDocumentException();
      } else {
         this.createdTimestamp = CompoundDocument.toJavaTimeInMillis(var1.readLong());
         this.modifiedTimestamp = CompoundDocument.toJavaTimeInMillis(var1.readLong());
         this.startSId = var1.readInt();
         this.streamSize = var1.readInt();
         var1.readInt();
      }
   }

   public boolean isRoot() {
      return this.type == 5;
   }

   public boolean isDirectory() {
      return this.type == 1;
   }

   public boolean isFile() {
      return this.type == 2;
   }

   public String getName() {
      return this.name;
   }

   public SeekableInputStream getInputStream() throws IOException {
      return !this.isFile() ? null : this.document.getInputStreamForSId(this.startSId, this.streamSize);
   }

   public long length() {
      return !this.isFile() ? 0L : this.streamSize;
   }

   public long created() {
      return this.createdTimestamp;
   }

   public long lastModified() {
      return this.modifiedTimestamp;
   }

   public Entry getParentEntry() {
      return this.parent;
   }

   public Entry getChildEntry(String var1) throws IOException {
      if (!this.isFile() && this.rootNodeDId != -1) {
         Entry var2 = new Entry();
         var2.name = var1;
         var2.parent = this;
         SortedSet var3 = this.getChildEntries().tailSet(var2);
         return (Entry)var3.first();
      } else {
         return null;
      }
   }

   public SortedSet<Entry> getChildEntries() throws IOException {
      if (this.children == null) {
         if (!this.isFile() && this.rootNodeDId != -1) {
            this.children = Collections.unmodifiableSortedSet(this.document.getEntries(this.rootNodeDId, this));
         } else {
            this.children = NO_CHILDREN;
         }
      }

      return this.children;
   }

   @Override
   public String toString() {
      return "\""
         + this.name
         + "\" ("
         + (this.isFile() ? "Document" : (this.isDirectory() ? "Directory" : "Root"))
         + (this.parent != null ? ", parent: \"" + this.parent.getName() + "\"" : "")
         + (this.isFile() ? "" : ", children: " + (this.children != null ? String.valueOf(this.children.size()) : "(unknown)"))
         + ", SId="
         + this.startSId
         + ", length="
         + this.streamSize
         + ")";
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Entry)) {
         return false;
      } else {
         Entry var2 = (Entry)var1;
         return this.name.equals(var2.name) && (this.parent == var2.parent || this.parent != null && this.parent.equals(var2.parent));
      }
   }

   @Override
   public int hashCode() {
      return this.name.hashCode() ^ this.startSId;
   }

   public int compareTo(Entry var1) {
      if (this == var1) {
         return 0;
      } else {
         int var2 = this.name.length() - var1.name.length();
         return var2 != 0 ? var2 : this.name.compareTo(var1.name);
      }
   }
}
