package org.example.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String url="jdbc:sqlite:prueba.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
