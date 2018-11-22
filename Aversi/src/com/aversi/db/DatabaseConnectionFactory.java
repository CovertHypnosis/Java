package com.aversi.db;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionFactory {
    private static DatabaseConnectionFactory conFactory = new DatabaseConnectionFactory();

    private DataSource datasource = null;

    private DatabaseConnectionFactory() {}

    public static DatabaseConnectionFactory getConFactory() {
        return conFactory;
    }

    public synchronized void init() throws IOException {
        if (datasource != null)
            return;

        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");
        Properties dbProperties = new Properties();
        dbProperties.load(inStream);
        inStream.close();

        PoolProperties p = new PoolProperties();
        p.setUrl(dbProperties.getProperty("db_url"));
        p.setDriverClassName(dbProperties.getProperty("db_driver_class_name"));
        p.setUsername(dbProperties.getProperty("db_user"));
        p.setPassword(dbProperties.getProperty("db_pass"));
        p.setMaxActive(100);
        p.setMaxIdle(10);

        datasource = new DataSource();
        datasource.setPoolProperties(p);
    }

    // return connection to db
    public Connection getConnection() throws SQLException {
        if (datasource == null)
            throw new SQLException("Error initializing datasource");
        return datasource.getConnection();
    }
}
