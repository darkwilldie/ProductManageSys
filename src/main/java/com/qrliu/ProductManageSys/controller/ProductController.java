package com.qrliu.ProductManageSys.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qrliu.ProductManageSys.entity.Category;
import com.qrliu.ProductManageSys.entity.Product;
import com.qrliu.ProductManageSys.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        List<Category> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = productService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@RequestParam(required = false) Integer id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer categoryId) {
        Product product;
        if (id != null) {
            product = productService.getProductById(id);
        } else {
            product = new Product();
        }

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);

        Category category = productService.getCategoryById(categoryId);
        product.setCategory(category);

        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/delete-batch")
    public String deleteBatch(@RequestParam(value = "ids", required = false) List<Integer> ids, Model model) {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new IllegalArgumentException("请选择要删除的商品");
            }
            productService.deleteProducts(ids);
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("error", "批量删除失败");
            model.addAttribute("message", e.getMessage());
            model.addAttribute("status", 500);
            return "error";
        }
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportProducts(@RequestParam(value = "ids", required = false) List<Integer> ids, Model model) {
        try {
            byte[] excelData = productService.exportProductsToExcel(ids);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "products.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } catch (Exception e) {
            throw new RuntimeException("导出失败: " + e.getMessage(), e);
        }
    }
}
