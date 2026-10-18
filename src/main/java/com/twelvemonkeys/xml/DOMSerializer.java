package com.twelvemonkeys.xml;

import java.io.OutputStream;
import java.io.Writer;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public final class DOMSerializer {
   private static final String PARAM_PRETTY_PRINT = "format-pretty-print";
   private static final String PARAM_XML_DECLARATION = "xml-declaration";
   private final LSSerializer serializer;
   private final LSOutput output;

   private DOMSerializer() {
      DOMImplementationLS var1 = DOMSerializer.Support.getImplementation();
      this.serializer = var1.createLSSerializer();
      this.output = var1.createLSOutput();
   }

   public DOMSerializer(OutputStream var1, String var2) {
      this();
      this.output.setByteStream(var1);
      this.output.setEncoding(var2);
   }

   public DOMSerializer(Writer var1) {
      this();
      this.output.setCharacterStream(var1);
   }

   public void setPrettyPrint(boolean var1) {
      DOMConfiguration var2 = this.serializer.getDomConfig();
      if (var2.canSetParameter("format-pretty-print", var1)) {
         var2.setParameter("format-pretty-print", var1);
      }
   }

   public boolean getPrettyPrint() {
      return Boolean.TRUE.equals(this.serializer.getDomConfig().getParameter("format-pretty-print"));
   }

   private void setXMLDeclaration(boolean var1) {
      this.serializer.getDomConfig().setParameter("xml-declaration", var1);
   }

   public void serialize(Document var1) {
      this.serializeImpl(var1, true);
   }

   public void serialize(Node var1) {
      this.serializeImpl(var1, false);
   }

   private void serializeImpl(Node var1, boolean var2) {
      this.setXMLDeclaration(var2);
      this.serializer.write(var1, this.output);
   }

   private static class Support {
      private static final DOMImplementationRegistry DOM_REGISTRY = createDOMRegistry();

      static DOMImplementationLS getImplementation() {
         DOMImplementationLS var0 = (DOMImplementationLS)DOM_REGISTRY.getDOMImplementation("LS 3.0");
         if (var0 != null) {
            return var0;
         } else {
            DOMImplementationList var1 = DOM_REGISTRY.getDOMImplementationList("");
            System.err.println("DOM implementations (" + var1.getLength() + "):");

            for (int var2 = 0; var2 < var1.getLength(); var2++) {
               System.err.println("    " + var1.item(var2));
            }

            throw new IllegalStateException("Could not create DOM Implementation (no LS support found)");
         }
      }

      private static DOMImplementationRegistry createDOMRegistry() {
         try {
            return DOMImplementationRegistry.newInstance();
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var1) {
            throw new IllegalStateException(var1);
         }
      }
   }
}
