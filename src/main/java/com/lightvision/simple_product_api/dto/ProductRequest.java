package com.lightvision.simple_product_api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Long categoryId;
}