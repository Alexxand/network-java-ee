package com.github.alexxand.controller.listeners;

import com.github.alexxand.controller.servlets.pages.*;
import com.github.alexxand.db.ManagerDAO;
import com.github.alexxand.db.postgres.PgManagerDAOImpl;
import com.github.alexxand.db.providers.PgDataSourceProvider;
import com.github.alexxand.controller.filters.CharsetFilter;
import com.github.alexxand.controller.filters.LocaleFilter;
import com.github.alexxand.controller.filters.ToLoginFilter;
import com.github.alexxand.model.validation.BasicValidator;
import com.github.alexxand.model.validation.Validator;
import com.github.alexxand.service.LoginService;
import com.github.alexxand.service.ManagerService;
import com.github.alexxand.service.SecurityService;
import com.github.alexxand.service.impl.BasicLoginService;
import com.github.alexxand.service.impl.BasicManagerService;
import com.github.alexxand.service.impl.SCryptSecurityService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.Properties;

import static com.github.alexxand.utils.BasicUtils.getProp;

@WebListener
public class GuiceConfig extends GuiceServletContextListener{

    private static class ValidationModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Validator.class).to(BasicValidator.class).in(Singleton.class);
        }
    }

    private static class ServiceModule extends AbstractModule{

        @Override
        protected void configure() {
            Properties dbConfig = getProp("security/cfg/scrypt.properties");
            Names.bindProperties(binder(), dbConfig);
            bind(SecurityService.class).to(SCryptSecurityService.class).in(Singleton.class);
            bind(ManagerService.class).to(BasicManagerService.class).in(Singleton.class);
            bind(LoginService.class).to(BasicLoginService.class).in(Singleton.class);
        }
    }

    private static class DbModule extends AbstractModule {

        @Override
        protected void configure() {
            Properties dbConfig = getProp("db/cfg/postgres.properties");
            Names.bindProperties(binder(),dbConfig);
            bind(DataSource.class).toProvider(PgDataSourceProvider.class).in(Singleton.class);
            bind(ManagerDAO.class).to(PgManagerDAOImpl.class).in(Singleton.class);
        }
    }

    private static class ServletConfigModule extends ServletModule {
        @Override
        protected void configureServlets() {
            filter("/*").through(CharsetFilter.class);
            filter("/*").through(LocaleFilter.class);
            filter("/*").through(ToLoginFilter.class);

            serve("/login").with(LoginPageController.class);
            serve("/reg").with(RegPageController.class);
            serve("/reg/company").with(RegPageController.class);
            serve("/reg/photo").with(RegPageController.class);
            serve("/edit").with(EditPageController.class);
            serve("/contacts").with(ContactsPageController.class);
            serve("/mailing").with(MailingPageController.class);
            serve("/events").with(EventsPageController.class);
            serve("/profile").with(ProfilePageController.class);
        }
    }
    protected Injector getInjector() {
        return Guice.createInjector(new ServletConfigModule(),
                new DbModule(),
                new ServiceModule(),
                new ValidationModule());
    }
}
