/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

/**
 *
 * @author Yorkit Tran
 */
public class XMLUtils implements Serializable {
    
    public static Document parseHTMLSourceToDOM(String source) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
        return doc;
    }
    
    public static XPath createXPath() throws Exception {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();
        return xPath;
    }
    
    public static boolean transformXMLToStreamResult(Document doc, String filePath) {
        DOMSource src = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        
        try {
            // Setup
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            // Implement dtd to xml
            DOMImplementation domImpl = doc.getImplementation();
            DocumentType doctype = domImpl.createDocumentType("doctype", "", "products.dtd");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            
            // transform
            transformer.transform(src, result);
            return true;
        } catch (TransformerException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
