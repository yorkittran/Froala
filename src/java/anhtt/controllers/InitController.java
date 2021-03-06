/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.controllers;

import anhtt.clients.FavoritesClient;
import anhtt.clients.ProductsClient;
import anhtt.config.Constant;
import anhtt.dtos.Favorites;
import anhtt.dtos.Products;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Yorkit Tran
 */
public class InitController extends HttpServlet {

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
            // Get random favorite
            FavoritesClient favoritesClient = new FavoritesClient();
            ProductsClient productsClient = new ProductsClient();
            List<Favorites> listFavorites = favoritesClient.findAll_XML(List.class);
            if (listFavorites.isEmpty()) {
                System.out.println("wtf");
                return;
            }
            int randIndex = ThreadLocalRandom.current().nextInt(listFavorites.size()) % listFavorites.size();
            Favorites randFavorites = listFavorites.get(randIndex);
            
            // Get product from favorite
            List<Products> setOfClothes = new ArrayList<>();
            Products layer = randFavorites.getLayerId();
            if (layer != null) {
                setOfClothes.add(productsClient.find_XML(Products.class, String.valueOf(randFavorites.getLayerId().getId())));
            }
            setOfClothes.add(productsClient.find_XML(Products.class, String.valueOf(randFavorites.getTopId().getId())));
            setOfClothes.add(productsClient.find_XML(Products.class, String.valueOf(randFavorites.getBottomId().getId())));
            
            request.setAttribute("SETOFCLOTHES", setOfClothes);
        } catch (Exception e) {
            log("ERROR at InitController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(Constant.INDEX_JSP_PAGE).forward(request, response);
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
