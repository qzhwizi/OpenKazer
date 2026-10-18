package com.twelvemonkeys.imageio.metadata;

import com.twelvemonkeys.lang.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractCompoundDirectory extends AbstractDirectory implements CompoundDirectory {
   private final List<Directory> directories = new ArrayList<>();

   protected AbstractCompoundDirectory(Collection<? extends Directory> var1) {
      super(null);
      if (var1 != null) {
         this.directories.addAll(Validate.noNullElements(var1));
      }
   }

   @Override
   public Directory getDirectory(int var1) {
      return this.directories.get(var1);
   }

   @Override
   public int directoryCount() {
      return this.directories.size();
   }

   @Override
   public Entry getEntryById(Object var1) {
      for (Directory var3 : this.directories) {
         Entry var4 = var3.getEntryById(var1);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   @Override
   public Entry getEntryByFieldName(String var1) {
      for (Directory var3 : this.directories) {
         Entry var4 = var3.getEntryByFieldName(var1);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   @Override
   public Iterator<Entry> iterator() {
      return new Iterator<Entry>() {
         Iterator<Directory> directoryIterator = AbstractCompoundDirectory.this.directories.iterator();
         Iterator<Entry> current;

         @Override
         public boolean hasNext() {
            return this.current != null && this.current.hasNext()
               || this.directoryIterator.hasNext() && (this.current = this.directoryIterator.next().iterator()).hasNext();
         }

         public Entry next() {
            this.hasNext();
            return this.current.next();
         }

         @Override
         public void remove() {
            this.current.remove();
         }
      };
   }

   @Override
   public boolean add(Entry var1) {
      throw new UnsupportedOperationException("Directory is read-only");
   }

   @Override
   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Directory is read-only");
   }

   @Override
   public boolean isReadOnly() {
      return true;
   }

   @Override
   public int size() {
      int var1 = 0;

      for (Directory var3 : this.directories) {
         var1 += var3.size();
      }

      return var1;
   }

   @Override
   public String toString() {
      return String.format("%s%s", this.getClass().getSimpleName(), this.directories.toString());
   }

   @Override
   public int hashCode() {
      int var1 = 0;

      for (Directory var3 : this.directories) {
         var1 ^= var3.hashCode();
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1.getClass() != this.getClass()) {
         return false;
      } else {
         CompoundDirectory var2 = (CompoundDirectory)var1;
         if (this.directoryCount() != var2.directoryCount()) {
            return false;
         } else {
            for (int var3 = 0; var3 < this.directoryCount(); var3++) {
               if (!this.getDirectory(var3).equals(var2.getDirectory(var3))) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
