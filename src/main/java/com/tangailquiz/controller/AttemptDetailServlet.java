package com.tangailquiz.controller;

import com.tangailquiz.dao.QuizAnswerDAO;
import com.tangailquiz.dao.QuizAttemptDAO;
import com.tangailquiz.model.QuizAttempt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/attempts/view")
public class AttemptDetailServlet extends BaseServlet {

    private final QuizAttemptDAO attemptDAO = new QuizAttemptDAO();
    private final QuizAnswerDAO answerDAO = new QuizAnswerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            QuizAttempt attempt = attemptDAO.findById(id);
            if (attempt == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That quiz attempt was not found.");
                return;
            }
            request.setAttribute("attempt", attempt);
            request.setAttribute("answers", answerDAO.findByAttemptId(id));
            request.getRequestDispatcher("/WEB-INF/jsp/admin/attempt-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That attempt id is not valid.");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
