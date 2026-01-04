# 商品管理系统 (Product Management System)

## 项目简介

这是一个基于Spring Boot + Thymeleaf + SQL Server的商品管理系统，实现了用户登录/注册、商品的增删改查、批量删除、Excel导出等功能。

## 功能特性

- ✅ **用户认证**: 用户登录和注册功能
- ✅ **商品管理**: 增加、编辑、删除商品
- ✅ **商品列表**: 展示所有商品（ID、商品名、分类）
- ✅ **商品详情**: 查看商品的完整信息
- ✅ **批量操作**: 多选商品批量删除
- ✅ **数据导出**: 支持导出选中商品或全部商品为Excel文件
- ✅ **分类管理**: 商品分类支持

## 技术栈

- **后端框架**: Spring Boot 4.0.1
- **数据库**: Microsoft SQL Server
- **ORM**: Spring Data JPA / Hibernate
- **安全框架**: Spring Security
- **模板引擎**: Thymeleaf
- **Excel处理**: Apache POI
- **构建工具**: Maven

## 环境要求

- JDK 25
- SQL Server (本地实例)
- Maven 3.x

## 数据库配置

1. 在SQL Server中创建数据库:

```sql
CREATE DATABASE ProductManageSys;
```

2. 运行DDL.sql文件创建表结构（可选，项目会自动创建表）

3. 在 `application.properties` 中配置数据库连接:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;integratedSecurity=true;encrypt=true;trustServerCertificate=true;
```

## 运行项目

### 方式1: 使用Maven

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### 方式2: 使用IDE

直接运行 `ProductManageSysApplication.java` 主类

## 访问系统

启动成功后，访问: http://localhost:8080

### 默认账户

- 用户名: `admin`
- 密码: `admin123`

## 项目结构

```
src/main/java/com/qrliu/ProductManageSys/
├── config/
│   ├── DataInitializer.java      # 数据初始化
│   └── SecurityConfig.java        # Spring Security配置
├── controller/
│   ├── AuthController.java        # 认证控制器
│   └── ProductController.java     # 商品控制器
├── entity/
│   ├── Category.java              # 分类实体
│   ├── Product.java               # 商品实体
│   └── User.java                  # 用户实体
├── repository/
│   ├── CategoryRepository.java    # 分类数据访问
│   ├── ProductRepository.java     # 商品数据访问
│   └── UserRepository.java        # 用户数据访问
├── service/
│   ├── ProductService.java        # 商品业务逻辑
│   └── UserService.java           # 用户业务逻辑
└── ProductManageSysApplication.java # 启动类

src/main/resources/
├── templates/
│   ├── login.html                 # 登录页面
│   ├── register.html              # 注册页面
│   ├── products.html              # 商品列表页面
│   ├── product-detail.html        # 商品详情页面
│   └── product-form.html          # 商品表单页面
└── application.properties         # 配置文件
```

## 功能说明

### 1. 用户认证
- 支持用户注册
- 使用BCrypt加密密码
- 基于Spring Security的认证和授权

### 2. 商品列表页面
- 显示所有商品的ID、名称、分类、价格、库存
- 支持全选/单选商品
- 批量删除选中的商品
- 导出选中商品或全部商品为Excel文件
- 点击商品名称查看详情

### 3. 商品详情页面
- 显示商品的完整信息（ID、名称、分类、价格、库存、描述、创建时间）
- 可以编辑或删除当前商品

### 4. 新增/编辑商品
- 表单包含：商品名称、分类、价格、库存数量、描述
- 表单验证确保数据完整性

### 5. Excel导出
- 导出格式: xlsx
- 导出内容: ID、商品名称、分类、描述、价格、库存数量、创建时间
- 支持导出选中商品或全部商品

## 初始数据

系统启动时会自动创建以下分类:
- 电子产品
- 服装鞋帽
- 食品饮料
- 图书文具
- 运动户外

## 常见问题

### 1. 数据库连接失败
- 确保SQL Server正在运行
- 检查Windows身份验证是否启用
- 确认数据库名称和连接字符串正确

### 2. 编译错误
运行以下命令重新安装依赖:
```bash
mvnw clean install
```

### 3. 端口占用
修改 `application.properties` 中的端口:
```properties
server.port=8081
```

## 开发者

- GitHub: qrliu

## 许可证

MIT License
