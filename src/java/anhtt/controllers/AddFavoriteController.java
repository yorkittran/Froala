/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.controllers;

import anhtt.clients.FavoritesClient;
import anhtt.config.Constant;
import anhtt.dtos.Favorites;
import anhtt.dtos.Products;
import anhtt.dtos.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Yorkit Tran
 */
public class AddFavoriteController extends HttpServlet {

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
            // Check if user is login 
            HttpSession session = request.getSession(false);  
            Users user = (Users) session.getAttribute("USER");
            if (user == null) {
                request.getRequestDispatcher(Constant.LOGIN_PAGE).forward(request, response);
            }
            
            // Get params
            Products topId = new Products(Integer.parseInt(request.getParameter("topId")));
            Products bottomId = new Products(Integer.parseInt(request.getParameter("bottomId")));
            Favorites favorites;
            Products layerId;
            
            // Check if there is layer item or not
            if (request.getParameter("layerId") != null) {
                layerId = new Products(Integer.parseInt(request.getParameter("layerId")));
                favorites = new Favorites(0, layerId, topId, bottomId, user);
            } else {
                favorites = new Favorites(0, topId, bottomId, user);
            }
            FavoritesClient favoritesClient = new FavoritesClient();
            favoritesClient.create_XML(favorites);
            
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Added to favorite");
        } catch (Exception e) {
            log("ERROR at AddFavoriteController: " + e.getMessage());
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
