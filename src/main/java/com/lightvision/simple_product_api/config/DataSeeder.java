package com.lightvision.simple_product_api.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lightvision.simple_product_api.entity.Category;
import com.lightvision.simple_product_api.entity.Product;
import com.lightvision.simple_product_api.entity.Role;
import com.lightvision.simple_product_api.entity.User;
import com.lightvision.simple_product_api.repository.CategoryRepository;
import com.lightvision.simple_product_api.repository.ProductRepository;
import com.lightvision.simple_product_api.repository.UserRepository;

import lombok.RequiredArgsConstructor; // Chỉ cần import List, không cần Arrays nữa

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) {
            System.out.println("----- Data already exists. Skipping seeding. -----");
            return;
        }

        // 1. SEED USERS
        // User 1: Admin
        User admin = User.builder()
                .username("admin")
                .email("admin@lightvision.com")
                .password("123456")
                .role(Role.ADMIN)
                .build();
        // User 2: Customer
        User customer = User.builder()
                .username("customer")
                .email("customer@lightvision.com")
                .password("123456")
                .role(Role.CUSTOMER)
                .build();
        
        // SỬA: Dùng List.of thay vì Arrays.asList
        userRepository.saveAll(List.of(admin, customer));
        System.out.println("----- Users Created: admin & customer -----");

        // 2. SEED CATEGORIES
        Category electronics = categoryRepository.save(Category.builder()
                .name("Electronics")
                .description("Devices, gadgets, and computer accessories")
                .build());

        Category books = categoryRepository.save(Category.builder()
                .name("Books")
                .description("Technical books, novels, and comics")
                .build());

        Category fashion = categoryRepository.save(Category.builder()
                .name("Fashion")
                .description("Clothing, shoes, and accessories")
                .build());

        System.out.println("----- Categories Created: Electronics, Books, Fashion -----");

        // 3. SEED PRODUCTS
        Product laptop = Product.builder()
                .name("Gaming Laptop ASUS ROG")
                .price(BigDecimal.valueOf(1200.00))
                .stock(10)
                .description("High performance gaming laptop with RTX 4060")
                .category(electronics)
                .build();

        Product book = Product.builder()
                .name("Clean Code")
                .price(BigDecimal.valueOf(45.00))
                .stock(20)
                .description("A Handbook of Agile Software Craftsmanship by Robert C. Martin")
                .category(books)
                .build();

        Product tShirt = Product.builder()
                .name("Cotton T-Shirt")
                .price(BigDecimal.valueOf(15.00))
                .stock(50)
                .description("100% Cotton, Black color, Size L")
                .category(fashion)
                .build();

        // SỬA: Dùng List.of thay vì Arrays.asList
        productRepository.saveAll(List.of(laptop, book, tShirt));
        System.out.println("----- Products Created: Laptop, Book, T-Shirt -----");
    }
}