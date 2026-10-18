package com.twelvemonkeys.imageio.metadata;

public interface Entry {
   Object getIdentifier();

   String getFieldName();

   Object getValue();

   String getValueAsString();

   String getTypeName();

   int valueCount();
}
