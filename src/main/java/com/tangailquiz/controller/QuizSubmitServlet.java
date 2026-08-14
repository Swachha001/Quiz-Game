package com.tangailquiz.controller;

import com.tangailquiz.dao.DBConnection;
import com.tangailquiz.dao.QuizAnswerDAO;
import com.tangailquiz.dao.QuizAttemptDAO;
import com.tangailquiz.model.Question;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Marks the quiz on the server, then saves the attempt and each answer.
 */
@WebServlet("/quiz/submit")
public class QuizSubmitServlet extends BaseServlet {

    private final QuizAttemptDAO attemptDAO = new QuizAttemptDAO();
    private final QuizAnswerDAO answerDAO = new QuizAnswerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/quiz");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/quiz/start");
            return;
        }

        Integer playerId = (Integer) session.getAttribute(SessionKeys.PLAYER_ID);
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute(SessionKeys.QUIZ_QUESTIONS);
        Timestamp startedAt = (Timestamp) session.getAttribute(SessionKeys.QUIZ_START_TIME);

        if (playerId == null || questions == null || questions.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/quiz/start");
            return;
        }

        int correctCount = 0;
        QuizAnswer[] answers = new QuizAnswer[questions.size()];

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selected = QuizUtil.clean(request.getParameter("answer_" + question.getId())).toUpperCase();
            if (!QuizUtil.isOption(selected)) {
                selected = null;
            }

            boolean isCorrect = selected != null && selected.equals(question.getCorrectOption());
            if (isCorrect) {
                correctCount++;
            }

            QuizAnswer answer = new QuizAnswer();
            answer.setQuestionId(question.getId());
            answer.setSelectedOption(selected);
            answer.setCorrectOption(question.getCorrectOption());
            answer.setCorrect(isCorrect);
            answers[i] = answer;
        }

        int total = questions.size();
        int wrongCount = total - correctCount;
        int score = correctCount * 10;
        double percentage = total == 0 ? 0 : (correctCount * 100.0) / total;

        QuizAttempt attempt = new QuizAttempt();
        attempt.setPlayerId(playerId);
        attempt.setTotalQuestions(total);
        attempt.setCorrectAnswers(correctCount);
        attempt.setWrongAnswers(wrongCount);
        attempt.setScore(score);
        attempt.setPercentage(percentage);
        attempt.setStartedAt(startedAt);

        // Save the attempt and all 10 answers together.
        // If one insert fails, nothing is saved (rollback).
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int attemptId = attemptDAO.insert(conn, attempt);
                for (QuizAnswer answer : answers) {
                    answer.setAttemptId(attemptId);
                    answerDAO.insert(conn, answer);
                }
                conn.commit();

                session.removeAttribute(SessionKeys.QUIZ_QUESTIONS);
                session.removeAttribute(SessionKeys.QUIZ_START_TIME);
                session.removeAttribute(SessionKeys.PLAYER_ID);
                session.setAttribute(SessionKeys.LAST_ATTEMPT_ID, attemptId);

                response.sendRedirect(request.getContextPath() + "/quiz/result");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
