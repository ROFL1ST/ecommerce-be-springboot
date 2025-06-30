package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(User user);
}