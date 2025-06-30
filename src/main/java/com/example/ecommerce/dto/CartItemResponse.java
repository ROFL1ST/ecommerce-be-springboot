package com.example.ecommerce.dto;

import com.example.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Product product;
    private int quantity;
}
