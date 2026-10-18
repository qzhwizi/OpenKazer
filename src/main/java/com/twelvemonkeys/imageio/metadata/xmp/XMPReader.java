package com.twelvemonkeys.imageio.metadata.xmp;

import com.twelvemonkeys.imageio.metadata.Directory;
import com.twelvemonkeys.imageio.metadata.MetadataReader;
import com.twelvemonkeys.imageio.util.IIOUtil;
import com.twelvemonkeys.lang.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.IIOException;
import javax.imageio.stream.ImageInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class XMPReader extends MetadataReader {
   @Override
   public Directory read(ImageInputStream var1) throws IOException {
      Validate.notNull(var1, "input");

      try {
         DocumentBuilderFactory var2 = this.createDocumentBuilderFactory();
         DocumentBuilder var3 = var2.newDocumentBuilder();
         var3.setErrorHandler(new DefaultHandler());
         Document var4 = var3.parse(new InputSource(IIOUtil.createStreamAdapter(var1)));
         String var5 = this.getToolkit(var4);
         Node var6 = var4.getElementsByTagNameNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "RDF").item(0);
         NodeList var7 = var4.getElementsByTagNameNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "Description");
         return this.parseDirectories(var6, var7, var5);
      } catch (SAXException var8) {
         throw new IIOException(var8.getMessage(), var8);
      } catch (ParserConfigurationException var9) {
         throw new RuntimeException(var9);
      }
   }

   private DocumentBuilderFactory createDocumentBuilderFactory() throws ParserConfigurationException {
      DocumentBuilderFactory var1 = DocumentBuilderFactory.newInstance();
      var1.setNamespaceAware(true);
      var1.setXIncludeAware(false);
      var1.setExpandEntityReferences(false);
      var1.setAttribute("http://javax.xml.XMLConstants/feature/secure-processing", true);
      var1.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      var1.setAttribute("http://javax.xml.XMLConstants/property/accessExternalSchema", "");
      var1.setFeature("http://xml.org/sax/features/external-general-entities", false);
      var1.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      var1.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      return var1;
   }

   private String getToolkit(Document var1) {
      NodeList var2 = var1.getElementsByTagNameNS("adobe:ns:meta/", "xmpmeta");
      if (var2 != null && var2.getLength() > 0) {
         Node var3 = var2.item(0).getAttributes().getNamedItemNS("adobe:ns:meta/", "xmptk");
         return var3 != null ? var3.getNodeValue() : null;
      } else {
         return null;
      }
   }

   private XMPDirectory parseDirectories(Node var1, NodeList var2, String var3) {
      LinkedHashMap var4 = new LinkedHashMap();

      for (Node var6 : this.asIterable(var2)) {
         if (var6.getParentNode() == var1) {
            this.parseAttributesForKnownElements(var4, var6);

            for (Node var8 : this.asIterable(var6.getChildNodes())) {
               if (var8.getNodeType() == 1) {
                  List var9 = (List)var4.get(var8.getNamespaceURI());
                  if (var9 == null) {
                     var9 = new ArrayList();
                     var4.put(var8.getNamespaceURI(), var9);
                  }

                  Object var10;
                  if (this.isResourceType(var8)) {
                     var10 = this.parseAsResource(var8);
                  } else {
                     LinkedHashMap var11 = new LinkedHashMap();
                     this.parseAttributesForKnownElements(var11, var8);
                     if (var11.isEmpty()) {
                        var10 = this.getChildTextValue(var8);
                     } else {
                        ArrayList var12 = new ArrayList(var11.size());

                        for (Entry var14 : (Iterable<Entry>) var11.entrySet()) {
                           var12.addAll((Collection)var14.getValue());
                        }

                        var10 = new RDFDescription(var12);
                     }
                  }

                  var9.add(new XMPEntry(var8.getNamespaceURI() + var8.getLocalName(), var8.getLocalName(), var10));
               }
            }
         }
      }

      ArrayList var15 = new ArrayList(var4.size());

      for (Entry var17 : (Iterable<Entry>) var4.entrySet()) {
         var15.add(new RDFDescription((String)var17.getKey(), (Collection<? extends com.twelvemonkeys.imageio.metadata.Entry>)var17.getValue()));
      }

      return new XMPDirectory(var15, var3);
   }

   private boolean isResourceType(Node var1) {
      Node var2 = var1.getAttributes().getNamedItemNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType");
      return var2 != null && "Resource".equals(var2.getNodeValue());
   }

   private RDFDescription parseAsResource(Node var1) {
      ArrayList var2 = new ArrayList();

      for (Node var4 : this.asIterable(var1.getChildNodes())) {
         if (var4.getNodeType() == 1) {
            var2.add(new XMPEntry(var4.getNamespaceURI() + var4.getLocalName(), var4.getLocalName(), this.getChildTextValue(var4)));
         }
      }

      return new RDFDescription(var2);
   }

   private void parseAttributesForKnownElements(Map<String, List<com.twelvemonkeys.imageio.metadata.Entry>> var1, Node var2) {
      NamedNodeMap var3 = var2.getAttributes();

      for (Node var5 : this.asIterable(var3)) {
         if (XMP.ELEMENTS.contains(var5.getNamespaceURI())) {
            List<com.twelvemonkeys.imageio.metadata.Entry> var6 = (List)var1.get(var5.getNamespaceURI());
            if (var6 == null) {
               var6 = new ArrayList();
               var1.put(var5.getNamespaceURI(), var6);
            }

            var6.add(new XMPEntry(var5.getNamespaceURI() + var5.getLocalName(), var5.getLocalName(), var5.getNodeValue()));
         }
      }
   }

   private Object getChildTextValue(Node var1) {
      for (Node var3 : this.asIterable(var1.getChildNodes())) {
         if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(var3.getNamespaceURI()) && "Alt".equals(var3.getLocalName())) {
            LinkedHashMap var11 = new LinkedHashMap();

            for (Node var13 : this.asIterable(var3.getChildNodes())) {
               if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(var13.getNamespaceURI()) && "li".equals(var13.getLocalName())) {
                  NamedNodeMap var14 = var13.getAttributes();
                  Node var8 = var14.getNamedItem("xml:lang");
                  var11.put(var8 == null ? null : var8.getTextContent(), this.getChildTextValue(var13));
               }
            }

            return var11;
         }

         if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(var3.getNamespaceURI())
            && ("Seq".equals(var3.getLocalName()) || "Bag".equals(var3.getLocalName()))) {
            ArrayList var4 = new ArrayList();

            for (Node var6 : this.asIterable(var3.getChildNodes())) {
               if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(var6.getNamespaceURI()) && "li".equals(var6.getLocalName())) {
                  Object var7 = this.getChildTextValue(var6);
                  var4.add(var7);
               }
            }

            return Collections.unmodifiableList(var4);
         }
      }

      if (this.isResourceType(var1)) {
         return this.parseAsResource(var1);
      } else {
         Node var9 = var1.getFirstChild();
         String var10 = var9 != null ? var9.getNodeValue() : null;
         return var10 != null ? var10.trim() : "";
      }
   }

   private Iterable<? extends Node> asIterable(final NamedNodeMap var1) {
      return new Iterable<Node>() {
         @Override
         public Iterator<Node> iterator() {
            return new Iterator<Node>() {
               private int index;

               @Override
               public boolean hasNext() {
                  return var1 != null && var1.getLength() > this.index;
               }

               public Node next() {
                  return var1.item(this.index++);
               }

               @Override
               public void remove() {
                  throw new UnsupportedOperationException("Method remove not supported");
               }
            };
         }
      };
   }

   private Iterable<? extends Node> asIterable(final NodeList var1) {
      return new Iterable<Node>() {
         @Override
         public Iterator<Node> iterator() {
            return new Iterator<Node>() {
               private int index;

               @Override
               public boolean hasNext() {
                  return var1 != null && var1.getLength() > this.index;
               }

               public Node next() {
                  return var1.item(this.index++);
               }

               @Override
               public void remove() {
                  throw new UnsupportedOperationException("Method remove not supported");
               }
            };
         }
      };
   }
}
