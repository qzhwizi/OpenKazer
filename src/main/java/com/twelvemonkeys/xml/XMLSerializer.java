package com.twelvemonkeys.xml;

import com.twelvemonkeys.lang.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.SAXException;

public class XMLSerializer {
   private final OutputStream output;
   private final Charset encoding;
   private final XMLSerializer.SerializationContext context;

   public XMLSerializer(OutputStream var1, String var2) {
      this.output = var1;
      this.encoding = Charset.forName(var2);
      this.context = new XMLSerializer.SerializationContext();
   }

   public final XMLSerializer indentation(String var1) {
      this.context.indent = var1 != null ? var1 : "\t";
      return this;
   }

   public final XMLSerializer stripComments(boolean var1) {
      this.context.stripComments = var1;
      return this;
   }

   public void serialize(Document var1) {
      this.serialize(var1, true);
   }

   public void serialize(Node var1, boolean var2) {
      PrintWriter var3 = new PrintWriter(new OutputStreamWriter(this.output, this.encoding));

      try {
         if (var2) {
            this.writeXMLDeclaration(var3);
         }

         this.writeXML(var3, var1, this.context.copy());
      } finally {
         var3.flush();
      }
   }

   private void writeXMLDeclaration(PrintWriter var1) {
      var1.print("<?xml version=\"1.0\" encoding=\"");
      var1.print(this.encoding.name());
      var1.println("\"?>");
   }

   private void writeXML(PrintWriter var1, Node var2, XMLSerializer.SerializationContext var3) {
      this.writeNodeRecursive(var1, var2, var3);
   }

   private void writeNodeRecursive(PrintWriter var1, Node var2, XMLSerializer.SerializationContext var3) {
      if (var2.getNodeType() != 3) {
         indentToLevel(var1, var3);
      }

      switch (var2.getNodeType()) {
         case 1:
            boolean var4 = var3.preserveSpace;
            updatePreserveSpace(var2, var3);
            this.writeElement(var1, (Element)var2, var3);
            var3.preserveSpace = var4;
            break;
         case 2:
            throw new IllegalArgumentException("Malformed input Document: Attribute nodes should only occur inside Element nodes");
         case 3:
            this.writeText(var1, var2, var3);
            break;
         case 4:
            this.writeCData(var1, var2);
            break;
         case 5:
         case 6:
         case 12:
         default:
            throw new InternalError("Lazy programmer never implemented serialization of " + var2.getClass());
         case 7:
            this.writeProcessingInstruction(var1, (ProcessingInstruction)var2);
            break;
         case 8:
            this.writeComment(var1, var2, var3);
            break;
         case 9:
         case 11:
            this.writeDocument(var1, var2, var3);
            break;
         case 10:
            this.writeDoctype(var1, (DocumentType)var2);
      }
   }

   private void writeProcessingInstruction(PrintWriter var1, ProcessingInstruction var2) {
      var1.print("\n<?");
      var1.print(var2.getTarget());
      String var3 = var2.getData();
      if (var3 != null) {
         var1.print(" ");
         var1.print(var3);
      }

      var1.println("?>");
   }

   private void writeText(PrintWriter var1, Node var2, XMLSerializer.SerializationContext var3) {
      String var4 = var2.getNodeValue();
      if (var3.preserveSpace) {
         var1.print(maybeEscapeElementValue(var4));
      } else if (!StringUtil.isEmpty(var4)) {
         String var5 = maybeEscapeElementValue(var4.trim());
         indentToLevel(var1, var3);
         var1.println(var5);
      }
   }

   private void writeCData(PrintWriter var1, Node var2) {
      var1.print("<![CDATA[");
      var1.print(validateCDataValue(var2.getNodeValue()));
      var1.println("]]>");
   }

   private static void updatePreserveSpace(Node var0, XMLSerializer.SerializationContext var1) {
      NamedNodeMap var2 = var0.getAttributes();
      if (var2 != null) {
         Node var3 = var2.getNamedItem("xml:space");
         if (var3 != null) {
            if ("preserve".equals(var3.getNodeValue())) {
               var1.preserveSpace = true;
            } else if ("default".equals(var3.getNodeValue())) {
               var1.preserveSpace = false;
            }
         }
      }
   }

   private static void indentToLevel(PrintWriter var0, XMLSerializer.SerializationContext var1) {
      for (int var2 = 0; var2 < var1.level; var2++) {
         var0.print(var1.indent);
      }
   }

   private void writeComment(PrintWriter var1, Node var2, XMLSerializer.SerializationContext var3) {
      if (!var3.stripComments) {
         String var4 = var2.getNodeValue();
         validateCommentValue(var4);
         if (var4.startsWith(" ")) {
            var1.print("<!--");
         } else {
            var1.print("<!-- ");
         }

         var1.print(var4);
         if (var4.endsWith(" ")) {
            var1.println("-->");
         } else {
            var1.println(" -->");
         }
      }
   }

   static String maybeEscapeElementValue(String var0) {
      int var1 = needsEscapeElement(var0);
      if (var1 < 0) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder(var0.substring(0, var1));
         var2.ensureCapacity(var0.length() + 30);
         int var3 = var1;

         for (int var4 = var1; var4 < var0.length(); var4++) {
            switch (var0.charAt(var4)) {
               case '&':
                  var3 = appendAndEscape(var0, var3, var4, var2, "&amp;");
                  break;
               case '<':
                  var3 = appendAndEscape(var0, var3, var4, var2, "&lt;");
                  break;
               case '>':
                  var3 = appendAndEscape(var0, var3, var4, var2, "&gt;");
            }
         }

         var2.append(var0.substring(var3));
         return var2.toString();
      }
   }

   private static int appendAndEscape(String var0, int var1, int var2, StringBuilder var3, String var4) {
      var3.append(var0, var1, var2);
      var3.append(var4);
      return var2 + 1;
   }

   private static int needsEscapeElement(String var0) {
      for (int var1 = 0; var1 < var0.length(); var1++) {
         switch (var0.charAt(var1)) {
            case '&':
            case '<':
            case '>':
               return var1;
         }
      }

      return -1;
   }

   private static String maybeEscapeAttributeValue(String var0) {
      int var1 = needsEscapeAttribute(var0);
      if (var1 < 0) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder(var0.substring(0, var1));
         var2.ensureCapacity(var0.length() + 16);
         int var3 = var1;

         for (int var4 = var1; var4 < var0.length(); var4++) {
            switch (var0.charAt(var4)) {
               case '"':
                  var3 = appendAndEscape(var0, var3, var4, var2, "&quot;");
                  break;
               case '&':
                  var3 = appendAndEscape(var0, var3, var4, var2, "&amp;");
            }
         }

         var2.append(var0.substring(var3));
         return var2.toString();
      }
   }

   private static int needsEscapeAttribute(String var0) {
      for (int var1 = 0; var1 < var0.length(); var1++) {
         switch (var0.charAt(var1)) {
            case '"':
            case '&':
               return var1;
         }
      }

      return -1;
   }

   private static String validateCDataValue(String var0) {
      if (var0.contains("]]>")) {
         throw new IllegalArgumentException("Malformed input document: CDATA block may not contain the string ']]>'");
      } else {
         return var0;
      }
   }

   private static String validateCommentValue(String var0) {
      if (var0.contains("--")) {
         throw new IllegalArgumentException("Malformed input document: Comment may not contain the string '--'");
      } else {
         return var0;
      }
   }

   private void writeDocument(PrintWriter var1, Node var2, XMLSerializer.SerializationContext var3) {
      if (var2.hasChildNodes()) {
         for (Node var4 = var2.getFirstChild(); var4 != null; var4 = var4.getNextSibling()) {
            this.writeNodeRecursive(var1, var4, var3);
         }
      }
   }

   private void writeElement(PrintWriter var1, Element var2, XMLSerializer.SerializationContext var3) {
      var1.print("<");
      var1.print(var2.getTagName());
      String var4 = var2.getNamespaceURI();
      if (var4 != null && !var4.equals(var3.defaultNamespace)) {
         String var5 = var2.getPrefix();
         if (var5 == null) {
            var3.defaultNamespace = var4;
            var1.print(" xmlns");
         } else {
            var1.print(" xmlns:");
            var1.print(var5);
         }

         var1.print("=\"");
         var1.print(var4);
         var1.print("\"");
      }

      if (var2.hasAttributes()) {
         NamedNodeMap var9 = var2.getAttributes();

         for (int var6 = 0; var6 < var9.getLength(); var6++) {
            Attr var7 = (Attr)var9.item(var6);
            String var8 = var7.getName();
            if (!var8.startsWith("xmlns") || var8.length() != 5 && var8.charAt(5) != ':') {
               var1.print(" ");
               var1.print(var8);
               var1.print("=\"");
               var1.print(maybeEscapeAttributeValue(var7.getValue()));
               var1.print("\"");
            }
         }
      }

      if (var2.hasChildNodes()) {
         var1.print(">");
         if (!var3.preserveSpace) {
            var1.println();
         }

         for (Node var10 = var2.getFirstChild(); var10 != null; var10 = var10.getNextSibling()) {
            this.writeNodeRecursive(var1, var10, var3.push());
         }

         if (!var3.preserveSpace) {
            indentToLevel(var1, var3);
         }

         var1.print("</");
         var1.print(var2.getTagName());
         var1.println(">");
      } else if (var2.getNodeValue() != null) {
         var1.print(">");
         var1.print(var2.getNodeValue());
         var1.print("</");
         var1.print(var2.getTagName());
         var1.println(">");
      } else {
         var1.println("/>");
      }
   }

   private void writeDoctype(PrintWriter var1, DocumentType var2) {
      if (var2 != null) {
         var1.print("<!DOCTYPE ");
         var1.print(var2.getName());
         String var3 = var2.getPublicId();
         if (!StringUtil.isEmpty(var3)) {
            var1.print(" PUBLIC ");
            var1.print(var3);
         }

         String var4 = var2.getSystemId();
         if (!StringUtil.isEmpty(var4)) {
            if (StringUtil.isEmpty(var3)) {
               var1.print(" SYSTEM \"");
            } else {
               var1.print(" \"");
            }

            var1.print(var4);
            var1.print("\"");
         }

         String var5 = var2.getInternalSubset();
         if (!StringUtil.isEmpty(var5)) {
            var1.print(" [ ");
            var1.print(var5);
            var1.print(" ]");
         }

         var1.println(">");
      }
   }

   public static void main(String[] var0) throws IOException, SAXException {
      DocumentBuilderFactory var1 = DocumentBuilderFactory.newInstance();
      var1.setNamespaceAware(true);

      DocumentBuilder var2;
      try {
         var2 = var1.newDocumentBuilder();
      } catch (ParserConfigurationException var20) {
         throw new IOException(var20);
      }

      DOMImplementation var3 = var2.getDOMImplementation();
      Document var4 = var3.createDocument("http://www.twelvemonkeys.com/xml/test", "test", var3.createDocumentType("test", null, null));
      Element var5 = var4.getDocumentElement();
      var4.insertBefore(var4.createComment(new Date().toString()), var5);
      Element var6 = var4.createElement("sub");
      var5.appendChild(var6);
      Element var7 = var4.createElementNS("http://more.com/1999/namespace", "more:more");
      var7.setAttribute("foo", "test");
      var7.setAttribute("bar", "'really' \"legal\" & ok");
      var6.appendChild(var7);
      var7.appendChild(var4.createTextNode("Simply some text."));
      var7.appendChild(var4.createCDATASection("&something escaped;"));
      var7.appendChild(var4.createTextNode("More & <more>!"));
      var7.appendChild(var4.createTextNode("\"<<'&'>>\""));
      Element var8 = var4.createElement("another");
      var6.appendChild(var8);
      Element var9 = var4.createElement("yet-another");
      var9.setAttribute("this-one", "with-params");
      var6.appendChild(var9);
      Element var10 = var4.createElementNS("http://www.twelvemonkeys.com/xml/test", "pre");
      var10.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", "preserve");
      var10.appendChild(var4.createTextNode(" \t \n\r some text & white ' '   \n   "));
      var6.appendChild(var10);
      Element var11 = var4.createElementNS("http://www.twelvemonkeys.com/xml/test", "tight");
      var11.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:space", "preserve");
      var11.appendChild(var4.createTextNode("no-space-around-me"));
      var6.appendChild(var11);
      System.out.println("XMLSerializer:");
      XMLSerializer var12 = new XMLSerializer(System.out, "UTF-8");
      var12.serialize(var4);
      System.out.println();
      System.out.println("DOMSerializer:");
      DOMSerializer var13 = new DOMSerializer(System.out, "UTF-8");
      var13.setPrettyPrint(true);
      var13.serialize(var4);
      System.out.println();
      System.out.println("\n");
      ByteArrayOutputStream var14 = new ByteArrayOutputStream();
      XMLSerializer var15 = new XMLSerializer(var14, "UTF-8");
      var15.serialize(var4);
      ByteArrayOutputStream var16 = new ByteArrayOutputStream();
      DOMSerializer var17 = new DOMSerializer(var16, "UTF-8");
      var17.serialize(var4);
      Document var18 = var2.parse(new ByteArrayInputStream(var14.toByteArray()));
      System.out.println("XMLSerializer reparsed XMLSerializer:");
      var12.serialize(var18);
      System.out.println();
      System.out.println("DOMSerializer reparsed XMLSerializer:");
      var13.serialize(var18);
      System.out.println();
      Document var19 = var2.parse(new ByteArrayInputStream(var16.toByteArray()));
      System.out.println("XMLSerializer reparsed DOMSerializer:");
      var12.serialize(var19);
      System.out.println();
      System.out.println("DOMSerializer reparsed DOMSerializer:");
      var13.serialize(var19);
      System.out.println();
   }

   static class SerializationContext implements Cloneable {
      String indent = "\t";
      int level = 0;
      boolean preserveSpace = false;
      boolean stripComments = false;
      String defaultNamespace;

      public XMLSerializer.SerializationContext copy() {
         try {
            return (XMLSerializer.SerializationContext)this.clone();
         } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
         }
      }

      public XMLSerializer.SerializationContext push() {
         XMLSerializer.SerializationContext var1 = this.copy();
         var1.level++;
         return var1;
      }
   }
}
