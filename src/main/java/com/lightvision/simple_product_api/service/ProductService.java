package com.lightvision.simple_product_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lightvision.simple_product_api.dto.CategoryResponse;
import com.lightvision.simple_product_api.dto.ProductRequest;
import com.lightvision.simple_product_api.dto.ProductResponse;
import com.lightvision.simple_product_api.entity.Category;
import com.lightvision.simple_product_api.entity.Product;
import com.lightvision.simple_product_api.repository.CategoryRepository;
import com.lightvision.simple_product_api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // 1. get all products 
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        // Use Stream to convert each Entity to DTO
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    // 2. get product by id
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToProductResponse(product);
    }

    // 3. create product
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .description(request.getDescription())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    // --- Helper Method: Convert from Entity to DTO ---
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                // Map Category Entity sang Category Response
                .category(CategoryResponse.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .description(product.getCategory().getDescription())
                        .build())
                .build();
    }
}