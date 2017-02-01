package com.github.alexxand.controller.filters;


import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class CharsetFilter extends HttpFilter {
    private String baseEncoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        baseEncoding = "UTF-8";
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(baseEncoding);
        response.setCharacterEncoding(baseEncoding);
        chain.doFilter(request,response);
    }

}
