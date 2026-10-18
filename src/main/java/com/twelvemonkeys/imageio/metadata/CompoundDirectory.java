package com.twelvemonkeys.imageio.metadata;

public interface CompoundDirectory extends Directory {
   Directory getDirectory(int var1);

   int directoryCount();
}
