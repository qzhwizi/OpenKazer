package com.twelvemonkeys.imageio.metadata;

import java.io.IOException;
import javax.imageio.stream.ImageOutputStream;

public abstract class MetadataWriter {
   public abstract boolean write(Directory var1, ImageOutputStream var2) throws IOException;
}
