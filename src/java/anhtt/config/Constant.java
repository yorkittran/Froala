/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.config;

/**
 *
 * @author Yorkit Tran
 */
public class Constant {
    
    public static final String MRSIMPLE_URL = "https://mrsimple.com.au";
    public static final String MRSIMPLE_CATEGORY_URLS_XPATH = "//*[@id=\"section-header\"]/div/div[1]/nav/ul/li[position()=2 or position()=3]/div/div/div[1]/ul/li/a";
    public static final String MRSIMPLE_LIST_PRODUCT_URLS_XPATH = "//*[@class='Grid__Cell 1/2--phone 1/2--tablet-and-up 1/3--desk']/div/div/a";
    
    public static final String JACKJONES_URL = "https://www.jackjones.com/se/en/home";
    public static final String JACKJONES_CATEGORY_URLS_XPATH = "//*[@data-category-id='jj-jeans2' or @data-category-id='jj-shorts']/a";
    public static final String JACKJONES_LIST_PRODUCT_URLS_XPATH = "//*[@class='isotope-grid__item isotope-grid__item--product']/article/div/header/a";
    
    public static final String XML_FILE = "/WEB-INF/products.xml";
    public static final String XSL_MRSIMPLE_FILE = "/WEB-INF/products_mrsimple.xsl";
    public static final String XSD_MRSIMPLE_FILE = "/WEB-INF/products_mrsimple.xsd";
    public static final String XSL_JACKJONES_FILE = "/WEB-INF/products_jackjones.xsl";
    public static final String DTD_JACKJONES_FILE = "/WEB-INF/products_jackjones.dtd";
    
    public static final String INDEX_PAGE = "index.jsp";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String REGISTER_PAGE = "register.jsp";
    public static final String SUCCESS_PAGE = "success.jsp";
    public static final String ERROR_PAGE = "error.jsp";
    
    public static final String ADMIN_ROLE = "admin";
    public static final String USER_ROLE = "user";

    public static final String LOGIN_ERROR_MESSAGE = "Invalid email or password. Please try again.";
    public static final String REGISTER_ERROR_MESSAGE = "Confirm password is not matched.";
    
}
