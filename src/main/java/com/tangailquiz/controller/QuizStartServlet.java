package com.tangailquiz.controller;

import com.tangailquiz.dao.PlayerDAO;
import com.tangailquiz.dao.QuestionDAO;
import com.tangailquiz.model.Player;
import com.tangailquiz.model.Question;
import com.tangailquiz.util.QuizUtil;
import com.tangailquiz.util.SessionKeys;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * GET  = show the player form
 * POST = save the player, pick 10 random questions, put them in the session
 */
@WebServlet("/quiz/start")
public class QuizStartServlet extends BaseServlet {

    private final PlayerDAO playerDAO = new PlayerDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/player.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = QuizUtil.clean(request.getParameter("name"));
        String email = QuizUtil.clean(request.getParameter("email"));
        String phone = QuizUtil.clean(request.getParameter("phone"));

        if (QuizUtil.isBlank(name)) {
            request.setAttribute("error", "Please enter your name.");
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/WEB-INF/jsp/player.jsp").forward(request, response);
            return;
        }

        if (!QuizUtil.isBlank(email) && !email.contains("@")) {
            request.setAttribute("error", "Please enter a valid email, or leave it empty.");
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/WEB-INF/jsp/player.jsp").forward(request, response);
            return;
        }

        try {
            List<Question> questions = questionDAO.findRandomActive(10);
            if (questions.size() < 10) {
                showError(request, response, HttpServletResponse.SC_BAD_REQUEST,
                        "There are not enough active questions to start a quiz. An admin must add or activate questions.");
                return;
            }

            Player player = new Player();
            player.setName(name);
            player.setEmail(email);
            player.setPhone(phone);
            int playerId = playerDAO.insert(player);

            HttpSession session = request.getSession(true);
            session.setAttribute(SessionKeys.PLAYER_ID, playerId);
            session.setAttribute(SessionKeys.PLAYER_NAME, name);
            session.setAttribute(SessionKeys.QUIZ_QUESTIONS, questions);
            session.setAttribute(SessionKeys.QUIZ_START_TIME, Timestamp.from(Instant.now()));
            session.removeAttribute(SessionKeys.LAST_ATTEMPT_ID);

            response.sendRedirect(request.getContextPath() + "/quiz");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
