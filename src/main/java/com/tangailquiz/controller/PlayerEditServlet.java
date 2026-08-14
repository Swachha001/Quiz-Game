package com.tangailquiz.controller;

import com.tangailquiz.dao.PlayerDAO;
import com.tangailquiz.dao.QuizAttemptDAO;
import com.tangailquiz.model.Player;
import com.tangailquiz.util.QuizUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/players/edit")
public class PlayerEditServlet extends BaseServlet {

    private final PlayerDAO playerDAO = new PlayerDAO();
    private final QuizAttemptDAO attemptDAO = new QuizAttemptDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Player player = playerDAO.findById(id);
            if (player == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That player was not found.");
                return;
            }
            request.setAttribute("player", player);
            request.setAttribute("attempts", attemptDAO.findByPlayerId(id));
            request.getRequestDispatcher("/WEB-INF/jsp/admin/player-form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That player id is not valid.");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Player player = playerDAO.findById(id);
            if (player == null) {
                showError(request, response, HttpServletResponse.SC_NOT_FOUND, "That player was not found.");
                return;
            }

            String name = QuizUtil.clean(request.getParameter("name"));
            String email = QuizUtil.clean(request.getParameter("email"));
            String phone = QuizUtil.clean(request.getParameter("phone"));
            if (QuizUtil.isBlank(name)) {
                request.setAttribute("error", "Name is required.");
                player.setName(name);
                player.setEmail(email);
                player.setPhone(phone);
                request.setAttribute("player", player);
                request.setAttribute("attempts", attemptDAO.findByPlayerId(id));
                request.getRequestDispatcher("/WEB-INF/jsp/admin/player-form.jsp").forward(request, response);
                return;
            }

            player.setName(name);
            player.setEmail(email);
            player.setPhone(phone);
            playerDAO.update(player);
            response.sendRedirect(request.getContextPath() + "/admin/players");
        } catch (NumberFormatException e) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That player id is not valid.");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
