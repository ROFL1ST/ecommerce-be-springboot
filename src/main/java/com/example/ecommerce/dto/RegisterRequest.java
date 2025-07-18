package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String fullname;
    private String phoneNo;
    private String address;
}
