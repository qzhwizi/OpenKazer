package com.twelvemonkeys.io;

import java.io.IOException;

public interface Seekable {
   long getStreamPosition() throws IOException;

   void seek(long var1) throws IOException;

   void mark();

   void reset() throws IOException;

   void flushBefore(long var1) throws IOException;

   void flush() throws IOException;

   long getFlushedPosition() throws IOException;

   boolean isCached();

   boolean isCachedMemory();

   boolean isCachedFile();

   void close() throws IOException;
}
