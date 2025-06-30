package com.example.ecommerce.controller;

import com.example.ecommerce.dto.InvoiceResponse;
import com.example.ecommerce.dto.OrderItemResponse;
import com.example.ecommerce.model.Invoice;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.InvoiceService;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final AuthService authService;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getInvoice(@PathVariable Long orderId, Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        Order order = orderService.getOrderById(orderId);

        if (!order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("Tidak diizinkan mengakses invoice ini.");
        }

        Invoice invoice = invoiceService.getInvoiceByOrderId(orderId);

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(i -> new OrderItemResponse(i.getProduct(), i.getQuantity()))
                .toList();

        InvoiceResponse response = new InvoiceResponse(
                invoice.getId(),
                order.getId(),
                order.getStatus(),
                order.getOrderTime(),
                itemResponses,
                invoice.getTotal(),
                invoice.getTax(),
                invoice.getShipping(),
                invoice.getIssueDate()
        );

        return ResponseEntity.ok(response);
    }

}
