package com.cspgadmin.cspg_usb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conexiondb {
    private static final String URL = "jdbc:postgresql://localhost:5432/cspgDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "axiss";
    private static Connection connection = null;

    public static Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexion a la base de datos establecida.");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }
}
