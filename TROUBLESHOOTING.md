# 故障排查指南

## 常见启动失败原因及解决方案

### 1. 数据库未创建或未启动

**症状**: 应用启动失败，提示无法连接数据库

**解决方案**:
```sql
-- 在SQL Server Management Studio中执行
CREATE DATABASE ProductManageSys;
GO
```

确保SQL Server服务正在运行:
- 打开"服务" (services.msc)
- 找到 "SQL Server (MSSQLSERVER)" 或类似服务
- 确保状态为"正在运行"

### 2. 端口被占用

**症状**: `Port 8080 was already in use`

**解决方案**:
```bash
# 查找占用8080端口的进程
netstat -ano | findstr :8080

# 结束进程
taskkill /PID <进程ID> /F

# 或修改端口
# 在application.properties中添加:
server.port=8081
```

### 3. Java版本不匹配

**症状**: 编译错误或启动失败

**解决方案**:
```bash
# 检查Java版本
java -version

# 应该显示 Java 25 或更高
```

如果版本不对，请安装JDK 25。

### 4. Maven依赖下载失败

**症状**: 编译时提示缺少依赖

**解决方案**:
```bash
# 清理并重新下载依赖
mvnw.cmd clean install -U
```

### 5. 内存不足

**症状**: `java.lang.OutOfMemoryError`

**解决方案**:

修改启动命令:
```bash
set MAVEN_OPTS=-Xmx2048m
mvnw.cmd spring-boot:run
```

### 6. SQL Server身份验证问题

**症状**: `Login failed for user`

**解决方案A - 使用Windows身份验证** (推荐):
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;integratedSecurity=true;encrypt=true;trustServerCertificate=true;
```

**解决方案B - 使用SQL Server身份验证**:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=你的密码
```

### 7. 编译错误

**症状**: 代码有红线或编译失败

**解决方案**:
```bash
# 清理编译
mvnw.cmd clean

# 重新编译
mvnw.cmd compile

# 如果还有问题，删除.m2缓存重新下载
rmdir /s /q %USERPROFILE%\.m2\repository\org\springframework
mvnw.cmd compile
```

### 8. Lombok注解不生效

**症状**: 提示找不到getter/setter方法

**解决方案**:
- 确保IDE安装了Lombok插件
- VS Code: 安装 "Lombok Annotations Support"
- IntelliJ IDEA: 安装 "Lombok Plugin"
- Eclipse: 运行 lombok.jar

### 9. 表结构不匹配

**症状**: `Schema-validation: wrong column type`

**解决方案**:

方案1 - 删除并重建表:
```sql
USE ProductManageSys;
GO

DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS [User];
GO
```

然后重启应用，让JPA自动创建表。

方案2 - 手动运行DDL.sql:
```bash
# 在项目根目录找到DDL.sql并在SSMS中执行
```

### 10. 应用运行但无法访问

**症状**: 应用启动成功，但浏览器无法打开

**检查清单**:
- [ ] 确认端口号正确 (默认8080)
- [ ] 检查防火墙设置
- [ ] 尝试 http://localhost:8080
- [ ] 尝试 http://127.0.0.1:8080
- [ ] 查看控制台是否有错误

## 诊断步骤

### 步骤1: 检查环境
```bash
# Java版本
java -version

# Maven版本
mvnw.cmd -v

# SQL Server连接
sqlcmd -S localhost -E -Q "SELECT @@VERSION"
```

### 步骤2: 检查配置
打开 `src/main/resources/application.properties`
确认:
- 数据库URL正确
- 数据库名称存在
- 认证方式正确

### 步骤3: 测试编译
```bash
mvnw.cmd clean compile
```

如果编译失败，查看错误信息。

### 步骤4: 查看详细日志
```bash
mvnw.cmd spring-boot:run
```

仔细阅读启动日志，找到第一个ERROR或异常。

### 步骤5: 使用诊断脚本
```bash
start.bat
```

## 获取帮助

如果以上方法都无法解决问题，请提供:
1. 完整的错误日志（从启动到失败）
2. `application.properties` 内容
3. Java版本 (`java -version`)
4. SQL Server版本
5. 操作系统版本

## 成功启动的标志

看到以下日志表示启动成功:
```
Started ProductManageSysApplication in X.XXX seconds
```

然后访问: http://localhost:8080

## 快速测试

如果一切都配置正确，运行:
```bash
mvnw.cmd spring-boot:run
```

应该在20-30秒内看到启动成功的消息。

## 应急方案

如果无法启动，可以:
1. 检查是否是SQL Server的问题
2. 尝试使用H2内存数据库（需要修改配置）
3. 查看项目的测试类是否能运行
4. 降级到Java 17或21

## 常用命令

```bash
# 清理
mvnw.cmd clean

# 编译
mvnw.cmd compile

# 打包
mvnw.cmd package -DskipTests

# 运行
mvnw.cmd spring-boot:run

# 运行测试
mvnw.cmd test
```
