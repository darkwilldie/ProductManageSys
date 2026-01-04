# 部署指南

本文档介绍如何将商品管理系统部署到生产环境。

## 目录
1. [环境准备](#环境准备)
2. [数据库配置](#数据库配置)
3. [应用配置](#应用配置)
4. [打包部署](#打包部署)
5. [启动应用](#启动应用)
6. [验证部署](#验证部署)
7. [常见问题](#常见问题)

---

## 环境准备

### 必需软件

1. **Java Development Kit (JDK)**
   - 版本: JDK 25
   - 下载地址: https://www.oracle.com/java/technologies/downloads/

2. **SQL Server**
   - 版本: SQL Server 2019 或更高
   - 可以使用 SQL Server Express（免费版）

3. **Maven** (可选)
   - 项目包含Maven Wrapper，可直接使用
   - 如需独立Maven，版本 3.6+

### 系统要求

- **操作系统**: Windows 10/11, Windows Server 2016+
- **内存**: 至少 2GB RAM（建议 4GB+）
- **磁盘空间**: 至少 500MB 可用空间
- **网络**: 需要访问Maven中央仓库（首次构建）

---

## 数据库配置

### 步骤1: 创建数据库

打开SQL Server Management Studio，执行:

```sql
CREATE DATABASE ProductManageSys;
GO

-- 创建数据库用户（可选，用于SQL Server身份验证）
CREATE LOGIN productadmin WITH PASSWORD = 'YourStrongPassword123!';
GO

USE ProductManageSys;
GO

CREATE USER productadmin FOR LOGIN productadmin;
GO

-- 授予权限
ALTER ROLE db_owner ADD MEMBER productadmin;
GO
```

### 步骤2: 配置连接方式

#### 方式A: Windows身份验证（推荐用于开发）

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;integratedSecurity=true;encrypt=true;trustServerCertificate=true;
```

#### 方式B: SQL Server身份验证（推荐用于生产）

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManageSys;encrypt=true;trustServerCertificate=true;
spring.datasource.username=productadmin
spring.datasource.password=YourStrongPassword123!
```

---

## 应用配置

### 生产环境配置文件

创建 `src/main/resources/application-prod.properties`:

```properties
# 应用名称
spring.application.name=ProductManageSys

# 服务器端口
server.port=8080

# 数据库配置
spring.datasource.url=jdbc:sqlserver://your-server:1433;databaseName=ProductManageSys;encrypt=true;trustServerCertificate=true;
spring.datasource.username=productadmin
spring.datasource.password=YourStrongPassword123!
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# JPA配置（生产环境使用validate，不自动修改表结构）
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

# 日志配置
logging.level.root=INFO
logging.level.com.qrliu.ProductManageSys=INFO
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=30

# 生产环境安全配置
server.error.include-message=never
server.error.include-stacktrace=never
```

### 安全建议

1. **修改默认管理员密码**
   - 首次部署后立即登录并修改密码
   - 或在代码中修改 `DataInitializer.java`

2. **使用环境变量**
   ```properties
   spring.datasource.password=${DB_PASSWORD}
   ```

3. **启用HTTPS**（生产环境强烈推荐）
   ```properties
   server.ssl.enabled=true
   server.ssl.key-store=classpath:keystore.p12
   server.ssl.key-store-password=your-password
   server.ssl.key-store-type=PKCS12
   ```

---

## 打包部署

### 步骤1: 清理和编译

```bash
# Windows
mvnw.cmd clean package -DskipTests

# Linux/Mac
./mvnw clean package -DskipTests
```

成功后会在 `target/` 目录生成 `ProductManageSys-0.0.1-SNAPSHOT.jar`

### 步骤2: 上传到服务器

将以下文件上传到服务器:
- `target/ProductManageSys-0.0.1-SNAPSHOT.jar`
- `application-prod.properties` (如果有)

---

## 启动应用

### 方式1: 直接运行JAR

```bash
# 使用默认配置
java -jar ProductManageSys-0.0.1-SNAPSHOT.jar

# 使用生产配置
java -jar ProductManageSys-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# 指定端口
java -jar ProductManageSys-0.0.1-SNAPSHOT.jar --server.port=8081

# 指定JVM参数
java -Xms512m -Xmx1024m -jar ProductManageSys-0.0.1-SNAPSHOT.jar
```

### 方式2: 后台运行（Linux）

```bash
nohup java -jar ProductManageSys-0.0.1-SNAPSHOT.jar > application.log 2>&1 &

# 查看进程
ps aux | grep ProductManageSys

# 停止进程
kill <PID>
```

### 方式3: Windows服务

使用 NSSM 或 WinSW 将应用注册为Windows服务:

1. 下载 NSSM: https://nssm.cc/download
2. 安装服务:
```cmd
nssm install ProductManageSys "C:\path\to\java.exe" "-jar C:\path\to\ProductManageSys-0.0.1-SNAPSHOT.jar"
nssm start ProductManageSys
```

### 方式4: systemd服务（Linux）

创建 `/etc/systemd/system/productmanagesys.service`:

```ini
[Unit]
Description=Product Management System
After=syslog.target network.target

[Service]
User=productapp
ExecStart=/usr/bin/java -jar /opt/productmanagesys/ProductManageSys-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启动服务:
```bash
sudo systemctl daemon-reload
sudo systemctl enable productmanagesys
sudo systemctl start productmanagesys
sudo systemctl status productmanagesys
```

---

## 验证部署

### 1. 检查应用状态

```bash
# 查看日志
tail -f logs/application.log

# 检查端口监听
netstat -ano | findstr :8080    # Windows
netstat -tuln | grep 8080       # Linux
```

### 2. 访问应用

打开浏览器访问: `http://your-server:8080`

### 3. 测试功能

- [ ] 登录功能正常
- [ ] 商品列表显示正常
- [ ] 可以新增商品
- [ ] 可以编辑商品
- [ ] 可以删除商品
- [ ] 可以批量删除
- [ ] 可以导出Excel

### 4. 检查数据库

```sql
USE ProductManageSys;
GO

-- 检查表是否创建
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES;

-- 检查分类数据
SELECT * FROM Category;

-- 检查用户数据
SELECT id, username, role, created_at FROM [User];
```

---

## 常见问题

### Q1: 端口被占用

**错误**: `Port 8080 was already in use`

**解决**:
```bash
# Windows - 查找占用端口的进程
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# 或修改端口
java -jar app.jar --server.port=8081
```

### Q2: 无法连接数据库

**错误**: `Cannot create PoolableConnectionFactory`

**解决**:
1. 检查数据库服务是否运行
2. 验证连接字符串
3. 检查防火墙设置
4. 确认SQL Server允许远程连接

### Q3: 内存不足

**错误**: `java.lang.OutOfMemoryError`

**解决**:
```bash
# 增加堆内存
java -Xms512m -Xmx2048m -jar app.jar
```

### Q4: 日志找不到

**解决**:
确保应用有写入日志目录的权限:
```bash
mkdir -p logs
chmod 755 logs
```

### Q5: 表结构不匹配

**错误**: `Schema-validation: wrong column type`

**解决**:
1. 删除旧表（**注意备份数据**）
2. 让应用重新创建表
3. 或手动运行DDL.sql

---

## 性能优化

### 1. JVM调优

```bash
java -XX:+UseG1GC \
     -Xms512m \
     -Xmx2048m \
     -XX:MaxGCPauseMillis=200 \
     -jar app.jar
```

### 2. 数据库连接池

在 `application-prod.properties` 中:

```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
```

### 3. 缓存配置

添加缓存依赖并配置缓存策略（可选）

---

## 监控和维护

### 1. 日志监控

定期检查日志文件:
```bash
# 查看错误日志
grep -i error logs/application.log

# 实时监控
tail -f logs/application.log
```

### 2. 数据库备份

定期备份数据库:
```sql
BACKUP DATABASE ProductManageSys 
TO DISK = 'C:\Backups\ProductManageSys.bak'
WITH FORMAT;
```

### 3. 应用健康检查

可以添加Spring Boot Actuator进行健康检查:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

访问: `http://your-server:8080/actuator/health`

---

## 安全清单

部署前检查:
- [ ] 修改默认管理员密码
- [ ] 使用强密码策略
- [ ] 启用HTTPS
- [ ] 配置防火墙规则
- [ ] 定期更新依赖
- [ ] 配置日志轮转
- [ ] 限制错误信息显示
- [ ] 定期数据库备份
- [ ] 监控异常登录

---

## 回滚策略

如果部署出现问题:

1. **停止应用**
   ```bash
   kill <PID>  # 或
   systemctl stop productmanagesys
   ```

2. **恢复数据库**
   ```sql
   RESTORE DATABASE ProductManageSys 
   FROM DISK = 'C:\Backups\ProductManageSys.bak';
   ```

3. **部署旧版本**
   ```bash
   java -jar ProductManageSys-0.0.1-SNAPSHOT.jar.backup
   ```

---

## 技术支持

遇到问题请查看:
- 应用日志: `logs/application.log`
- README.md
- QUICKSTART.md
- USER_MANUAL.md

---

**部署成功！祝您使用愉快！** 🎉
