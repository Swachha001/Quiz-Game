package com.tangailquiz.controller;

import com.tangailquiz.dao.QuestionDAO;
import com.tangailquiz.model.Question;
import com.tangailquiz.util.QuestionForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/questions/edit")
public class QuestionEditServlet extends BaseServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseId(request);
        if (id <= 0) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That question id is not valid.");
            return;
        }

        try {
            Question question = questionDAO.findById(id);
            if (question == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That question was not found.");
                return;
            }
            request.setAttribute("mode", "edit");
            request.setAttribute("question", question);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/question-form.jsp").forward(request, response);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseId(request);
        if (id <= 0) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That question id is not valid.");
            return;
        }

        Question question = QuestionForm.fromRequest(request);
        question.setId(id);
        String error = QuestionForm.validate(question);
        if (error != null) {
            request.setAttribute("mode", "edit");
            request.setAttribute("question", question);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/question-form.jsp").forward(request, response);
            return;
        }

        try {
            if (questionDAO.findById(id) == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That question was not found.");
                return;
            }
            questionDAO.update(question);
            response.sendRedirect(request.getContextPath() + "/admin/questions");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }

    private int parseId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
