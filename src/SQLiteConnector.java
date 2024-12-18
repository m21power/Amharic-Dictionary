import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnector {
    private static final String DB_URL = "jdbc:sqlite:ahun.sqlite"; // Path to our database file

    // Establish a database connection
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC Driver
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to the database successfully!");
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
}
