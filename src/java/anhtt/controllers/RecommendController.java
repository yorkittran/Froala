/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.controllers;

import anhtt.clients.AnswersClient;
import anhtt.clients.ProductsClient;
import anhtt.clients.QuestionsClient;
import anhtt.clients.TagsofanswersClient;
import anhtt.clients.TagsofproductsClient;
import anhtt.config.Constant;
import anhtt.dtos.Answers;
import anhtt.dtos.ListProducts;
import anhtt.dtos.Products;
import anhtt.dtos.Questions;
import anhtt.dtos.Tags;
import anhtt.dtos.Tagsofanswers;
import anhtt.dtos.Tagsofproducts;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Yorkit Tran
 */
public class RecommendController extends HttpServlet {

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
        try {
            // Init client
            QuestionsClient questionsClient = new QuestionsClient();
            AnswersClient answersClient = new AnswersClient();
            ProductsClient productsClient = new ProductsClient();
            TagsofanswersClient tagsOfAnswersClient = new TagsofanswersClient();
            TagsofproductsClient tagsOfProductsClient = new TagsofproductsClient();
            
            // Get some data
            List<Questions> listQuestions = questionsClient.findAll_XML(List.class);
            List<Products> listProducts = productsClient.findAll_XML(List.class);
            Map<Products, Integer> listRecommendProducts = new HashMap<>();
            Map<Tags, Integer> listTagsWithMark = new HashMap<>();
            
            // Get question layer
            boolean preferLayer = false;
            
            for (Questions question : listQuestions) {
                // Get answer id
                String answerIdStr = request.getParameter("question" + question.getId());
                int answerId = Integer.parseInt(answerIdStr.replace("answer", ""));
                if (question.getName().equals("Do you prefer a layer over your top piece?") 
                        && answersClient.find_XML(Answers.class, String.valueOf(answerId)).getName().equals("Yes")) {
                    preferLayer = true;
                }
                
                // Get tagsofanswers by answer id
                List<Tagsofanswers> listTagsOfAnswers = tagsOfAnswersClient.findByAnswerId(List.class, String.valueOf(answerId));
                for (Tagsofanswers tagsOfAnswers : listTagsOfAnswers) {
                    // Add tag and mark into hashmap
                    Tags tag = tagsOfAnswers.getTagId();
                    int mark = question.getMark();
                    
                    // Put into hashmap
                    listTagsWithMark.put(tag, mark);
                }
            }
            for (Products product : listProducts) {
                // Get tags of single product
                List<Tagsofproducts> listTagsOfProducts = tagsOfProductsClient.findByProductId(List.class, String.valueOf(product.getId()));
                
                // Add tag id into List (cause cant find Tags in listTagsOfProducts.contains)
                List<Integer> listTagsOfProductsId = new ArrayList<>();
                for (Tagsofproducts tagsOfProducts : listTagsOfProducts) {
                    listTagsOfProductsId.add(tagsOfProducts.getTagId().getId());
                }
                
                // Increase mark
                int mark = 0;
                for (Map.Entry<Tags, Integer> entry : listTagsWithMark.entrySet()) {
                    if (listTagsOfProductsId.contains(entry.getKey().getId())) {
                        mark += entry.getValue();
                    }
                }
                
                // Put into hashmap
                if (mark > 0) {
                    listRecommendProducts.put(product, mark);
                }
            }
            
            // Sort hashmap listRecommendProducts
            Object[] arrRecommendProducts = listRecommendProducts.entrySet().toArray();
            Arrays.sort(arrRecommendProducts, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<Products, Integer>) o2).getValue().compareTo(((Map.Entry<Products, Integer>) o1).getValue());
                }
            });
            
            // Init set of clothes
            List<Products> setOfClothes = new ArrayList<>();
            List<Products> listLayerHighMark = new ArrayList<>();
            List<Products> listTopHighMark = new ArrayList<>();
            List<Products> listTeeHighMark = new ArrayList<>();
            List<Products> listBottomHighMark = new ArrayList<>();
            int highScore = 0;
            
            // Check if user prefer layer
            if (preferLayer) {
                // Get layer
                for (Object entry : arrRecommendProducts) {
                    Products product = ((Map.Entry<Products, Integer>) entry).getKey();
                    String productName = product.getCategoryId().getName();
                    int productMark = ((Map.Entry<Products, Integer>) entry).getValue();

                    // Add short sleeve and jacket to list
                    if ((productName.contains("Short Sleeve") || productName.contains("Jacket")) && highScore <= productMark) {
                        listLayerHighMark.add(product);
                        highScore = ((Map.Entry<Products, Integer>) entry).getValue();
                    } 
                }
            }
            System.out.println("Layer High Score: " + highScore);
            // Get top
            highScore = 0;
            for (Object entry : arrRecommendProducts) {
                Products product = ((Map.Entry<Products, Integer>) entry).getKey();
                String productName = product.getCategoryId().getName();
                String productType = product.getCategoryId().getType();
                int productMark = ((Map.Entry<Products, Integer>) entry).getValue();
                // Add top to list
                if (productType.equals(Constant.TOP_CATEGORY_TYPE) && !productName.contains("Jacket") && highScore <= productMark) {
                    listTopHighMark.add(product);
                    highScore = ((Map.Entry<Products, Integer>) entry).getValue();
                } 
            }
            System.out.println("Top High Score: " + highScore);
            // Get tees
            highScore = 0;
            for (Object entry : arrRecommendProducts) {
                Products product = ((Map.Entry<Products, Integer>) entry).getKey();
                int productMark = ((Map.Entry<Products, Integer>) entry).getValue();
                // Add tees to list only tee (for layer short sleeve)
                if (product.getCategoryId().getName().equals("Tees") && highScore <= productMark) {
                    // tees without pattern
                    boolean pattern = false;
                    List<Tagsofproducts> listTagsOfTee = tagsOfProductsClient.findByProductId(List.class, String.valueOf(product.getId()));
                    for (Tagsofproducts tagOfTee : listTagsOfTee) {
                        if (tagOfTee.getTagId().getName().equals(Constant.PATTERN_TAG_NAME)) {
                            pattern = true;
                            break;
                        }
                    }
                    if (!pattern) {
                        listTeeHighMark.add(product);
                        highScore = ((Map.Entry<Products, Integer>) entry).getValue();
                    }
                }
            }
            System.out.println("Tee High Score: " + highScore);
            // Get bottom
            highScore = 0;
            for (Object entry : arrRecommendProducts) {
                Products product = ((Map.Entry<Products, Integer>) entry).getKey();
                String productType = product.getCategoryId().getType();
                int productMark = ((Map.Entry<Products, Integer>) entry).getValue();
                
                // Add top to list
                if (productType.equals(Constant.BOTTOM_CATEGORY_TYPE) && highScore <= productMark) {
                    listBottomHighMark.add(product);
                    highScore = ((Map.Entry<Products, Integer>) entry).getValue();
                } 
            }
            System.out.println("Bottom High Score: " + highScore);
            
            System.out.println("Layer======");
            for (Products product : listLayerHighMark) {
                System.out.println(product.getName());
            }
            
            System.out.println("Top======");
            for (Products product : listTopHighMark) {
                System.out.println(product.getName());
            }
            
            System.out.println("Tee======");
            for (Products product : listTeeHighMark) {
                System.out.println(product.getName());
            }
            
            System.out.println("Bottom======");
            for (Products product : listBottomHighMark) {
                System.out.println(product.getName());
            }
            
            // Pick random item
            if (preferLayer) {
                // Get layer
                int randLayerIndex = ThreadLocalRandom.current().nextInt(listLayerHighMark.size()) % listLayerHighMark.size();
                Products layer = listLayerHighMark.get(randLayerIndex);
                setOfClothes.add(layer);
                
                // Check if layer is Short Sleeve -> should get a tee as top
                if (layer.getName().contains("Short Sleeve")) {
                    // Get tee
                    int randTeeIndex = ThreadLocalRandom.current().nextInt(listTeeHighMark.size()) % listTeeHighMark.size();
                    Products tee = listTeeHighMark.get(randTeeIndex);
                    setOfClothes.add(tee);
                } else {
                    // Get top
                    int randTopIndex = ThreadLocalRandom.current().nextInt(listTopHighMark.size()) % listTopHighMark.size();
                    Products top = listTopHighMark.get(randTopIndex);
                    setOfClothes.add(top);
                }
            } else {
                // Get top
                int randTopIndex = ThreadLocalRandom.current().nextInt(listTopHighMark.size()) % listTopHighMark.size();
                Products top = listTopHighMark.get(randTopIndex);
                setOfClothes.add(top);
            }
            // Get bottom
            int randBottomIndex = ThreadLocalRandom.current().nextInt(listBottomHighMark.size()) % listBottomHighMark.size();
            Products bottom = listBottomHighMark.get(randBottomIndex);
            setOfClothes.add(bottom);
            request.setAttribute("SETOFCLOTHES", setOfClothes);
            
            String setOfClothesXML = jaxbObjectToXML(new ListProducts(setOfClothes));
            
            response.setContentType("application/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(setOfClothesXML);
        } catch (Exception e) {
            log("ERROR at RecommendController: " + e.getMessage());
        }
    }
    
    private static String jaxbObjectToXML(ListProducts listProduct) {
        String xmlString = "";
        try {
            JAXBContext context = JAXBContext.newInstance(ListProducts.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            m.marshal(listProduct, sw);
            xmlString = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlString;
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
