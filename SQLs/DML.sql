-- =============================================
-- 1. INSERT USERS (10 Rows)
-- =============================================
INSERT INTO [User] (username, password, role) VALUES 
('admin_master', 'admin123!', 'ADMIN'),
('manager_bob', 'securePass', 'MANAGER'),
('dev_alice', 'codePass', 'DEVELOPER'),
('user_john', 'pass123', 'USER'),
('user_jane', 'pass456', 'USER'),
('guest_mike', 'guestPass', 'USER'),
('analyst_tom', 'dataPass', 'ANALYST'),
('support_team', 'helpMe', 'SUPPORT'),
('test_account', 'testPass', 'TESTER'),
('vendor_portal', 'vendPass', 'VENDOR');
GO

-- =============================================
-- 2. INSERT CATEGORIES (5 Rows)
-- =============================================
INSERT INTO Category (name, description) VALUES 
('Electronics', 'Gadgets, phones, and computers'),
('Books', 'Technical and fiction books'),
('Clothing', 'Apparel for men and women'),
('Home & Kitchen', 'Furniture and appliances'),
('Sports & Outdoors', 'Gym equipment and outdoor gear');
GO

-- =============================================
-- 3. INSERT PRODUCTS (20 Rows)
-- Assumes Category IDs are 1-5 based on insertion order
-- =============================================

-- Category 1: Electronics
INSERT INTO Product (name, price, stock_quantity, category_id, description) VALUES 
('Smartphone Galaxy S24', 899.99, 50, 1, 'Latest Android flagship'),
('MacBook Air M3', 1199.00, 20, 1, 'Lightweight powerful laptop'),
('Sony WH-1000XM5', 349.99, 80, 1, 'Noise cancelling headphones'),
('Logitech MX Master 3S', 99.99, 150, 1, 'Ergonomic wireless mouse');

-- Category 2: Books
INSERT INTO Product (name, price, stock_quantity, category_id, description) VALUES 
('Effective Java 3rd Ed', 45.50, 60, 2, 'Best practices for Java platform'),
('System Design Interview', 35.99, 40, 2, 'Guide to distributed systems'),
('The Alchemist', 14.99, 100, 2, 'A novel by Paulo Coelho'),
('Atomic Habits', 18.00, 120, 2, 'Building good habits');

-- Category 3: Clothing
INSERT INTO Product (name, price, stock_quantity, category_id, description) VALUES 
('Levis 501 Original Jeans', 69.50, 90, 3, 'Classic straight fit'),
('Nike Air Max 90', 120.00, 30, 3, 'Popular athletic sneakers'),
('Uniqlo Heattech Shirt', 19.90, 200, 3, 'Thermal inner wear'),
('North Face Rain Jacket', 89.99, 25, 3, 'Waterproof shell jacket');
USE [ProductManageSys];

-- Category 4: Home & Kitchen
INSERT INTO Product (name, price, stock_quantity, category_id, description) VALUES 
('Dyson Vacuum V15', 699.99, 15, 4, 'Cordless vacuum cleaner'),
('Instant Pot Duo', 89.95, 75, 4, '7-in-1 electric pressure cooker'),
('IKEA Standing Desk', 249.00, 10, 4, 'Adjustable height desk'),
('Philips Hue Bulb', 45.00, 100, 4, 'Smart LED light bulb');

-- Category 5: Sports & Outdoors
INSERT INTO Product (name, price, stock_quantity, category_id, description) VALUES 
('Yoga Mat Extra Thick', 25.99, 60, 5, 'Non-slip exercise mat'),
('Dumbbell Set 20lbs', 45.00, 40, 5, 'Cast iron hand weights'),
('Camping Tent 4-Person', 129.99, 12, 5, 'Water resistant dome tent'),
('Protein Powder 2kg', 55.00, 85, 5, 'Chocolate whey protein');
GO

-- =============================================
-- 4. VERIFICATION
-- =============================================
SELECT 
    p.id, 
    p.name AS ProductName, 
    c.name AS CategoryName, 
    p.price 
FROM Product p
INNER JOIN Category c ON p.category_id = c.id;