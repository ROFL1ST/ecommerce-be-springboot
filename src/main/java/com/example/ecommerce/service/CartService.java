package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public List<CartItem> getCart(User user) {
        return cartRepo.findByUser(user);
    }
    public List<CartItem> getCartItemsByUser(User user) {
        return cartRepo.findByUser(user);
    }

    public CartItemResponse addToCart(User user, Long productId, int qty) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        // Cek apakah cart item untuk produk ini sudah ada
        CartItem item = cartRepo.findByUserAndProduct(user, product)
                .orElse(CartItem.builder()
                        .user(user)
                        .product(product)
                        .quantity(0)
                        .build());

        item.setQuantity(item.getQuantity() + qty);
        CartItem saved = cartRepo.save(item);

        return new CartItemResponse(saved.getId(), saved.getProduct(), saved.getQuantity());
    }

    public CartItemResponse updateCart(Long cartItemId, int qty) {
        CartItem cartItem = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item tidak ditemukan"));
        if (qty < 1) {
            throw new RuntimeException("Jumlah minimal 1.");
        }
        if (qty > cartItem.getProduct().getStock()) {
            throw new RuntimeException("Stok tidak mencukupi.");
        }

        cartItem.setQuantity(qty);
        CartItem saved = cartRepo.save(cartItem);

        return new CartItemResponse(saved.getId(), saved.getProduct(), saved.getQuantity());
    }


    public void removeItem(User user, Long cartItemId) {
        CartItem item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item tidak ditemukan"));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Kamu tidak diizinkan menghapus item ini.");
        }

        cartRepo.delete(item);
    }

}