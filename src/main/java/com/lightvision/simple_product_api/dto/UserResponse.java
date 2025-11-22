package com.lightvision.simple_product_api.dto;

import com.lightvision.simple_product_api.entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
}