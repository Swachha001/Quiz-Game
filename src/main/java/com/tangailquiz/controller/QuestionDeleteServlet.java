package com.tangailquiz.controller;

import com.tangailquiz.dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/questions/delete")
public class QuestionDeleteServlet extends BaseServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            questionDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/questions");
        } catch (NumberFormatException e) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That question id is not valid.");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
