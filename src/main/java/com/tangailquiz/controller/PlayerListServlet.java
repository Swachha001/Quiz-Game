package com.tangailquiz.controller;

import com.tangailquiz.dao.PlayerDAO;
import com.tangailquiz.util.QuizUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/players")
public class PlayerListServlet extends BaseServlet {

    private final PlayerDAO playerDAO = new PlayerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = QuizUtil.clean(request.getParameter("q"));
        try {
            request.setAttribute("players", playerDAO.search(keyword));
            request.setAttribute("q", keyword);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/players.jsp").forward(request, response);
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
