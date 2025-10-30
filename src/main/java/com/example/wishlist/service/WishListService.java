package com.example.wishlist.service;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WishListService {
    private final WishListRepository repository;

    public WishListService(WishListRepository repository) {
        this.repository = repository;
    }

    public ArrayList<WishList> getAllWishLists(){
        return repository.getAllWishLists();
    }

    public void createWishList(WishList wishlist) {

        wishlist.setCreatedAt(LocalDateTime.now());
        wishlist.setIsShared(false);
        wishlist.setShareToken(UUID.randomUUID().toString());

        repository.createWishList(wishlist);
    }
}
