package com.github.alexxand.controller.servlets.pages;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RegPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        int lastSlashIndex = servletPath.lastIndexOf("/");
        String action = servletPath.substring(lastSlashIndex);
        if (action.equals("") || lastSlashIndex == 0)
            action = "/";
        switch (action) {
            case "/":
                req.getServletContext().getRequestDispatcher("/WEB-INF/reg/index.jsp").forward(req, resp);
                break;
            case "/add-position":
                req.getServletContext().getRequestDispatcher("/WEB-INF/reg/addPosition.jsp").forward(req, resp);
                break;
            case "/add-photo":
                req.getServletContext().getRequestDispatcher("/WEB-INF/reg/addPhoto.jsp").forward(req, resp);
                break;
        }
    }
}