package com.github.alexxand.controller.servlets.pages;

import com.github.alexxand.model.Credentials;
import com.github.alexxand.model.validation.FormValidation;
import com.github.alexxand.model.validation.Validator;
import com.github.alexxand.service.LoginService;
import com.github.alexxand.service.ManagerService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.zip.CRC32;

@Singleton
public class LoginPageController extends HttpServlet{

    private final LoginService loginService;

    @Inject
    public LoginPageController(LoginService loginService, Validator validator) {
        this.loginService = loginService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean logIn = (Boolean) session.getAttribute("logIn");
        if (logIn)
            resp.sendRedirect(req.getContextPath());
        else
            req.getRequestDispatcher("WEB-INF/login/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Credentials credentials = Credentials.builder().
                email(req.getParameter("email")).
                password(req.getParameter("password")).
                build();
        if (!loginService.check(credentials)){
            FormValidation validation = new FormValidation();
            validation.getErrors().put("INVALID_CREDENTIALS",true);
            req.setAttribute("validation", validation);
            req.setAttribute("data",credentials);
            req.getServletContext().getRequestDispatcher("/WEB-INF/login/index.jsp").forward(req, resp);
        } else{
            int id = loginService.getId(credentials);
            //login
            resp.sendRedirect(req.getContextPath() + "/profile");
        }
    }
}
