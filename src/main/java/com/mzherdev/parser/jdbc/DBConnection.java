package com.mzherdev.parser.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by mzherdev on 27.09.16.
 */
public class DBConnection {

    public static Connection getConnection() {
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        Connection connection = null;
        try {
            inputStream = new FileInputStream("src/main/resources/jdbc.properties");
            properties.load(inputStream);
            Class.forName(properties.getProperty("database.jdbc.driver"));
            connection = DriverManager.getConnection(properties.getProperty("database.jdbc.url"),
                    properties.getProperty("database.jdbc.username"),
                    properties.getProperty("database.jdbc.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
