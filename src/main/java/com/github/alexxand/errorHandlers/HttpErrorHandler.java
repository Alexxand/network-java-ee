package com.github.alexxand.errorHandlers;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HttpErrorHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
       /*Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        System.out.println("HttpError");
        System.out.println(statusCode);*/
        if (statusCode == 404){
            req.getRequestDispatcher("/WEB-INF/errors/404.jsp").forward(req,resp);
        }
        if (statusCode == 500){
            //log exception with fatal before sending a request
            req.getRequestDispatcher("/WEB-INF/errors/500.jsp").forward(req,resp);
        }
    }
}
