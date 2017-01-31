package com.github.alexxand.listeners;

import com.github.alexxand.db.ManagerDAO;
import com.github.alexxand.db.postgres.PgManagerDAOImpl;
import com.github.alexxand.db.providers.PgDataSourceProvider;
import com.github.alexxand.exceptions.ResourceNotLoadedException;
import com.github.alexxand.filters.CharsetFilter;
import com.github.alexxand.filters.LocaleFilter;
import com.github.alexxand.filters.ToLoginFilter;
import com.github.alexxand.controllers.*;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class GuiceConfig extends GuiceServletContextListener{

    private static class DbModule extends AbstractModule {

        //TODO it's better to put it in the separate class
        private static Properties getProp(String fileName){
            final Properties prop = new Properties();
            try(InputStream stream = DbModule.class.getClassLoader().getResourceAsStream(fileName)) {
                if (stream == null)
                    throw new ResourceNotLoadedException("Resource " + fileName + " wasn't found");
                prop.load(stream);
            } catch (IOException e){
                throw new ResourceNotLoadedException("Resource couldn't be loaded",e);
            }
            return prop;
        }

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
            serve("/reg/add-position").with(RegPageController.class);
            serve("/reg/add-photo").with(RegPageController.class);
            serve("/edit").with(EditPageController.class);
            serve("/contacts").with(ContactsPageController.class);
            serve("/messages").with(MessagesPageController.class);
            serve("/mailing").with(MailingPageController.class);
            serve("/events").with(EventsPageController.class);
            //serve("/*").with(ProfilePagesController.class);
        }
    }
    protected Injector getInjector() {
        return Guice.createInjector(new ServletConfigModule(), new DbModule());
    }
}
