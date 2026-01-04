package com.qrliu.ProductManageSys.config;

import com.qrliu.ProductManageSys.entity.Category;
import com.qrliu.ProductManageSys.entity.User;
import com.qrliu.ProductManageSys.repository.CategoryRepository;
import com.qrliu.ProductManageSys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 初始化分类数据
        if (categoryRepository.count() == 0) {
            Category electronics = new Category();
            electronics.setName("电子产品");
            electronics.setDescription("各类电子产品和数码设备");
            categoryRepository.save(electronics);

            Category clothing = new Category();
            clothing.setName("服装鞋帽");
            clothing.setDescription("各类服装、鞋子和配饰");
            categoryRepository.save(clothing);

            Category food = new Category();
            food.setName("食品饮料");
            food.setDescription("各类食品和饮料");
            categoryRepository.save(food);

            Category books = new Category();
            books.setName("图书文具");
            books.setDescription("各类图书和文具用品");
            categoryRepository.save(books);

            Category sports = new Category();
            sports.setName("运动户外");
            sports.setDescription("各类运动器材和户外用品");
            categoryRepository.save(sports);

            System.out.println("✓ 已初始化分类数据");
        }

        // 创建默认管理员账户（如果不存在）
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

            System.out.println("✓ 已创建默认管理员账户: admin/admin123");
        }
    }
}
