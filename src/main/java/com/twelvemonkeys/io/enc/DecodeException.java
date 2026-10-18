package com.twelvemonkeys.io.enc;

import java.io.IOException;

public class DecodeException extends IOException {
   public DecodeException(String var1) {
      super(var1);
   }

   public DecodeException(String var1, Throwable var2) {
      super(var1);
      this.initCause(var2);
   }

   public DecodeException(Throwable var1) {
      this(var1.getMessage(), var1);
   }
}
