package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReportResponse {
    private Long reportId;
    private String period;
    private double totalSales;
    private LocalDate generatedDate;
}
