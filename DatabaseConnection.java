import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/url_db";
    private static final String USER = "root";  // Change this
    private static final String PASSWORD = "#Sumanth123";  // Change this

    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Get a single connection instance
    public static Connection getConnection() {
        if (connection == null) {  // Only create connection if it doesn't exist
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database Connected Successfully!");
            } catch (SQLException e) {
                System.out.println("Database Connection Failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Close connection when application stops
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database Connection Closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
