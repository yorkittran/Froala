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
    public static final String MRSIMPLE_CATEGORY_URLS_XPATH = "//*[@id=\"section-header\"]/div/div[1]/nav/ul/li[2]/div/div/div[1]/ul/li/a";
    public static final String MRSIMPLE_LIST_PRODUCT_URLS_XPATH = "//*[@class='Grid__Cell 1/2--phone 1/2--tablet-and-up 1/3--desk']/div/div/a";
    
    public static final String JACKJONES_URL = "https://www.jackjones.com/se/en/home";
    public static final String JACKJONES_CATEGORY_URLS_XPATH = "//*[@data-category-id='jj-jeans2' or @data-category-id='jj-shorts' or @data-category-id='jj-trousers']/a";
    public static final String JACKJONES_LIST_PRODUCT_URLS_XPATH = "//*[@class='isotope-grid__item isotope-grid__item--product']/article/div/header/a";
    public static final String JACKJONES_SIZE_PARAMETER = "?start=0&sz=200";
    
    public static final String XML_FILE = "/WEB-INF/products.xml";
    public static final String XSL_MRSIMPLE_FILE = "/WEB-INF/products_mrsimple.xsl";
    public static final String XSD_MRSIMPLE_FILE = "/WEB-INF/products_mrsimple.xsd";
    public static final String XSL_JACKJONES_FILE = "/WEB-INF/products_jackjones.xsl";
    public static final String DTD_JACKJONES_FILE = "/WEB-INF/products_jackjones.dtd";
    
    public static final String INDEX_PAGE = "index.jsp";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String REGISTER_PAGE = "register.jsp";
    public static final String RECOMMEND_PAGE = "recommend.jsp";
    public static final String SHOP_PAGE = "shop.jsp";
    public static final String SUCCESS_PAGE = "success.jsp";
    public static final String ERROR_PAGE = "error.jsp";
    
    public static final String ADMIN_ROLE = "admin";
    public static final String USER_ROLE = "user";
    
    public static final String TOP_LAYER_CATEGORY_TYPE = "Top/Layer";
    public static final String LAYER_CATEGORY_TYPE = "Layer";
    public static final String TOP_CATEGORY_TYPE = "Top";
    public static final String BOTTOM_CATEGORY_TYPE = "Bottom";
    
    public static final String PATTERN_TAG_NAME = "pattern";

    public static final String LOGIN_ERROR_MESSAGE = "Invalid email or password. Please try again.";
    public static final String REGISTER_ERROR_MESSAGE = "Confirm password is not matched.";
    public static final int PREFER_LAYER = 5;
}
