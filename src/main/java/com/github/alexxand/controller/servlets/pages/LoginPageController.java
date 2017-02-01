package com.github.alexxand.controller.servlets.pages;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class LoginPageController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean logIn = (Boolean) session.getAttribute("logIn");
        if (logIn)
            resp.sendRedirect(req.getContextPath());
        else
            req.getRequestDispatcher("WEB-INF/login/index.jsp").forward(req, resp);
    }
}
