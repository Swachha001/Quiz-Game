package com.tangailquiz.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Shared helpers for all servlets.
 */
public abstract class BaseServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(BaseServlet.class.getName());

    protected void showError(HttpServletRequest request, HttpServletResponse response, int status, String message)
            throws ServletException, IOException {
        response.setStatus(status);
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorCode", status);
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }

    protected void handleDatabaseError(HttpServletRequest request, HttpServletResponse response, SQLException e)
            throws ServletException, IOException {
        LOG.log(Level.SEVERE, "Database error", e);
        showError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Could not talk to the database. Check that MySQL is running and db.properties is correct.");
    }
}
