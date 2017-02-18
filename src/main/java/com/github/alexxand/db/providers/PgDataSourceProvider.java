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
    public PgDataSourceProvider(@Named("postgres.user") String user,
                                @Named("postgres.password") String password,
                                @Named("postgres.db.name") String dbName,
                                @Named("postgres.max.connections") int maxConnections,
                                @Named("postgres.init.connections") int initConnections){
        ds = new PGPoolingDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(dbName);
        ds.setMaxConnections(maxConnections);
        ds.setInitialConnections(initConnections);
    }
}