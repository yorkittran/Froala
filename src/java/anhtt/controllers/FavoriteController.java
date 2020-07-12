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
import anhtt.dtos.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Yorkit Tran
 */
public class FavoriteController extends HttpServlet {

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
        String url = Constant.ERROR_PAGE;
        try {
            // Check if user is login 
            HttpSession session = request.getSession(false);  
            Users user = (Users) session.getAttribute("USER");
            if (user == null) {
                request.getRequestDispatcher(Constant.LOGIN_PAGE).forward(request, response);
            }
            
            // Get random favorite
            FavoritesClient favoritesClient = new FavoritesClient();
            ProductsClient productsClient = new ProductsClient();
            List<List<Products>> listSetOfClothes = new ArrayList<>();
            List<Favorites> listFavorites = favoritesClient.findByUser(List.class, user.getEmail());
            if (listFavorites.isEmpty()) {
                url = Constant.FAVORITE_PAGE;
                return;
            }
            
            // Loop to add product list into list
            for (Favorites favorites : listFavorites) {
                // Get set of clothes from favorite
                List<Products> setOfClothes = new ArrayList<>();
                Products layer = favorites.getLayerId();
                if (layer != null) {
                    setOfClothes.add(productsClient.find_XML(Products.class, String.valueOf(favorites.getLayerId().getId())));
                }
                setOfClothes.add(productsClient.find_XML(Products.class, String.valueOf(favorites.getTopId().getId())));
                setOfClothes.add(productsClient.find_XML(Products.class, String.valueOf(favorites.getBottomId().getId())));
                listSetOfClothes.add(setOfClothes);
            }
            
            request.setAttribute("LISTSETOFCLOTHES", listSetOfClothes);
            url = Constant.FAVORITE_PAGE;
        } catch (Exception e) {
            log("ERROR at FavoritesController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
