package com.example.ecommerce.service;

import com.example.ecommerce.dto.WishlistResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishlistItem;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistItemRepository repo;
    private final ProductRepository productRepo;

    public List<WishlistItem> getWishlistByUser(User user) {
        return repo.findByUser(user);
    }


    public WishlistResponse add(User user, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        WishlistItem item = WishlistItem.builder()
                .user(user)
                .product(product)
                .build();
        WishlistItem saved = repo.save(item);
        return new WishlistResponse(saved.getId(), saved.getProduct());
    }

    public void remove(Long id) {
        repo.deleteById(id);
    }
}
