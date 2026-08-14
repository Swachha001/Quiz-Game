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

@WebServlet("/admin/questions/create")
public class QuestionCreateServlet extends BaseServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Question question = new Question();
        question.setActive(true);
        question.setCorrectOption("A");
        question.setCategory("Crops & Agriculture");
        question.setDifficulty("Easy");
        request.setAttribute("mode", "create");
        request.setAttribute("question", question);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/question-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Question question = QuestionForm.fromRequest(request);
        String error = QuestionForm.validate(question);
        if (error != null) {
            request.setAttribute("mode", "create");
            request.setAttribute("question", question);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/question-form.jsp").forward(request, response);
            return;
        }

        try {
            questionDAO.insert(question);
            response.sendRedirect(request.getContextPath() + "/admin/questions");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
