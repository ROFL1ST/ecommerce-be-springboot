package com.example.ecommerce.controller;


import com.example.ecommerce.dto.WishlistResponse;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishlistItem;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final AuthService authService;

    @GetMapping
    public List<WishlistResponse> getWishlist(Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        List<WishlistItem> wishlists = wishlistService.getWishlistByUser(user);

        return wishlists.stream()
                .map(w -> new WishlistResponse(w.getId(), w.getProduct()))
                .toList();
    }

    @PostMapping
    public WishlistResponse add(@RequestParam Long productId, Principal principal) {
        User user = authService.getUserFromPrincipal(principal);
        return wishlistService.add(user, productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        wishlistService.remove(id);
        return ResponseEntity.ok("Wishlist dihapus.");
    }
}
