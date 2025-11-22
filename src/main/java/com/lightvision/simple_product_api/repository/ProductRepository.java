package com.lightvision.simple_product_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightvision.simple_product_api.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Lấy list sản phẩm theo category id
    List<Product> findByCategoryId(Long categoryId);
    
    // Tìm kiếm sản phẩm theo tên (có chữ cái nào đó)
    // SELECT * FROM products WHERE name LIKE %keyword%
    List<Product> findByNameContainingIgnoreCase(String keyword);
}