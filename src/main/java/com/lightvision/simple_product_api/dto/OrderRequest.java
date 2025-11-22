package com.lightvision.simple_product_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId; // Tạm thời gửi userId thủ công (vì chưa làm JWT)
    private List<OrderItemRequest> items; // Danh sách hàng muốn mua

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}