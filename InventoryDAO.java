import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * InventoryDAO.java
 * (DAO = "Data Access Object" — a class whose only job is talking to the database)
 *
 * Every method here does ONE thing:
 *  - opens a connection (using DBConnection)
 *  - runs one SQL statement
 *  - closes everything with try-with-resources (so we never leak connections)
 *
 * The rest of the app (InventoryApp.java) never writes SQL directly —
 * it just calls these methods.
 */
public class InventoryDAO {

    // ---------- CREATE ----------
    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (name, category, quantity, price, low_stock_threshold) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getName());
            stmt.setString(2, p.getCategory());
            stmt.setInt(3, p.getQuantity());
            stmt.setDouble(4, p.getPrice());
            stmt.setInt(5, p.getLowStockThreshold());

            stmt.executeUpdate();
        }
    }

    // ---------- READ (all products) ----------
    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    // ---------- READ (one product by id) ----------
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null; // no product found with that id
    }

    // ---------- UPDATE (stock quantity) ----------
    public boolean updateStock(int id, int newQuantity) throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // true if a product with that id existed
        }
    }

    // ---------- DELETE ----------
    public boolean deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // ---------- REPORT: low stock / out of stock products ----------
    public List<Product> getLowStockProducts() throws SQLException {
        String sql = "SELECT * FROM products WHERE quantity <= low_stock_threshold ORDER BY quantity";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    // Small helper so we don't repeat this ResultSet-reading code in every method above
    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getInt("quantity"),
                rs.getDouble("price"),
                rs.getInt("low_stock_threshold")
        );
    }
}
