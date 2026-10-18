package com.twelvemonkeys.imageio.metadata;

public interface Directory extends Iterable<Entry> {
   Entry getEntryById(Object var1);

   Entry getEntryByFieldName(String var1);

   boolean add(Entry var1);

   boolean remove(Object var1);

   int size();

   boolean isReadOnly();
}
