package com.qrliu.ProductManageSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ProductManageSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductManageSysApplication.class, args);
    }

    @Controller
    public static class RootController {

        @GetMapping("/")
        public String root() {
            return "redirect:/products";
        }
    }
}
