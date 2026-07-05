# Inventory Management System

A desktop-based **Inventory Management System** developed using **Core Java, Java Swing, JDBC, and MySQL**. The application helps manage inventory efficiently through a graphical user interface with complete CRUD operations and real-time database connectivity.

---

## Features

- Add New Product
- Update Product Stock
- Delete Product
- Search Product
- Generate Inventory Report
- Dashboard with Live Statistics
- Product Table with MySQL Data
- Low Stock Monitoring
- User-Friendly Java Swing GUI

---

## Technologies Used

- Core Java
- Java Swing
- JDBC
- MySQL
- Git & GitHub
- VS Code

---

## Project Structure

```
InventoryManagement/
│
├── InventoryApp.java
├── InventoryGUI.java
├── InventoryDAO.java
├── DBConnection.java
├── Product.java
├── schema.sql
└── README.md
```

---

## Database

Database Name:

```
inventory_db
```

Main Table:

```
products
```

Columns:

- id
- name
- category
- quantity
- price
- low_stock_threshold

---

## How to Run

### 1. Clone Repository

```bash
git clone https://github.com/saurabhnigam4380-hub/inventorymanagement.git
```

### 2. Create Database

Open MySQL and execute:

```sql
source schema.sql
```

### 3. Compile

```bash
javac *.java
```

### 4. Run Console Version

```bash
java InventoryApp
```

### 5. Run GUI Version

```bash
java -cp ".;mysql-connector-j-9.7.0.jar" InventoryGUI
```

---

## Functionalities

- Inventory Dashboard
- Product Management
- Live Database Connectivity
- CRUD Operations
- Inventory Statistics
- Search Functionality
- Report Generation

---

## Future Enhancements

- Login Authentication
- Barcode Scanner Integration
- PDF Report Export
- Excel Export
- Supplier Management
- Sales Module
- Billing System

---

## Author

**Saurabh Nigam**

B.Tech CSE

United College of Engineering and Research

Prayagraj, Uttar Pradesh

---

## License

This project is developed for educational purposes during Summer Training.