/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.controllers;

import anhtt.clients.CategoriesClient;
import anhtt.clients.ProductsClient;
import anhtt.clients.TagsClient;
import anhtt.clients.TagsofproductsClient;
import anhtt.config.Constant;
import anhtt.dtos.Categories;
import anhtt.dtos.Products;
import anhtt.dtos.Tags;
import anhtt.dtos.Tagsofproducts;
import anhtt.utils.TextUtils;
import anhtt.utils.XMLUtils;
import anhtt.utils.XSLTApplier;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Yorkit Tran
 */
public class CrawlController extends HttpServlet {
    
    int countProducts = 0;
    int countTagsOfProduct = 0;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Constant.SUCCESS_PAGE;
        try {
//            crawlMrSimple();
            crawlJackJones();
            
            url = Constant.SUCCESS_PAGE;
        } catch (Exception e) {
            url = Constant.ERROR_PAGE;
            log("Error at CrawlController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void crawlMrSimple() throws Exception {
        // Get XSL file path
        String realPath = getServletContext().getRealPath("/");
        String xslPath = realPath + Constant.XSL_MRSIMPLE_FILE;
        String xsdPath = realPath + Constant.XSD_MRSIMPLE_FILE;

        // Init client and factory
        CategoriesClient categoriesClient = new CategoriesClient();
        ProductsClient productsClient = new ProductsClient();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaFile = new File(xsdPath);
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();

        // Get HTML source
        String source = TextUtils.getHTMLFromURL(Constant.MRSIMPLE_URL);
        Document document = XMLUtils.parseHTMLSourceToDOM(source);
        XPath xPath = XMLUtils.createXPath();

        NodeList categoriesNodeList = (NodeList) xPath.evaluate(Constant.MRSIMPLE_CATEGORY_URLS_XPATH, document, XPathConstants.NODESET);
        for (int i = 0; i < categoriesNodeList.getLength(); i++) {
            // Check category name is exist
            String categoryName = categoriesNodeList.item(i).getTextContent();
            System.out.println(categoryName);
            Categories foundCategory = categoriesClient.findByName(Categories.class, categoryName);
            if (foundCategory == null) {
                continue;
            }
            // Get list product HTML source
            String listProductURL = Constant.MRSIMPLE_URL + categoriesNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
            String listProductHTMLSrc = TextUtils.getHTMLFromURL(listProductURL);
            Document listProductDocument = XMLUtils.parseHTMLSourceToDOM(listProductHTMLSrc);

            NodeList listProductNodeList = (NodeList) xPath.evaluate(Constant.MRSIMPLE_LIST_PRODUCT_URLS_XPATH, listProductDocument, XPathConstants.NODESET);
            for (int j = 0; j < listProductNodeList.getLength(); j++) {
                // Get XML source from HTML source with XSLT
                String productURL = Constant.MRSIMPLE_URL + listProductNodeList.item(j).getAttributes().getNamedItem("href").getNodeValue();
                String productHTMLSrc = TextUtils.getHTMLFromURL(productURL);
                String xmlSrc = new XSLTApplier().applyStylesheet(xslPath, productHTMLSrc);

                // Unmarshall XML source and validate via Schema to get object
                JAXBContext jaxb = JAXBContext.newInstance(Products.class);
                validator.validate(new SAXSource(new InputSource(new StringReader(xmlSrc))));
                Unmarshaller unmarshaller = jaxb.createUnmarshaller();
                Products product = (Products) unmarshaller.unmarshal(new ByteArrayInputStream(xmlSrc.getBytes(StandardCharsets.UTF_8)));

                // Set some data to product
                Categories categoryId = new Categories(foundCategory.getId());
                product.setId(++countProducts);
                product.setCategoryId(categoryId);
                product.setUrl(productURL);
                
                // Insert to database
                productsClient.create_XML(product);
                
                // Create tags for product
                System.out.print(product.getName() + " " + product.getColour() + ": ");
                createTagsOfProducts(product);
                System.out.println();
                
                System.out.println("Got " + countProducts + " items");
//                break;
            }
//            break;
        }
    }
    
    private void crawlJackJones() throws Exception {
        // Get XSL file path
        String realPath = getServletContext().getRealPath("/");
        String xslPath = realPath + Constant.XSL_JACKJONES_FILE;
        String dtdPath = realPath + Constant.DTD_JACKJONES_FILE;

        // Init client and factory
        CategoriesClient categoriesClient = new CategoriesClient();
        ProductsClient productsClient = new ProductsClient();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdPath);
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        // Get HTML source
        String source = TextUtils.getHTMLFromURL(Constant.JACKJONES_URL);
        Document document = XMLUtils.parseHTMLSourceToDOM(source);
        XPath xPath = XMLUtils.createXPath();
        
        NodeList categoriesNodeList = (NodeList) xPath.evaluate(Constant.JACKJONES_CATEGORY_URLS_XPATH, document, XPathConstants.NODESET);
        for (int i = 0; i < categoriesNodeList.getLength(); i++) {
            // Check category name is exist
            String categoryName = categoriesNodeList.item(i).getTextContent();
            System.out.println(categoryName);
            Categories foundCategory = categoriesClient.findByName(Categories.class, categoryName);
            if (foundCategory == null) {
                continue;
            }
            // Get list product HTML source
            String listProductURL = categoriesNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue() + Constant.JACKJONES_SIZE_PARAMETER;
            String listProductHTMLSrc = TextUtils.getHTMLFromURL(listProductURL);
            Document listProductDocument = XMLUtils.parseHTMLSourceToDOM(listProductHTMLSrc);

            NodeList listProductNodeList = (NodeList) xPath.evaluate(Constant.JACKJONES_LIST_PRODUCT_URLS_XPATH, listProductDocument, XPathConstants.NODESET);
            for (int j = 0; j < listProductNodeList.getLength(); j++) {
                // Get XML source from HTML source with XSLT
                String productURL = listProductNodeList.item(j).getAttributes().getNamedItem("href").getNodeValue();
                String productHTMLSrc = TextUtils.getHTMLFromURL(productURL);
                String xmlSrc = new XSLTApplier().applyStylesheet(xslPath, productHTMLSrc);
                
                // Add DTD to XML string
                StreamSource stream = new StreamSource(new StringReader(xmlSrc));
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                transformer.transform(stream, result);
                String xmlSrcWithDTD = writer.toString();
                
                // Validate XML string with DTD by parse to DOM
                builder.setEntityResolver((String publicId, String systemId) -> {
                    if (systemId.endsWith(dtdPath)) {
                        return new InputSource(new FileInputStream(dtdPath));
                    }
                    return null;
                });
                Document doc = builder.parse(new InputSource(new StringReader(xmlSrcWithDTD)));
                
                // Unmarshall XML source and validate via Schema to get object
                JAXBContext jaxb = JAXBContext.newInstance(Products.class);
                Unmarshaller unmarshaller = jaxb.createUnmarshaller();
                Products product = (Products) unmarshaller.unmarshal(new ByteArrayInputStream(xmlSrc.getBytes(StandardCharsets.UTF_8)));

                // Set some data to product
                Categories categoryId = new Categories(foundCategory.getId());
                product.setId(++countProducts);
                product.setCategoryId(categoryId);
                product.setUrl(productURL);
                String[] strArr = productURL.split("_");
                product.setColour(strArr[strArr.length-1].replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));

                // Insert to database
                productsClient.create_XML(product);
                
                // Create tags for product
                System.out.print(product.getName() + " " + product.getColour() + ": ");
                createTagsOfProducts(product);
                System.out.println();
                
                System.out.println("Got " + countProducts + " items");
//                break;
            }
//            break;
        }
    }
    
    private void createTagsOfProducts(Products product) {
        TagsClient tagsClient = new TagsClient();
        List<Tags> listTags = tagsClient.findAll_XML(List.class);
        TagsofproductsClient tagsOfProductClient = new TagsofproductsClient();
        String textInfo = " " + product.getName().toLowerCase() + " " + product.getColour().toLowerCase() + " " + product.getDescription().toLowerCase() + " ";

        for (Tags tag : listTags) {
            String[] keywords = tag.getKeyword().split(",");
            for (String keyword : keywords) {
                if (textInfo.contains(" " + keyword + " ")) {
                    Tagsofproducts top = new Tagsofproducts(++countTagsOfProduct, product, tag);
                    System.out.print(tag.getName() + ",");
                    tagsOfProductClient.create_XML(top);
                    break;
                }
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
