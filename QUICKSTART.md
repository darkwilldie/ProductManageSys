# 快速启动指南

## 第一步：准备数据库

1. 打开SQL Server Management Studio
2. 连接到本地SQL Server实例
3. 运行以下SQL创建数据库:

```sql
CREATE DATABASE ProductManageSys;
```

4. 运行 `SQL/` 内的文件创建表结构。

## 第二步：检查配置

确保 `src/main/resources/application.properties` 中的数据库配置正确:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;integratedSecurity=true;encrypt=true;trustServerCertificate=true;
```

如果你的SQL Server不在本地或使用不同的端口，请修改连接字符串。

## 第三步：启动项目

### 使用Maven Wrapper（推荐）

在项目根目录打开命令行，运行:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac  
./mvnw spring-boot:run
```

### 使用IDE

在VS Code、IntelliJ IDEA或Eclipse中:
1. 打开项目
2. 找到 `ProductManageSysApplication.java`
3. 右键 -> Run 'ProductManageSysApplication'

## 第四步：访问系统

1. 打开浏览器访问: http://localhost:8080

2. 使用默认管理员账户登录:
   - 用户名: `admin`
   - 密码: `admin123`

3. 或者注册新用户

## 功能演示

### 1. 查看商品列表
登录后自动跳转到商品列表页面，可以看到所有商品

### 2. 新增商品
- 点击"新增商品"按钮
- 填写商品信息（名称、分类、价格、库存、描述）
- 点击"保存"

### 3. 查看商品详情
- 在商品列表中点击商品名称
- 查看商品的完整信息

### 4. 编辑商品
- 在商品详情页点击"编辑商品"
- 或在列表页点击"编辑"按钮
- 修改信息后保存

### 5. 删除商品
- **单个删除**: 在列表或详情页点击"删除"按钮
- **批量删除**: 
  - 在列表页勾选要删除的商品
  - 点击"批量删除"按钮
  - 确认删除

### 6. 导出Excel
- **导出选中**: 勾选商品后点击"导出选中"
- **导出全部**: 直接点击"导出全部"
- 浏览器会自动下载 `products.xlsx` 文件

## 系统预置分类

系统启动时会自动创建以下分类:
- 电子产品
- 服装鞋帽
- 食品饮料
- 图书文具
- 运动户外

## 常见问题解决

### 问题1: 无法连接数据库

**错误信息**: `Cannot open database "ProductManageSys"`

**解决方案**:
1. 确认SQL Server正在运行
2. 检查数据库名称是否正确
3. 确认Windows身份验证已启用

### 问题2: 端口被占用

**错误信息**: `Port 8080 was already in use`

**解决方案**:
在 `application.properties` 中修改端口:
```properties
server.port=8081
```

### 问题3: Maven依赖下载失败

**解决方案**:
```bash
# 清理并重新安装
mvnw clean install

# 如果还是失败，删除本地Maven仓库缓存
# Windows: C:\Users\<用户名>\.m2\repository
# 然后重新运行 mvnw clean install
```

### 问题4: 编译错误

**解决方案**:
1. 确保安装了JDK 25
2. 运行 `mvnw clean compile`
3. 刷新IDE项目

## 技术支持

如有问题，请查看完整的 `README.md` 文档或提交Issue。

## 下一步

- 尝试注册新用户
- 添加更多商品
- 测试批量操作和导出功能
- 探索不同的分类
