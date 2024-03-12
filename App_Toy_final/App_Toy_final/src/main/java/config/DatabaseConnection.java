package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static String url = "jdbc:mysql://localhost:3306/toy_store";
    public static String username = "root";
    public static String password = "";
    private static Connection connection;

    public static Connection getConnection() {
      try {
        if (connection == null) {

          connection = DriverManager.getConnection(url, username, password);
        }
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
      return connection;
    }
}
