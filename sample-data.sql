USE [ProductManageSys];
GO

-- =============================================
-- 插入示例商品数据
-- =============================================

-- 注意：这个脚本假设分类已经通过DataInitializer创建
-- 如果手动运行，请先确保Category表中有数据

-- 电子产品类别的商品
INSERT INTO Product (name, description, price, stock_quantity, category_id)
VALUES 
('iPhone 15 Pro', 'Apple最新旗舰手机，搭载A17 Pro芯片，钛金属边框，强大的Pro级摄像头系统', 7999.00, 50, 1),
('MacBook Air M3', '全新M3芯片，13.6英寸液态视网膜显示屏，轻薄便携，续航持久', 9999.00, 30, 1),
('AirPods Pro 2', '主动降噪，自适应通透模式，个性化空间音频', 1899.00, 100, 1),
('iPad Air', '10.9英寸液态视网膜显示屏，搭载M1芯片，支持Apple Pencil', 4799.00, 40, 1),
('Apple Watch Series 9', '全新S9芯片，更亮的显示屏，碳中和表款', 2999.00, 60, 1);

-- 服装鞋帽类别的商品
INSERT INTO Product (name, description, price, stock_quantity, category_id)
VALUES 
('耐克运动T恤', '透气速干面料，适合运动和休闲，多色可选', 299.00, 200, 2),
('阿迪达斯运动鞋', '舒适缓震，时尚百搭，适合跑步和日常穿着', 599.00, 150, 2),
('优衣库羽绒服', '轻薄保暖，防风防水，冬季必备单品', 799.00, 80, 2),
('李宁运动裤', '弹力面料，舒适透气，适合各种运动场景', 199.00, 180, 2);

-- 食品饮料类别的商品
INSERT INTO Product (name, description, price, stock_quantity, category_id)
VALUES 
('三只松鼠坚果礼盒', '精选优质坚果，多种口味组合，健康零食首选', 128.00, 300, 3),
('农夫山泉矿泉水', '天然矿泉水，550ml*24瓶/箱', 45.00, 500, 3),
('蒙牛纯牛奶', '100%生牛乳，250ml*16盒/箱', 58.00, 400, 3),
('百草味零食大礼包', '多种休闲零食组合，居家办公必备', 99.00, 250, 3);

-- 图书文具类别的商品
INSERT INTO Product (name, description, price, stock_quantity, category_id)
VALUES 
('《活着》余华著', '中国当代文学经典作品，深刻感人', 35.00, 100, 4),
('晨光文具套装', '包含笔、橡皮、尺子等常用文具，学生必备', 45.00, 200, 4),
('Moleskine笔记本', '经典黑色硬壳笔记本，品质上乘，适合记录和创作', 128.00, 80, 4),
('《人类简史》', '尤瓦尔·赫拉利著，畅销全球的历史巨著', 68.00, 150, 4);

-- 运动户外类别的商品
INSERT INTO Product (name, description, price, stock_quantity, category_id)
VALUES 
('迪卡侬瑜伽垫', '厚度适中，防滑耐用，适合瑜伽和健身', 99.00, 120, 5),
('威尔胜网球拍', '专业级网球拍，适合初学者和进阶选手', 399.00, 60, 5),
('探路者登山包', '大容量，多口袋设计，适合户外徒步和旅行', 299.00, 90, 5),
('李宁篮球', '标准7号球，室内外两用，手感出色', 199.00, 150, 5);

GO

-- 查询插入的数据
SELECT 
    p.id AS '商品ID',
    p.name AS '商品名称',
    c.name AS '分类',
    p.price AS '价格',
    p.stock_quantity AS '库存'
FROM Product p
JOIN Category c ON p.category_id = c.id
ORDER BY c.id, p.id;
GO
