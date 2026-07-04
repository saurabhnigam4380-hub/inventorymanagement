-- ============================================
-- Inventory Management System - Database Setup
-- ============================================
-- How to run this file:
--   1. Open a terminal
--   2. Type: mysql -u root -p
--   3. Enter your MySQL password
--   4. Type: source schema.sql
--      (or copy-paste everything below into the MySQL prompt)
-- ============================================

CREATE DATABASE IF NOT EXISTS inventory_db;

USE inventory_db;

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    quantity INT NOT NULL DEFAULT 0,
    price DOUBLE NOT NULL DEFAULT 0.0,
    low_stock_threshold INT NOT NULL DEFAULT 5
);

-- A couple of sample rows so the app has something to show right away.
-- Feel free to delete these later using the app's "Delete Product" option.
INSERT INTO products (name, category, quantity, price, low_stock_threshold)
VALUES
    ('Notebook', 'Stationery', 50, 2.50, 10),
    ('USB Cable', 'Electronics', 3, 4.99, 5),
    ('Coffee Mug', 'Kitchen', 0, 6.00, 5);
