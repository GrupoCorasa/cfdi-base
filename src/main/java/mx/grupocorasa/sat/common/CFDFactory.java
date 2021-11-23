package mx.grupocorasa.sat.common;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;

public abstract class CFDFactory {

    protected static String getVersion(byte[] data) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(data));
        XPathFactory xfactory = XPathFactory.newInstance();
        XPath xpath = xfactory.newXPath();
        String v = xpath.evaluate("/Comprobante/@version", doc);
        if (v.equals("")) {
            return xpath.evaluate("/Comprobante/@Version", doc);
        } else {
            return v;
        }
    }

}
