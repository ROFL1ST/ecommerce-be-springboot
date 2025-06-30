package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class InvoiceResponse {
    private Long invoiceId;
    private Long orderId;
    private String status;
    private LocalDateTime orderTime;
    private List<OrderItemResponse> items;
    private double total;
    private double tax;
    private double shipping;
    private LocalDate issueDate;
}
