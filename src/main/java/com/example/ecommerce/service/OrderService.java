package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderItemResponse;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final InvoiceRepository invoiceRepository;

    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));
    }
    public List<Order> getOrders(User user) {
        return orderRepo.findByUser(user);
    }

    public OrderResponse checkout(User user, List<Long> selectedCartItemIds) {
        List<CartItem> items = cartRepo.findByUser(user);

        List<CartItem> selectedItems = items.stream()
                .filter(cart -> selectedCartItemIds.contains(cart.getId()))
                .toList();

        if (selectedItems.isEmpty()) {
            throw new RuntimeException("Tidak ada item yang dipilih untuk checkout.");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cart : selectedItems) {
            Product product = cart.getProduct();
            if (product.getStock() < cart.getQuantity()) {
                throw new RuntimeException("Stok tidak cukup untuk produk: " + product.getName());
            }

            product.setStock(product.getStock() - cart.getQuantity());
            productRepo.save(product);

            orderItems.add(OrderItem.builder()
                    .product(product)
                    .price(product.getPrice())
                    .quantity(cart.getQuantity())
                    .build());

            total += product.getPrice() * cart.getQuantity();
        }

        Order order = Order.builder()
                .user(user)
                .orderTime(LocalDateTime.now())
                .status("PENDING")
                .total(total)
                .items(orderItems)
                .build();

        cartRepo.deleteAll(selectedItems);
        Order savedOrder = orderRepo.save(order);

        Invoice invoice = Invoice.builder()
                .order(savedOrder)
                .total(savedOrder.getTotal())
                .tax(savedOrder.getTotal() * 0.1)
                .shipping(15000.0)
                .issueDate(LocalDate.now())
                .build();

        invoiceRepository.save(invoice);

        // Build response DTO
        List<OrderItemResponse> itemResponses = orderItems.stream()
                .map(item -> new OrderItemResponse(item.getProduct(), item.getQuantity()))
                .toList();

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getStatus(),
                savedOrder.getTotal(),
                savedOrder.getOrderTime(),
                itemResponses
        );
    }

}
