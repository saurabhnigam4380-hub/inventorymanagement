/**
 * Product.java
 *
 * This is a plain "model" class. It doesn't talk to the database directly —
 * it just holds the data for ONE product (one row from the products table).
 * We pass Product objects around between the DAO (database code) and the
 * main app (menu code).
 */
public class Product {

    private int id;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private int lowStockThreshold;

    // Constructor used when creating a NEW product (no id yet, database assigns it)
    public Product(String name, String category, int quantity, double price, int lowStockThreshold) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.lowStockThreshold = lowStockThreshold;
    }

    // Constructor used when reading an EXISTING product back from the database (id is known)
    public Product(int id, String name, String category, int quantity, double price, int lowStockThreshold) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.lowStockThreshold = lowStockThreshold;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }

    // Handy for printing a product in a readable row format
    @Override
    public String toString() {
        return String.format("ID: %-4d | %-20s | %-15s | Qty: %-6d | Price: $%-8.2f | Reorder below: %d",
                id, name, category, quantity, price, lowStockThreshold);
    }
}
