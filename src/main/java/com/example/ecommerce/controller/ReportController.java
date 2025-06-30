package com.example.ecommerce.controller;

import com.example.ecommerce.dto.TotalSalesResponse;
import com.example.ecommerce.model.Report;
import com.example.ecommerce.service.ReportService;
import com.example.ecommerce.dto.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/total-sales")
    public TotalSalesResponse getTotalSales() {
        double total = reportService.getTotalSales();
        return new TotalSalesResponse(total);
    }


    @GetMapping
    public List<ReportResponse> getAllReports() {
        return reportService.getAllReports().stream()
                .map(r -> new ReportResponse(
                        r.getId(),
                        r.getPeriod(),
                        r.getTotalSales(),
                        r.getGeneratedDate()
                ))
                .toList();
    }

    @PostMapping("/generate")
    public ReportResponse generateReport(@RequestParam String period) {
        Report report = reportService.generateMonthlyReport(period);
        return new ReportResponse(
                report.getId(),
                report.getPeriod(),
                report.getTotalSales(),
                report.getGeneratedDate()
        );
    }
}
