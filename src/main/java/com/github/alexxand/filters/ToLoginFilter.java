package com.github.alexxand.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Singleton
public class ToLoginFilter extends HttpFilter {

    private static boolean isGoodForRedirect(String path){
        boolean isNotLogin = !path.equals("/login");
        boolean isNotReg = !path.startsWith("/reg");
        boolean isNotWebjars = !path.startsWith("/webjars");
        return isNotLogin && isNotReg && isNotWebjars;
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
