@echo off
chcp 65001 >nul
echo ================================
echo 测试数据库连接
echo ================================
echo.

cd /d "%~dp0"

echo 正在编译测试类...
call mvnw.cmd test-compile
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ 编译失败
    pause
    exit /b 1
)

echo.
echo 正在测试数据库连接...
echo.

call mvnw.cmd exec:java -Dexec.mainClass="com.qrliu.ProductManageSys.DatabaseConnectionTest" -Dexec.classpathScope=test

echo.
pause
