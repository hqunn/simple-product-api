package com.lightvision.simple_product_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lightvision.simple_product_api.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Lấy lịch sử mua hàng của 1 user cụ thể
    List<Order> findByUserId(Long userId);
}