# 🚨 应用启动失败解决方案

根据你的错误信息，应用启动失败了。让我们一步步排查问题。

## 📋 快速诊断清单

请按顺序检查以下项目：

### ✅ 1. 数据库检查

**运行数据库测试**：
```bash
test-db.bat
```

如果失败，说明数据库有问题：

#### 1.1 确认SQL Server正在运行
```bash
# 打开服务管理
services.msc

# 查找并启动
SQL Server (MSSQLSERVER)
```

#### 1.2 创建数据库
打开 **SQL Server Management Studio**，执行：
```sql
CREATE DATABASE ProductManageSys;
GO
```

#### 1.3 验证数据库
```sql
-- 查看所有数据库
SELECT name FROM sys.databases;

-- 应该能看到 ProductManageSys
```

### ✅ 2. 端口检查

**检查8080端口**：
```bash
netstat -ano | findstr :8080
```

如果有输出，说明端口被占用。

**解决方案**：
```bash
# 方案A: 结束占用进程
taskkill /PID <进程ID> /F

# 方案B: 修改端口（在application.properties中）
server.port=8081
```

### ✅ 3. Java版本检查

```bash
java -version
```

应该显示 Java 17 或更高版本。

### ✅ 4. 清理并重新编译

```bash
mvnw.cmd clean
mvnw.cmd compile
```

查看是否有编译错误。

## 🔍 详细启动日志

运行以下命令查看完整日志：
```bash
mvnw.cmd spring-boot:run
```

## 常见错误及解决方案

### ❌ 错误 1: Cannot create PoolableConnectionFactory

**原因**: 无法连接到SQL Server数据库

**解决**:
1. 确认SQL Server服务正在运行
2. 确认数据库 `ProductManageSys` 已创建
3. 运行 `test-db.bat` 测试连接
4. 检查 `application.properties` 中的连接字符串

### ❌ 错误 2: Port 8080 was already in use

**原因**: 端口被占用

**解决**:
```bash
# 查找占用端口的进程
netstat -ano | findstr :8080

# 结束进程
taskkill /PID <PID> /F

# 或修改应用端口
```

### ❌ 错误 3: Cannot open database "ProductManageSys"

**原因**: 数据库不存在

**解决**:
```sql
CREATE DATABASE ProductManageSys;
```

### ❌ 错误 4: Login failed for user

**原因**: 数据库认证失败

**解决**:

检查 `application.properties`:
```properties
# Windows身份验证
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;integratedSecurity=true;encrypt=true;trustServerCertificate=true;

# 或使用SQL Server身份验证
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=你的密码
```

### ❌ 错误 5: ClassNotFoundException

**原因**: 依赖未正确加载

**解决**:
```bash
mvnw.cmd clean install -U
```

### ❌ 错误 6: OutOfMemoryError

**原因**: 内存不足

**解决**:
```bash
set MAVEN_OPTS=-Xmx2048m
mvnw.cmd spring-boot:run
```

## 📖 推荐排查流程

```bash
# 步骤 1: 测试数据库连接
test-db.bat

# 步骤 2: 如果数据库OK，清理项目
mvnw.cmd clean

# 步骤 3: 重新编译
mvnw.cmd compile

# 步骤 4: 启动应用（查看详细日志）
mvnw.cmd spring-boot:run
```

## 🎯 成功启动标志

看到以下信息表示启动成功：
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

[...]

Started ProductManageSysApplication in X.XXX seconds
```

然后访问: **http://localhost:8080**

## 🆘 还是无法解决？

请收集以下信息：

### 1. 环境信息
```bash
# Java版本
java -version

# Maven版本
mvnw.cmd -v
```

### 2. 数据库测试结果
```bash
test-db.bat > db-test-result.txt
```

### 3. 完整启动日志
```bash
mvnw.cmd spring-boot:run > startup-log.txt 2>&1
```

### 4. 配置文件
提供 `src/main/resources/application.properties` 内容（隐藏密码）

## 💡 快速修复建议

如果你急需启动应用，尝试以下步骤：

```bash
# 1. 确保数据库服务运行
net start MSSQLSERVER

# 2. 创建数据库（在SSMS中）
CREATE DATABASE ProductManageSys;

# 3. 清理并启动
mvnw.cmd clean spring-boot:run
```

## 📚 相关文档

- [README.md](README.md) - 完整项目说明
- [QUICKSTART.md](QUICKSTART.md) - 快速启动指南
- [TROUBLESHOOTING.md](TROUBLESHOOTING.md) - 详细故障排查
- [DEPLOYMENT.md](DEPLOYMENT.md) - 部署指南

## 🔧 工具脚本

项目提供了以下辅助脚本：

- `test-db.bat` - 测试数据库连接
- `start.bat` - 诊断并启动应用

直接双击运行即可。

---

**记住**: 90%的启动失败问题都是因为：
1. ❌ SQL Server未启动
2. ❌ 数据库未创建
3. ❌ 端口被占用

请先检查这三项！✅
