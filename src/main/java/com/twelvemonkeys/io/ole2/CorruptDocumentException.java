package com.twelvemonkeys.io.ole2;

import java.io.IOException;

public class CorruptDocumentException extends IOException {
   public CorruptDocumentException() {
      this("Corrupt OLE 2 Compound Document");
   }

   public CorruptDocumentException(String var1) {
      super(var1);
   }

   public CorruptDocumentException(Throwable var1) {
      super(var1.getMessage());
      this.initCause(var1);
   }
}
