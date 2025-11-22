package com.lightvision.simple_product_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}