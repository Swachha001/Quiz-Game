package com.tangailquiz.controller;

import com.tangailquiz.model.Question;
import com.tangailquiz.util.SessionKeys;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Shows the 10 questions already stored in the session.
 * Refreshing this page must NOT pick a new random set.
 */
@WebServlet("/quiz")
public class QuizServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/quiz/start");
            return;
        }

        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute(SessionKeys.QUIZ_QUESTIONS);
        if (questions == null || questions.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/quiz/start");
            return;
        }

        request.setAttribute("questions", questions);
        request.setAttribute("playerName", session.getAttribute(SessionKeys.PLAYER_NAME));
        request.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp").forward(request, response);
    }
}
