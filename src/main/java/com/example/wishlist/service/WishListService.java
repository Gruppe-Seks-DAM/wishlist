package com.example.wishlist.service;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
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

    public Optional<WishList> findById(Long id) {
        return repository.findById(id);
    }

    public void updateTitle(Long id, String newTitle) {
        boolean updated = repository.updateTitle(id, newTitle);
        if (!updated) {
            throw new RuntimeException("Wishlist not found with id: " + id);
        }
    }

    public void delete(Long id) {
        boolean deleted = repository.delete(id);
        if (!deleted) {
            throw new RuntimeException("Wishlist not found with id: " + id);
        }
    }

    public void updateWish(Long wid, Wish wish) {
        boolean updated = repo.update(wid, wish);
        if (!updated) {
            throw new RuntimeException("Kunne ikke opdatere ønsket med id: " + wish.getId());
        }
    }
}
