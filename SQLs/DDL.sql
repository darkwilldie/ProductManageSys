USE [ProductManageSys];

-- =============================================
-- 1. CLEANUP (Drop tables if they exist)
-- Order is important: Product depends on Category
-- =============================================
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL 
   DROP TABLE dbo.Product;
IF OBJECT_ID('dbo.Category', 'U') IS NOT NULL 
   DROP TABLE dbo.Category;
IF OBJECT_ID('dbo.[User]', 'U') IS NOT NULL 
   DROP TABLE dbo.[User];
GO

-- =============================================
-- 2. CREATE TABLES
-- =============================================

-- Table: User (Wrapped in [] because 'User' is a reserved keyword)
CREATE TABLE [User] (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL, -- Store hashed passwords in real apps
    role NVARCHAR(20) NOT NULL DEFAULT 'USER', -- e.g., 'ADMIN', 'USER'
    created_at DATETIME DEFAULT GETDATE()
);

-- Table: Category
CREATE TABLE Category (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(255)
);

-- Table: Product
CREATE TABLE Product (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(18, 2) NOT NULL, -- 18 digits total, 2 decimal places
    stock_quantity INT NOT NULL DEFAULT 0,
    category_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    
    -- Foreign Key Constraint
    CONSTRAINT FK_Product_Category FOREIGN KEY (category_id)
    REFERENCES Category(id)
    ON DELETE CASCADE
);
GO