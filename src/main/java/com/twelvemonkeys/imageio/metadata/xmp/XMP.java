package com.twelvemonkeys.imageio.metadata.xmp;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public interface XMP {
   String NS_RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
   String NS_DC = "http://purl.org/dc/elements/1.1/";
   String NS_EXIF = "http://ns.adobe.com/exif/1.0/";
   String NS_PHOTOSHOP = "http://ns.adobe.com/photoshop/1.0/";
   String NS_ST_REF = "http://ns.adobe.com/xap/1.0/sType/ResourceRef#";
   String NS_TIFF = "http://ns.adobe.com/tiff/1.0/";
   String NS_XAP = "http://ns.adobe.com/xap/1.0/";
   String NS_XAP_MM = "http://ns.adobe.com/xap/1.0/mm/";
   String NS_X = "adobe:ns:meta/";
   Map<String, String> DEFAULT_NS_MAPPING = Collections.unmodifiableMap(new XMPNamespaceMapping(true));
   Set<String> ELEMENTS = Collections.unmodifiableSet(new XMPNamespaceMapping(false).keySet());
}
