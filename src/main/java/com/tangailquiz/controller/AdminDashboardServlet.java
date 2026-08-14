package com.tangailquiz.controller;

import com.tangailquiz.dao.PlayerDAO;
import com.tangailquiz.dao.QuestionDAO;
import com.tangailquiz.dao.QuizAttemptDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final PlayerDAO playerDAO = new PlayerDAO();
    private final QuizAttemptDAO attemptDAO = new QuizAttemptDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("totalQuestions", questionDAO.countAll());
            request.setAttribute("activeQuestions", questionDAO.countActive());
            request.setAttribute("totalPlayers", playerDAO.countAll());
            request.setAttribute("totalAttempts", attemptDAO.countAll());
            request.setAttribute("averageScore", attemptDAO.averagePercentage());
            request.setAttribute("recentAttempts", attemptDAO.findRecent(5));
            request.setAttribute("topPlayers", attemptDAO.findTopPlayers(5));
            request.setAttribute("topScores", attemptDAO.findHighestScores(5));
            request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
