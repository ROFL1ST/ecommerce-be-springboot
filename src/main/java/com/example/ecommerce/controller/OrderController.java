package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderItemResponse;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    // ✅ Endpoint: Lihat daftar pesanan user
    @GetMapping
    public List<OrderResponse> getUserOrders(Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        List<Order> orders = orderService.getOrders(user);
        return orders.stream().map(o -> new OrderResponse(
                o.getId(),
                o.getStatus(),
                o.getTotal(),
                o.getOrderTime(),
                o.getItems().stream()
                        .map(i -> new OrderItemResponse(i.getProduct(), i.getQuantity()))
                        .toList()
        )).toList();

    }

    // ✅ Endpoint: Checkout pesanan dari cart user
    @PostMapping("/checkout")
    public OrderResponse checkout(@RequestBody List<Long> cartItemIds, Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        return orderService.checkout(user, cartItemIds);
    }

}
