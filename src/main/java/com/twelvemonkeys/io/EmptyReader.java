package com.twelvemonkeys.io;

import java.io.StringReader;

final class EmptyReader extends StringReader {
   public EmptyReader() {
      super("");
   }
}
