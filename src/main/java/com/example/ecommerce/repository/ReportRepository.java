package com.example.ecommerce.repository;

import com.example.ecommerce.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
