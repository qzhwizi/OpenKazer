package com.twelvemonkeys.imageio.metadata;

import com.twelvemonkeys.lang.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractDirectory implements Directory {
   private final List<Entry> entries = new ArrayList<>();
   private final List<Entry> unmodifiable = Collections.unmodifiableList(this.entries);

   protected AbstractDirectory(Collection<? extends Entry> var1) {
      if (var1 != null) {
         this.entries.addAll(Validate.noNullElements(var1));
      }
   }

   @Override
   public Entry getEntryById(Object var1) {
      for (Entry var3 : this) {
         if (var3.getIdentifier().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   @Override
   public Entry getEntryByFieldName(String var1) {
      for (Entry var3 : this) {
         if (var3.getFieldName() != null && var3.getFieldName().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   @Override
   public Iterator<Entry> iterator() {
      return this.isReadOnly() ? this.unmodifiable.iterator() : this.entries.iterator();
   }

   protected final void assertMutable() {
      if (this.isReadOnly()) {
         throw new UnsupportedOperationException("Directory is read-only");
      }
   }

   @Override
   public boolean add(Entry var1) {
      this.assertMutable();
      return this.entries.add(var1);
   }

   @Override
   public boolean remove(Object var1) {
      this.assertMutable();
      return this.entries.remove(var1);
   }

   @Override
   public int size() {
      return this.entries.size();
   }

   @Override
   public boolean isReadOnly() {
      return true;
   }

   @Override
   public int hashCode() {
      return this.entries.hashCode();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         AbstractDirectory var2 = (AbstractDirectory)var1;
         return this.entries.equals(var2.entries);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return String.format("%s%s", this.getClass().getSimpleName(), this.entries.toString());
   }
}
