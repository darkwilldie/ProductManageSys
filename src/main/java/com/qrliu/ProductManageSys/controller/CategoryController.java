package com.qrliu.ProductManageSys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qrliu.ProductManageSys.entity.Category;
import com.qrliu.ProductManageSys.service.ProductService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-form";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Integer id, Model model) {
        Category category = productService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category-form";
    }

    @PostMapping("/save")
    public String saveCategory(@RequestParam(required = false) Integer id,
            @RequestParam String name,
            @RequestParam(required = false) String description) {
        Category category;
        if (id != null) {
            category = productService.getCategoryById(id);
        } else {
            category = new Category();
        }

        category.setName(name);
        category.setDescription(description);

        productService.saveCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, Model model) {
        try {
            productService.deleteCategory(id);
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("error", "删除失败");
            model.addAttribute("message", e.getMessage());
            model.addAttribute("status", 500);
            return "error";
        }
    }

    // AJAX endpoint for quick category creation from product form
    @PostMapping("/quick-add")
    @ResponseBody
    public ResponseEntity<?> quickAddCategory(@RequestParam String name,
            @RequestParam(required = false) String description) {
        try {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            Category savedCategory = productService.saveCategory(category);
            return ResponseEntity.ok(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("分类名称已存在或创建失败");
        }
    }
}
