package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final AuthService authService;

    @GetMapping
    public List<CartItemResponse> getCartItems(Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        List<CartItem> cartItems = cartService.getCartItemsByUser(user);

        return cartItems.stream()
                .map(cart -> new CartItemResponse(cart.getId(), cart.getProduct(), cart.getQuantity()))
                .toList();
    }



    @PostMapping
    public CartItemResponse addToCart(
            @RequestParam Long productId,
            @RequestParam int qty,
            Principal principal
    ) {
        User user = authService.getUserFromPrincipal(principal);
        return cartService.addToCart(user, productId, qty);
    }

    @PutMapping("/{cartItemId}")
    public CartItemResponse updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam int qty
    ) {
        return cartService.updateCart(cartItemId, qty);
    }


    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartItemId, Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        cartService.removeItem(user, cartItemId);
        return ResponseEntity.ok("Item berhasil dihapus dari keranjang.");
    }

}