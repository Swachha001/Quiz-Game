package com.tangailquiz.controller;

import com.tangailquiz.dao.QuestionDAO;
import com.tangailquiz.util.QuizUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/questions")
public class QuestionListServlet extends BaseServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = QuizUtil.clean(request.getParameter("q"));
        String category = QuizUtil.clean(request.getParameter("category"));
        String difficulty = QuizUtil.clean(request.getParameter("difficulty"));

        try {
            request.setAttribute("questions", questionDAO.search(keyword, category, difficulty));
            request.setAttribute("q", keyword);
            request.setAttribute("category", category);
            request.setAttribute("difficulty", difficulty);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/questions.jsp").forward(request, response);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
