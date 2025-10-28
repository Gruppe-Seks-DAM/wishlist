package com.example.wishlist.service;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WishlistService {
    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public void createWishlist(Wishlist wishlist) {

        wishlist.setCreatedAt(LocalDateTime.now());
        wishlist.setIsShared(false);
        wishlist.setShareToken(UUID.randomUUID().toString());

        repository.createWishlist(wishlist);
    }
}
