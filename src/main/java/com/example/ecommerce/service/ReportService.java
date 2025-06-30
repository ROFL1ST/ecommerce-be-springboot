package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Report;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepo;
    private final ReportRepository reportRepo;

    public double getTotalSales() {
        return orderRepo.findAll().stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public List<Report> getAllReports() {
        return reportRepo.findAll();
    }

    public Report generateMonthlyReport(String period) {
        double totalSales = getTotalSales(); // kamu bisa filter berdasarkan bulan di sini nanti
        Report report = Report.builder()
                .period(period)
                .totalSales(totalSales)
                .generatedDate(LocalDate.now())
                .build();
        return reportRepo.save(report);
    }
}
