package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String status;
    private double total;
    private LocalDateTime orderTime;
    private List<OrderItemResponse> items;
}
