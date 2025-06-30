package com.example.ecommerce.service;

import com.example.ecommerce.model.Invoice;
import com.example.ecommerce.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public Invoice getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Invoice tidak ditemukan untuk order ID: " + orderId));
    }
}
