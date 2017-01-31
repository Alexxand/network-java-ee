package com.github.alexxand.db.providers;

import com.google.inject.Inject;
import org.postgresql.ds.PGPoolingDataSource;

import javax.inject.Named;
import javax.inject.Provider;
import javax.sql.DataSource;

public class PgDataSourceProvider implements Provider<DataSource> {

    private final PGPoolingDataSource ds;

    @Override
    public DataSource get() {
        return ds;
    }

    @Inject
    public PgDataSourceProvider(@Named("postres.user") String user,
                                @Named("postres.password") String password,
                                @Named("postres.db.name") String dbName,
                                @Named("postres.max.connections") int maxConnections,
                                @Named("postres.init.connections") int initConnections){
        ds = new PGPoolingDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(dbName);
        ds.setMaxConnections(maxConnections);
        ds.setInitialConnections(initConnections);
    }
}