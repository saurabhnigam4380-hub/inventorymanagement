import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class InventoryGUI extends JFrame {

    JPanel sidebar;
    JPanel header;
    JPanel content;

    JTable table;

DefaultTableModel model;
    JLabel totalProductsLabel;
    JLabel totalQuantityLabel;
    JLabel lowStockLabel;
    JButton addProductButton;
    JButton updateStockButton;
    JButton deleteProductButton;
    JButton searchButton;
    JButton reportButton;
    JButton exitButton;

    public InventoryGUI() {

        setTitle("Inventory Management System");

        setSize(1200,700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        //---------------- SIDEBAR ----------------

        sidebar = new JPanel();

        sidebar.setPreferredSize(new Dimension(250,700));

        sidebar.setBackground(new Color(240,243,250));

        sidebar.setLayout(new GridLayout(8,1,10,10));

        sidebar.add(createButton("Dashboard"));

        addProductButton = createButton("Add Product");
        sidebar.add(addProductButton);

        updateStockButton = createButton("Update Stock");
        sidebar.add(updateStockButton);

        deleteProductButton = createButton("Delete Product");
        sidebar.add(deleteProductButton);

        searchButton = createButton("Search Product");
        sidebar.add(searchButton);

        reportButton = createButton("Generate Report");
        sidebar.add(reportButton);

        sidebar.add(createButton("Low Stock"));

        exitButton = createButton("Exit");
        sidebar.add(exitButton);

        //---------------- HEADER ----------------

        header = new JPanel();

        header.setPreferredSize(new Dimension(900,100));

        header.setBackground(Color.WHITE);

        header.setLayout(new BorderLayout());

        JLabel title = new JLabel("INVENTORY MANAGEMENT SYSTEM");

        title.setFont(new Font("Arial",Font.BOLD,32));

        title.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        header.add(title,BorderLayout.WEST);

        //---------------- CONTENT ----------------

        content = new JPanel();

        content.setBackground(new Color(248,249,252));

        content.setLayout(new BorderLayout());

        content.add(createDashboard(), BorderLayout.CENTER);

        add(sidebar,BorderLayout.WEST);

        add(header,BorderLayout.NORTH);

        add(content,BorderLayout.CENTER);

        loadProducts();
        
        addProductButton.addActionListener(e -> addProduct());
        updateStockButton.addActionListener(e -> updateStock());
        deleteProductButton.addActionListener(e -> deleteProduct());
        searchButton.addActionListener(e -> searchProduct());
        reportButton.addActionListener(e -> generateReport());
        exitButton.addActionListener(e -> System.exit(0));
        setVisible(true);

    }
        private JPanel createDashboard() {

    JPanel dashboard = new JPanel();

    dashboard.setBackground(new Color(248,249,252));

    dashboard.setLayout(new BorderLayout());

    JLabel heading = new JLabel("Dashboard");

    heading.setFont(new Font("Arial", Font.BOLD,26));

    heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

    dashboard.add(heading, BorderLayout.NORTH);

    JPanel cards = new JPanel();

    cards.setBackground(new Color(248,249,252));

    cards.setLayout(new GridLayout(1,3,20,20));

    cards.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

    cards.add(createCard("Total Products"));

    cards.add(createCard("Total Quantity"));

    cards.add(createCard("Low Stock"));

    JPanel center = new JPanel();

center.setLayout(new BorderLayout());

center.setBackground(new Color(248,249,252));

center.add(cards, BorderLayout.NORTH);

String columns[] = {

        "ID",

        "Product",

        "Category",

        "Quantity",

        "Price"

};

model = new DefaultTableModel(columns,0);

table = new JTable(model);

JScrollPane scrollPane = new JScrollPane(table);

scrollPane.setBorder(BorderFactory.createTitledBorder("Products"));

center.add(scrollPane,BorderLayout.CENTER);

dashboard.add(center,BorderLayout.CENTER);

    return dashboard;

}

private JPanel createCard(String title) {

    JPanel panel = new JPanel();

    panel.setBackground(Color.WHITE);

    panel.setPreferredSize(new Dimension(200,120));

    panel.setLayout(new BorderLayout());

    panel.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),1));

    JLabel titleLabel = new JLabel(title);

    titleLabel.setHorizontalAlignment(JLabel.CENTER);

    titleLabel.setFont(new Font("Arial", Font.BOLD,18));

    titleLabel.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));

    JLabel valueLabel = new JLabel("0");

    valueLabel.setHorizontalAlignment(JLabel.CENTER);

    valueLabel.setFont(new Font("Arial", Font.BOLD,36));

    valueLabel.setForeground(new Color(0,102,204));

    if(title.equals("Total Products"))
        totalProductsLabel = valueLabel;

    else if(title.equals("Total Quantity"))
        totalQuantityLabel = valueLabel;

    else if(title.equals("Low Stock"))
        lowStockLabel = valueLabel;

    panel.add(titleLabel, BorderLayout.NORTH);

    panel.add(valueLabel, BorderLayout.CENTER);

    return panel;
}

private void loadProducts() {

    model.setRowCount(0);

    try {

        InventoryDAO dao = new InventoryDAO();

        int totalProducts = 0;
        int totalQuantity = 0;
        int lowStock = 0;

        for (Product p : dao.getAllProducts()) {

            model.addRow(new Object[] {

                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getQuantity(),
                p.getPrice()

            });

            // Update Statistics
            totalProducts++;
            totalQuantity += p.getQuantity();

            if (p.getQuantity() <= p.getLowStockThreshold()) {
                lowStock++;
            }
        }

        // Update Dashboard Cards
        totalProductsLabel.setText(String.valueOf(totalProducts));
        totalQuantityLabel.setText(String.valueOf(totalQuantity));
        lowStockLabel.setText(String.valueOf(lowStock));

    } catch (Exception e) {

        e.printStackTrace();

    }
}

private void updateStock() {

    try {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(this,
                        "Enter Product ID:"));

        int quantity = Integer.parseInt(
                JOptionPane.showInputDialog(this,
                        "Enter New Quantity:"));

        InventoryDAO dao = new InventoryDAO();

        boolean updated = dao.updateStock(id, quantity);

        if(updated){

            JOptionPane.showMessageDialog(this,
                    "Stock Updated Successfully!");

            loadProducts();

        }
        else{

            JOptionPane.showMessageDialog(this,
                    "Product ID Not Found!");

        }

    }
    catch(Exception e){

        JOptionPane.showMessageDialog(this,
                "Error : " + e.getMessage());

    }

}

private void deleteProduct() {

    try {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog(this,
                        "Enter Product ID to Delete:"));

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            InventoryDAO dao = new InventoryDAO();

            boolean deleted = dao.deleteProduct(id);

            if (deleted) {

                JOptionPane.showMessageDialog(this,
                        "Product Deleted Successfully!");

                loadProducts();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Product ID Not Found!");

            }
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Error : " + e.getMessage());

    }
}

private void generateReport() {

    try {

        InventoryDAO dao = new InventoryDAO();

        int totalProducts = 0;
        int totalQuantity = 0;
        int lowStock = 0;

        for(Product p : dao.getAllProducts()){

            totalProducts++;

            totalQuantity += p.getQuantity();

            if(p.getQuantity() <= p.getLowStockThreshold()){
                lowStock++;
            }

        }

        String report =
                "========== INVENTORY REPORT ==========\n\n" +
                "Total Products : " + totalProducts + "\n" +
                "Total Quantity : " + totalQuantity + "\n" +
                "Low Stock Items : " + lowStock + "\n\n" +
                "====================================";

        JOptionPane.showMessageDialog(
                this,
                report,
                "Inventory Report",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }
}

private void addProduct() {

    try {

        String name = JOptionPane.showInputDialog(this, "Enter Product Name:");

        if (name == null || name.trim().isEmpty())
            return;

        String category = JOptionPane.showInputDialog(this, "Enter Category:");

        if (category == null || category.trim().isEmpty())
            return;

        int quantity = Integer.parseInt(
                JOptionPane.showInputDialog(this, "Enter Quantity:"));

        double price = Double.parseDouble(
                JOptionPane.showInputDialog(this, "Enter Price:"));

        int threshold = Integer.parseInt(
                JOptionPane.showInputDialog(this, "Enter Low Stock Threshold:"));

        Product product = new Product(
                0,
                name,
                category,
                quantity,
                price,
                threshold
        );

        InventoryDAO dao = new InventoryDAO();

        dao.addProduct(product);

        JOptionPane.showMessageDialog(this,
                "Product Added Successfully!");

        loadProducts();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Error : " + e.getMessage());

    }

}
private void searchProduct() {

    try {

        String keyword = JOptionPane.showInputDialog(
                this,
                "Enter Product Name:");

        if(keyword == null || keyword.trim().isEmpty())
            return;

        InventoryDAO dao = new InventoryDAO();

        model.setRowCount(0);

        for(Product p : dao.searchProducts(keyword)){

            model.addRow(new Object[]{

                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getQuantity(),
                    p.getPrice()

            });

        }

    }

    catch(Exception e){

        JOptionPane.showMessageDialog(this,
                e.getMessage());

    }

}


    JButton createButton(String text){

        JButton button = new JButton(text);

        button.setFocusPainted(false);

        button.setBackground(Color.WHITE);

        button.setFont(new Font("Arial",Font.BOLD,18));

        return button;

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new InventoryGUI();

        });

    }

}