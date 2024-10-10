package org.maple.tallerprogramacion.ServerConnectionTests;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDBConnection {

    // Cambia el nombre de la clase y el paquete para que coincidan con MySQL
    Dotenv dotenv = Dotenv.load();
    String db = dotenv.get("DB_NAME");
    String url = dotenv.get("DB_URL");
    String user = dotenv.get("DB_USER");
    String pass = dotenv.get("DB_PASSWORD");

    public Connection getConnection() {
        Connection conn = null;
        try {
            // Cargar el driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Propiedades de conexión
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.user);
            connectionProps.put("password", this.pass);

            // Establecer la conexión
            conn = DriverManager.getConnection(this.url, connectionProps);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        MySQLDBConnection dbConnection = new MySQLDBConnection();
        Connection conn = dbConnection.getConnection();

        // Aquí puedes usar la conexión para realizar operaciones en la base de datos

        // Cerrar la conexión después de usarla
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
