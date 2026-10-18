package com.twelvemonkeys.imageio.metadata.exif;

import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.Entry;
import com.twelvemonkeys.imageio.metadata.MetadataWriter;
import com.twelvemonkeys.imageio.metadata.tiff.TIFFWriter;
import java.io.IOException;
import java.util.Collection;
import javax.imageio.stream.ImageOutputStream;

@Deprecated
public final class EXIFWriter extends MetadataWriter {
   private final TIFFWriter delegate = new TIFFWriter();

   @Override
   public boolean write(Directory var1, ImageOutputStream var2) throws IOException {
      return this.delegate.write(var1, var2);
   }

   public boolean write(Collection<Entry> var1, ImageOutputStream var2) throws IOException {
      return this.delegate.write(var1, var2);
   }
}
