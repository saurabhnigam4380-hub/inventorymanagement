# inventorymanagement
for summer internship
# 📦 Inventory Management System

A Java-based Inventory Management System developed using **Core Java**, **JDBC**, and **MySQL**. The application allows users to manage inventory through a simple console-based interface with CRUD operations and stock management.

## 🚀 Features

- ➕ Add new products
- ✏️ Update product stock
- ❌ Delete products
- 🔍 Check stock availability
- 📊 Generate inventory report
- ⚠️ Low stock alert support
- 💾 Data stored permanently using MySQL

## 🛠️ Technologies Used

- Java
- JDBC
- MySQL
- SQL
- Git & GitHub

## 📂 Project Structure

```
inventorymanagement/
│── DBConnection.java
│── InventoryApp.java
│── InventoryDAO.java
│── Product.java
│── schema.sql
│── README.md
```

## 🗄️ Database Setup

1. Create the database using the SQL script:

```sql
source schema.sql;
```

2. Update the database credentials in `DBConnection.java`.

Example:

```java
private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

## ▶️ How to Run

Compile:

```bash
javac -cp ".;mysql-connector-j-9.7.0.jar" *.java
```

Run:

```bash
java -cp ".;mysql-connector-j-9.7.0.jar" InventoryApp
```

## 📋 Menu

```
1. Add Product
2. Update Stock
3. Delete Product
4. Check Stock Availability
5. Generate Report
6. Exit
```

## 📸 Sample Output

```
=========================================
 Welcome to the Inventory Management System
=========================================

1. Add Product
2. Update Stock
3. Delete Product
4. Check Stock Availability
5. Generate Report
6. Exit
```

## 📌 Future Improvements

- Java Swing GUI
- Login Authentication
- Barcode Scanner Support
- Export Reports (PDF/Excel)
- Search by Product Name
- Sales Management Module

## 👨‍💻 Author

**Saurabh Nigam**

GitHub: https://github.com/saurabhnigam4380-hub