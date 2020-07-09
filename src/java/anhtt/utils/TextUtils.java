/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import anhtt.xmlchecker.XMLSyntaxChecker;

/**
 *
 * @author Yorkit Tran
 */
public class TextUtils {
    
    public static String refineHtml(String src) {
        src = getBody(src);
        src = removeMiscellaneousTags(src);
        XMLSyntaxChecker checker = new XMLSyntaxChecker();
        src = checker.check(src);
        src = getBody(src);
        return src;
    }
    
    private static String getBody(String src) {
        //remove head fragment before getting body fragment
        Matcher matcher = Pattern.compile("<head.*?</head>").matcher(src);
        String result = src, tmp = "";
        if (matcher.find()) {
            tmp = matcher.group(0);
            result = result.replace(tmp, "");
        }
        // end remove head
        String expression = "<body.*?</body>";
        matcher = Pattern.compile(expression).matcher(result);
        if (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }
    
    public static String removeMiscellaneousTags(String src) {
        String result = src;
        // remove all script tags
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        // remove all comments
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        // remove all whitespace
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        return result;
    }
    
    public static String getHTMLFromURL(String aUrl) throws Exception {
        String content = "";
        URL url = new URL(aUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            content += inputLine;
        }
        br.close();
        content = refineHtml(content.trim().replace("	", ""));
        if (checkWellformedXML(content)) {
            return content;
        } else {
            return null;
        }
    }
    
    public static boolean checkWellformedXML(String src) {
        boolean check = false;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println(exception.getMessage());
                }
                
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.out.println(exception.getMessage());
                }
                
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println(exception.getMessage());
                }
            });
            db.parse(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));
            check = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
}
