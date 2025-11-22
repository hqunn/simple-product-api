package com.lightvision.simple_product_api.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}