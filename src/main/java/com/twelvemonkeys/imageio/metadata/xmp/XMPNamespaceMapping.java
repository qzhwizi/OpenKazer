package com.twelvemonkeys.imageio.metadata.xmp;

import java.util.HashMap;

final class XMPNamespaceMapping extends HashMap<String, String> {
   public XMPNamespaceMapping(boolean var1) {
      if (var1) {
         this.put("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf");
         this.put("adobe:ns:meta/", "x");
      }

      this.put("http://purl.org/dc/elements/1.1/", "dc");
      this.put("http://ns.adobe.com/exif/1.0/", "exif");
      this.put("http://ns.adobe.com/photoshop/1.0/", "photoshop");
      this.put("http://ns.adobe.com/xap/1.0/sType/ResourceRef#", "stRef");
      this.put("http://ns.adobe.com/tiff/1.0/", "tiff");
      this.put("http://ns.adobe.com/xap/1.0/", "xap");
      this.put("http://ns.adobe.com/xap/1.0/mm/", "xapMM");
   }
}
