package com.tangailquiz.controller;

import com.tangailquiz.dao.QuizAnswerDAO;
import com.tangailquiz.dao.QuizAttemptDAO;
import com.tangailquiz.model.QuizAnswer;
import com.tangailquiz.model.QuizAttempt;
import com.tangailquiz.util.QuizUtil;
import com.tangailquiz.util.SessionKeys;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/quiz/result")
public class ResultServlet extends BaseServlet {

    private final QuizAttemptDAO attemptDAO = new QuizAttemptDAO();
    private final QuizAnswerDAO answerDAO = new QuizAnswerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer attemptId = session == null ? null : (Integer) session.getAttribute(SessionKeys.LAST_ATTEMPT_ID);
        if (attemptId == null) {
            response.sendRedirect(request.getContextPath() + "/quiz/start");
            return;
        }

        try {
            QuizAttempt attempt = attemptDAO.findById(attemptId);
            if (attempt == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That quiz result was not found.");
                return;
            }
            List<QuizAnswer> answers = answerDAO.findByAttemptId(attemptId);
            request.setAttribute("attempt", attempt);
            request.setAttribute("answers", answers);
            request.setAttribute("message", QuizUtil.messageForScore(attempt.getPercentage()));
            request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
