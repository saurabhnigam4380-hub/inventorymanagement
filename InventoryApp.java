import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * InventoryApp.java
 *
 * This is the file with the "main" method — it's the one you actually run.
 * It just shows a text menu, reads what the user types, and calls the
 * matching method on InventoryDAO. It doesn't know anything about SQL.
 */
public class InventoryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryDAO dao = new InventoryDAO();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println(" Welcome to the Inventory Management System");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addProduct(); break;
                case "2": updateStock(); break;
                case "3": deleteProduct(); break;
                case "4": checkStockAvailability(); break;
                case "5": generateReport(); break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please enter a number from 1 to 6.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. Add Product");
        System.out.println("2. Update Stock");
        System.out.println("3. Delete Product");
        System.out.println("4. Check Stock Availability");
        System.out.println("5. Generate Report");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    // ---------- 1. Add Product ----------
    private static void addProduct() {
        try {
            System.out.print("Product name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Category: ");
            String category = scanner.nextLine().trim();

            int quantity = readInt("Initial quantity: ");
            double price = readDouble("Price per unit: ");
            int threshold = readInt("Low stock alert threshold (e.g. 5): ");

            Product product = new Product(name, category, quantity, price, threshold);
            dao.addProduct(product);

            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Database error while adding product: " + e.getMessage());
        }
    }

    // ---------- 2. Update Stock ----------
    private static void updateStock() {
        try {
            int id = readInt("Enter the Product ID to update: ");

            Product existing = dao.getProductById(id);
            if (existing == null) {
                System.out.println("No product found with ID " + id);
                return;
            }

            System.out.println("Current details -> " + existing);
            int newQuantity = readInt("Enter new quantity: ");

            boolean success = dao.updateStock(id, newQuantity);
            if (success) {
                System.out.println("Stock updated successfully!");
            } else {
                System.out.println("Update failed — product may have been deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while updating stock: " + e.getMessage());
        }
    }

    // ---------- 3. Delete Product ----------
    private static void deleteProduct() {
        try {
            int id = readInt("Enter the Product ID to delete: ");

            Product existing = dao.getProductById(id);
            if (existing == null) {
                System.out.println("No product found with ID " + id);
                return;
            }

            System.out.println("You are about to delete -> " + existing);
            System.out.print("Are you sure? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y")) {
                boolean success = dao.deleteProduct(id);
                System.out.println(success ? "Product deleted." : "Delete failed.");
            } else {
                System.out.println("Delete cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while deleting product: " + e.getMessage());
        }
    }

    // ---------- 4. Check Stock Availability ----------
    private static void checkStockAvailability() {
        try {
            int id = readInt("Enter the Product ID to check: ");
            Product product = dao.getProductById(id);

            if (product == null) {
                System.out.println("No product found with ID " + id);
                return;
            }

            System.out.println(product);
            if (product.getQuantity() == 0) {
                System.out.println("Status: OUT OF STOCK");
            } else if (product.getQuantity() <= product.getLowStockThreshold()) {
                System.out.println("Status: LOW STOCK — consider reordering soon.");
            } else {
                System.out.println("Status: IN STOCK");
            }
        } catch (SQLException e) {
            System.out.println("Database error while checking stock: " + e.getMessage());
        }
    }

    // ---------- 5. Generate Report ----------
    private static void generateReport() {
        try {
            List<Product> allProducts = dao.getAllProducts();

            System.out.println("\n========== INVENTORY REPORT ==========");

            if (allProducts.isEmpty()) {
                System.out.println("No products in the system yet.");
                return;
            }

            int totalItems = 0;
            double totalValue = 0.0;

            System.out.println("\n-- All Products --");
            for (Product p : allProducts) {
                System.out.println(p);
                totalItems += p.getQuantity();
                totalValue += p.getQuantity() * p.getPrice();
            }

            System.out.println("\n-- Summary --");
            System.out.println("Total distinct products : " + allProducts.size());
            System.out.println("Total items in stock     : " + totalItems);
            System.out.printf("Total inventory value    : $%.2f%n", totalValue);

            List<Product> lowStock = dao.getLowStockProducts();
            System.out.println("\n-- Low Stock / Out of Stock Alerts --");
            if (lowStock.isEmpty()) {
                System.out.println("None — everything is above its reorder threshold.");
            } else {
                for (Product p : lowStock) {
                    String status = (p.getQuantity() == 0) ? "OUT OF STOCK" : "LOW STOCK";
                    System.out.println(p + "  [" + status + "]");
                }
            }
            System.out.println("=======================================");

        } catch (SQLException e) {
            System.out.println("Database error while generating report: " + e.getMessage());
        }
    }

    // ---------- Small helpers to safely read numbers from the console ----------
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid price (e.g. 9.99).");
            }
        }
    }
}
