package com.tangailquiz.controller;

import com.tangailquiz.dao.PlayerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/players/delete")
public class PlayerDeleteServlet extends BaseServlet {

    private final PlayerDAO playerDAO = new PlayerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            playerDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/players");
        } catch (NumberFormatException e) {
            showError(request, response, HttpServletResponse.SC_BAD_REQUEST, "That player id is not valid.");
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        }
    }
}
