@echo off
chcp 65001 >nul
echo ================================
echo 商品管理系统 - 启动诊断
echo ================================
echo.

echo [1/5] 检查Java版本...
java -version
echo.

echo [2/5] 检查数据库连接...
echo 请确保SQL Server正在运行
echo 数据库名称: ProductManageSys
echo.

echo [3/5] 清理项目...
call mvnw.cmd clean
echo.

echo [4/5] 编译项目...
call mvnw.cmd compile
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ 编译失败！请检查上面的错误信息
    pause
    exit /b 1
)
echo.

echo [5/5] 启动应用...
echo 如果遇到错误，请查看详细信息
echo.
call mvnw.cmd spring-boot:run

pause
