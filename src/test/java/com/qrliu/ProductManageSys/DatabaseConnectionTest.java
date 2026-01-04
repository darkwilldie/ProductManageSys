package com.qrliu.ProductManageSys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库连接测试工具 运行此类来测试SQL Server连接是否正常
 */
public class DatabaseConnectionTest {

    public static void main(String[] args) {
        // 使用SQL Server身份验证（避免Windows集成身份验证的DLL问题）
        String url = "jdbc:sqlserver://localhost;databaseName=ProductManageSys;encrypt=true;trustServerCertificate=true;";
        String username = "productuser";
        String password = "Product@2026!";

        System.out.println("=================================");
        System.out.println("数据库连接测试");
        System.out.println("=================================");
        System.out.println("连接方式: SQL Server身份验证");
        System.out.println("服务器: localhost");
        System.out.println("用户名: " + username);
        System.out.println("URL: " + url);
        System.out.println();

        try {
            System.out.println("正在加载JDBC驱动...");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("✓ 驱动加载成功");
            System.out.println();

            System.out.println("正在连接数据库...");
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✓ 数据库连接成功！");
            System.out.println();

            System.out.println("正在测试查询...");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT @@VERSION AS 'SQL Server Version'");

            if (rs.next()) {
                System.out.println("✓ SQL Server版本:");
                System.out.println("  " + rs.getString(1));
            }
            System.out.println();

            // 检查数据库是否存在
            ResultSet databases = stmt.executeQuery("SELECT name FROM sys.databases WHERE name='ProductManageSys'");
            if (databases.next()) {
                System.out.println("✓ 数据库 'ProductManageSys' 存在");
            } else {
                System.out.println("✗ 数据库 'ProductManageSys' 不存在");
                System.out.println("  请在SQL Server中执行: CREATE DATABASE ProductManageSys;");
            }
            System.out.println();

            // 检查表
            System.out.println("检查数据表...");
            ResultSet tables = stmt.executeQuery(
                    "SELECT TABLE_NAME FROM ProductManageSys.INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE'"
            );

            boolean hasTables = false;
            while (tables.next()) {
                hasTables = true;
                System.out.println("  - " + tables.getString(1));
            }

            if (!hasTables) {
                System.out.println("  (暂无数据表，应用首次启动时会自动创建)");
            }
            System.out.println();

            rs.close();
            stmt.close();
            conn.close();

            System.out.println("=================================");
            System.out.println("✓ 所有测试通过！");
            System.out.println("  数据库连接正常，可以启动应用");
            System.out.println("=================================");

        } catch (ClassNotFoundException e) {
            System.err.println("✗ 错误: 找不到SQL Server JDBC驱动");
            System.err.println("  " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("✗ 数据库连接失败！");
            System.err.println();
            System.err.println("错误信息: " + e.getMessage());
            System.err.println();
            System.err.println("可能的原因:");
            System.err.println("1. SQL Server服务未启动");
            System.err.println("2. 数据库 'ProductManageSys' 不存在");
            System.err.println("3. Windows身份验证未配置");
            System.err.println("4. 防火墙阻止了连接");
            System.err.println();
            System.err.println("解决方案:");
            System.err.println("1. 确保SQL Server服务正在运行");
            System.err.println("2. 在SSMS中执行: CREATE DATABASE ProductManageSys;");
            System.err.println("3. 检查SQL Server配置管理器中的身份验证模式");
            System.err.println();
            e.printStackTrace();
            System.exit(1);
        }
    }
}
