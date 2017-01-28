package com.github.alexxand.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class ToLoginFilter extends HttpFilter {

    private boolean isGoodForRedirect(String path){
        boolean isNotLogin = !path.equals("/login");
        //[0] --> empty string
        String root = path.split("/")[1];
        boolean isNotWebjars = !root.equals("webjars");
        return isNotLogin && isNotWebjars;
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
