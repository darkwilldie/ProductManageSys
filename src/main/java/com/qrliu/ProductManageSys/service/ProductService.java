package com.qrliu.ProductManageSys.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qrliu.ProductManageSys.entity.Category;
import com.qrliu.ProductManageSys.entity.Product;
import com.qrliu.ProductManageSys.repository.CategoryRepository;
import com.qrliu.ProductManageSys.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void deleteProducts(List<Integer> ids) {
        productRepository.deleteAllById(ids);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        Category category = getCategoryById(id);
        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new RuntimeException("该分类下还有商品，无法删除");
        }
        categoryRepository.deleteById(id);
    }

    public byte[] exportProductsToExcel(List<Integer> productIds) throws IOException {
        List<Product> products;
        if (productIds == null || productIds.isEmpty()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findAllById(productIds);
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("商品列表");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("商品名称");
        headerRow.createCell(2).setCellValue("分类");
        headerRow.createCell(3).setCellValue("描述");
        headerRow.createCell(4).setCellValue("价格");
        headerRow.createCell(5).setCellValue("库存数量");
        headerRow.createCell(6).setCellValue("创建时间");

        // 填充数据
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory() != null ? product.getCategory().getName() : "");
            row.createCell(3).setCellValue(product.getDescription() != null ? product.getDescription() : "");
            row.createCell(4).setCellValue(product.getPrice() != null ? product.getPrice().doubleValue() : 0.0);
            row.createCell(5).setCellValue(product.getStockQuantity() != null ? product.getStockQuantity() : 0);
            row.createCell(6).setCellValue(product.getCreatedAt() != null ? product.getCreatedAt().toString() : "");
        }

        // 自动调整列宽
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
