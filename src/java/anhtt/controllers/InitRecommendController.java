/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhtt.controllers;

import anhtt.clients.AnswersClient;
import anhtt.clients.QuestionsClient;
import anhtt.config.Constant;
import anhtt.dtos.Answers;
import anhtt.dtos.Questions;
import anhtt.dtos.Users;
import java.io.IOException;
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
public class InitRecommendController extends HttpServlet {

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
                url = Constant.LOGIN_PAGE;
                return;
            }
            
            QuestionsClient questionsClient = new QuestionsClient();
            AnswersClient answersClient = new AnswersClient();
            List<Questions> listQuestions = questionsClient.findAll_XML(List.class);
            List<Answers> listAnswers = answersClient.findAll_XML(List.class);
            request.setAttribute("QUESTIONS", listQuestions);
            request.setAttribute("ANSWERS", listAnswers);
            url = Constant.RECOMMEND_PAGE;
        } catch (Exception e) {
            log("ERROR at InitRecommendController: " + e.getMessage());
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
