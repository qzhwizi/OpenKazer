package com.twelvemonkeys.util.service;

public class ServiceConfigurationError extends Error {
   ServiceConfigurationError(Throwable var1) {
      super(var1);
   }

   ServiceConfigurationError(String var1) {
      super(var1);
   }

   ServiceConfigurationError(String var1, Throwable var2) {
      super(var1, var2);
   }
}
