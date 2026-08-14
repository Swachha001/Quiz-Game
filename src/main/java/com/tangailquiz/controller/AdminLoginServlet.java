package com.tangailquiz.controller;

import com.tangailquiz.util.AppConfig;
import com.tangailquiz.util.QuizUtil;
import com.tangailquiz.util.SessionKeys;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && Boolean.TRUE.equals(session.getAttribute(SessionKeys.ADMIN_LOGGED_IN))) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = QuizUtil.clean(request.getParameter("username"));
        String password = QuizUtil.clean(request.getParameter("password"));

        String expectedUser = AppConfig.get("admin.username");
        String expectedPass = AppConfig.get("admin.password");

        if (username.equals(expectedUser) && password.equals(expectedPass)) {
            request.getSession(true).setAttribute(SessionKeys.ADMIN_LOGGED_IN, Boolean.TRUE);
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        request.setAttribute("error", "Wrong username or password.");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
    }
}
