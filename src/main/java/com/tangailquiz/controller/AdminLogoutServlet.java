package com.tangailquiz.controller;

import com.tangailquiz.util.SessionKeys;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/logout")
public class AdminLogoutServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SessionKeys.ADMIN_LOGGED_IN);
        }
        response.sendRedirect(request.getContextPath() + "/admin/login");
    }
}
