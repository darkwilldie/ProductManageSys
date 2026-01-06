# 系统更新说明

## 更新日期
2026年1月6日

## 更新内容

### 1. 导出功能优化 - 使用系统保存窗口

**修改文件：**
- `src/main/java/com/qrliu/ProductManageSys/controller/ProductController.java`

**更新内容：**
- 改进导出功能，确保使用浏览器的保存对话框
- 添加了中文文件名支持（使用UTF-8到ISO-8859-1编码转换）
- 文件名自动包含时间戳：`商品列表_yyyyMMdd_HHmmss.xlsx`
- Content-Disposition设置为attachment，强制浏览器显示保存对话框

### 2. 分类管理功能

#### 2.1 后端实现

**新增文件：**
- `src/main/java/com/qrliu/ProductManageSys/controller/CategoryController.java`

**修改文件：**
- `src/main/java/com/qrliu/ProductManageSys/service/ProductService.java`

**功能特性：**
- 分类列表查看 (`GET /categories`)
- 新增分类 (`GET /categories/new` 和 `POST /categories/save`)
- 编辑分类 (`GET /categories/edit/{id}` 和 `POST /categories/save`)
- 删除分类 (`POST /categories/delete/{id}`)
  - 智能检测：如果分类下有商品，将禁止删除并提示用户
- 快速添加分类API (`POST /categories/quick-add`)
  - 用于从商品表单页面快速创建新分类

#### 2.2 前端实现

**新增文件：**
- `src/main/resources/templates/categories.html` - 分类列表页面
- `src/main/resources/templates/category-form.html` - 分类编辑表单页面

**修改文件：**
- `src/main/resources/templates/products.html` - 添加"分类管理"按钮
- `src/main/resources/templates/product-form.html` - 添加快速创建分类功能

**功能特性：**
- 分类列表页面显示所有分类及其关联的商品数量
- 快速创建分类的模态对话框
- 在商品表单页面，可以直接点击"+ 新建分类"按钮快速创建分类
- 创建成功后，新分类会自动添加到下拉列表并被选中

### 3. CSS重构 - 样式外部化

**新增CSS文件：**
- `src/main/resources/static/css/common.css` - 公共样式（按钮、表单、表格等）
- `src/main/resources/static/css/products.css` - 商品列表页面专用样式
- `src/main/resources/static/css/product-form.css` - 商品表单页面专用样式
- `src/main/resources/static/css/product-detail.css` - 商品详情页面专用样式
- `src/main/resources/static/css/login.css` - 登录页面专用样式
- `src/main/resources/static/css/register.css` - 注册页面专用样式
- `src/main/resources/static/css/error.css` - 错误页面专用样式
- `src/main/resources/static/css/categories.css` - 分类管理页面专用样式

**修改的HTML文件：**
所有HTML模板文件中的内联`<style>`标签已移除，改为引用外部CSS文件：
- `products.html`
- `product-form.html`
- `product-detail.html`
- `login.html`
- `register.html`
- `error.html`

**优势：**
- 更好的代码组织和可维护性
- 样式复用性提高
- 浏览器可以缓存CSS文件，提高页面加载速度
- 便于主题定制和样式管理

## 使用说明

### 分类管理
1. 在商品列表页面点击"分类管理"按钮进入分类管理页面
2. 点击"新增分类"创建新的分类
3. 在分类列表中可以编辑或删除分类
4. 如果分类下有商品，系统会阻止删除操作

### 快速创建分类
1. 在商品新增/编辑页面
2. 在"商品分类"选择框旁边点击"+ 新建分类"按钮
3. 在弹出的对话框中输入分类名称和描述
4. 点击"创建"按钮
5. 新分类会自动添加到下拉列表并被选中

### 导出商品数据
1. 在商品列表页面选择要导出的商品（勾选复选框）
2. 点击"导出选中"按钮导出选中的商品
3. 或点击"导出全部"按钮导出所有商品
4. 浏览器会弹出保存对话框，文件名格式为：`商品列表_20260106_143025.xlsx`

## 技术细节

### CSRF保护
所有POST请求都包含CSRF令牌保护，确保系统安全。

### 数据验证
- 分类名称不能重复
- 分类名称最大长度100字符
- 分类描述最大长度255字符
- 商品必须关联一个有效的分类

### 响应式设计
所有页面都支持响应式布局，在不同设备上都有良好的显示效果。

## 注意事项

1. 默认分类在系统初始化时会自动创建（通过DataInitializer）
2. 删除分类前，请确保该分类下没有商品
3. 导出的Excel文件使用UTF-8编码，支持中文显示
4. 快速创建分类功能使用AJAX，需要浏览器支持JavaScript

## 后续建议

1. 可以添加批量删除分类功能
2. 可以添加分类排序功能
3. 可以添加分类图标/图片上传功能
4. 可以添加分类的层级关系（父子分类）
5. 可以添加导入Excel功能
