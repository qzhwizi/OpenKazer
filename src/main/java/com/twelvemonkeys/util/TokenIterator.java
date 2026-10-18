package com.twelvemonkeys.util;

import java.util.Enumeration;
import java.util.Iterator;

public interface TokenIterator extends Enumeration<String>, Iterator<String> {
   boolean hasMoreTokens();

   String nextToken();

   void reset();
}
