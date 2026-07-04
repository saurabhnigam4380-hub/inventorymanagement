import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 *
 * This is the ONLY place in the whole project that knows how to connect
 * to MySQL. Every other file asks THIS class for a connection instead of
 * connecting on their own. That way, if your database URL/username/password
 * ever changes, you only need to update it here.
 *
 * >>> EDIT THE THREE LINES BELOW TO MATCH YOUR OWN MYSQL SETUP <<<
 */
public class DBConnection {

    // If MySQL is running on your own computer, "localhost" is correct.
    // "inventory_db" must match the database created in schema.sql
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";

    private static final String USERNAME = "root";        // <-- change if needed
    private static final String PASSWORD = "Sauurabh@4380"; // <-- change this!

    /**
     * Opens and returns a new connection to the database.
     * Every method in InventoryDAO calls this when it needs to talk to MySQL.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
