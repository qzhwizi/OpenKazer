package com.twelvemonkeys.util;

import java.util.Map;
import java.util.Map.Entry;

public interface ExpiringMap<K, V> extends Map<K, V> {
   void processRemoved(Entry<K, V> var1);
}
