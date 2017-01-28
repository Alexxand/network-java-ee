package com.github.alexxand.listeners;

import com.github.alexxand.filters.CharsetFilter;
import com.github.alexxand.filters.LocaleFilter;
import com.github.alexxand.filters.ToLoginFilter;
import com.github.alexxand.controllers.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import javax.servlet.annotation.WebListener;

@WebListener
public class GuiceConfig extends GuiceServletContextListener{

    private static class ServletConfigModule extends ServletModule {
        @Override
        protected void configureServlets() {
            filter("/*").through(CharsetFilter.class);
            filter("/*").through(LocaleFilter.class);
            filter("/*").through(ToLoginFilter.class);

            serve("/login").with(LoginPageController.class);
            serve("/reg").with(RegPageController.class);
            serve("/edit").with(EditPageController.class);
            serve("/settings").with(SettingsPageController.class);
            serve("/contacts").with(ContactsPageController.class);
            serve("/messages").with(MessagesPageController.class);
            serve("/mailing").with(MailingPageController.class);
            serve("/events").with(EventsPageController.class);
            //serve("/*").with(ProfilePagesController.class);
        }
    }
    protected Injector getInjector() {
        return Guice.createInjector(new ServletConfigModule());
    }
}
