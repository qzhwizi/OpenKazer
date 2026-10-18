package com.twelvemonkeys.util.service;

public interface RegisterableService {
   void onRegistration(ServiceRegistry var1, Class var2);

   void onDeregistration(ServiceRegistry var1, Class var2);
}
