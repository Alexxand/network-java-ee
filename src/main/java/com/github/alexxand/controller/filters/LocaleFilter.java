package com.github.alexxand.controller.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class LocaleFilter extends HttpFilter {

    private static String firstSuitableLanguage(String acceptLanguage){
        String[] locales = acceptLanguage.split("(;.*?(,|$))|,");
        for(String locale : locales){
            String localeLanguage = locale.substring(0,locale.indexOf("-"));
            switch(localeLanguage){
                case "en":
                case "ru":
                    return localeLanguage;
            }
        }

        return null;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String sessionLocale = (String) session.getAttribute("locale");
        String locale = request.getParameter("locale");

        boolean localeIsNotSelected = locale == null;
        boolean localeIsNotSpecified = sessionLocale == null;

        if (localeIsNotSelected && localeIsNotSpecified) {
            String acceptLocales = request.getHeader("Accept-Language");
            if((locale = firstSuitableLanguage(acceptLocales)) == null )
                locale = "en";
        }

        if (locale != null) {
            session.setAttribute("locale", locale);
        }

        chain.doFilter(request, response);
    }


}
