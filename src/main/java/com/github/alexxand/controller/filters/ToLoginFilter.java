package com.github.alexxand.controller.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class ToLoginFilter extends HttpFilter {

    private static boolean isGoodForRedirect(String path){
        boolean isNotLogin = !path.equals("/login");
        boolean isNotReg = !path.startsWith("/reg");
        boolean isNotWebjars = !path.startsWith("/webjars");
        boolean isNotCSS = !path.startsWith("/css");
        return isNotLogin && isNotReg && isNotWebjars && isNotCSS;
     }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        boolean logIn = (Boolean) session.getAttribute("logIn");
        if (isGoodForRedirect(request.getServletPath()) && !logIn)
            response.sendRedirect(request.getContextPath() + "/login");
        else
            chain.doFilter(request,response);
    }

}
