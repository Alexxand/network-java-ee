package com.github.alexxand.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class LocaleFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String sessionLocale = (String) session.getAttribute("locale");
        String locale = request.getParameter("locale");

        if (locale != null || sessionLocale == null) {
            if (locale == null) {
                String acceptLocales = request.getHeader("Accept-Language");
                if (acceptLocales != null)
                    locale = acceptLocales.substring(0, acceptLocales.indexOf("-"));
                else
                    locale = "en";
            }
            session.setAttribute("locale", locale);
        }


        chain.doFilter(request, response);
    }


}
