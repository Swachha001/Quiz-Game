package com.tangailquiz.controller;

import com.tangailquiz.dao.QuestionDAO;
import com.tangailquiz.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/questions/toggle")
public class QuestionToggleServlet extends BaseServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Question question = questionDAO.findById(id);
            if (question == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That question was not found.");
                return;
            }
            questionDAO.setActive(id, !question.isActive());
            response.sendRedirect(request.getContextPath() + "/admin/questions");
        } catch (NumberFormatException e) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That question id is not valid.");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
