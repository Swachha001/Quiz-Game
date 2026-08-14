package com.tangailquiz.controller;

import com.tangailquiz.dao.QuizAttemptDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/attempts")
public class AttemptListServlet extends BaseServlet {

    private final QuizAttemptDAO attemptDAO = new QuizAttemptDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("attempts", attemptDAO.findAll());
            request.getRequestDispatcher("/WEB-INF/jsp/admin/attempts.jsp").forward(request, response);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
