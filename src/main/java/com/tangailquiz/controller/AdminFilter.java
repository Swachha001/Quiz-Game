package com.tangailquiz.controller;

import com.tangailquiz.util.SessionKeys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Blocks the admin pages unless the user has logged in.
 * Login and logout URLs are allowed through.
 */
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        if (uri.endsWith("/admin/login") || uri.endsWith("/admin/logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && Boolean.TRUE.equals(session.getAttribute(SessionKeys.ADMIN_LOGGED_IN));
        if (!loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
