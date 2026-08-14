package com.tangailquiz.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forward(request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer code = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (code == null) {
            code = 500;
        }
        if (request.getAttribute("errorMessage") == null) {
            if (code == 404) {
                request.setAttribute("errorMessage", "That page was not found.");
            } else {
                request.setAttribute("errorMessage", "Something went wrong. Please try again.");
            }
        }
        request.setAttribute("errorCode", code);
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }
}
