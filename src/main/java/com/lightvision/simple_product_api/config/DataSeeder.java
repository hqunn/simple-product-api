package com.lightvision.simple_product_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lightvision.simple_product_api.entity.Category;
import com.lightvision.simple_product_api.entity.Role;
import com.lightvision.simple_product_api.entity.User;
import com.lightvision.simple_product_api.repository.CategoryRepository;
import com.lightvision.simple_product_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category electronics = Category.builder()
                    .name("Electronics")
                    .description("Gadgets and devices")
                    .build();
            categoryRepository.save(electronics);
            System.out.println("----- Created: Electronics -----");
        }

        // 2. Create sample user if not exists (for testing Order)
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@test.com")
                    .password("123456") // Note: In production, it should be encrypted, here is just for testing
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            
             User customer = User.builder()
                    .username("customer")
                    .email("customer@test.com")
                    .password("123456")
                    .role(Role.CUSTOMER)
                    .build();
            userRepository.save(customer);
            System.out.println("----- Created: admin & customer -----");
        }
    }
}