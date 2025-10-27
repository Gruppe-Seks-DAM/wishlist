package com.example.wishlist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlists")
public class WishListController {

    // GET /wishlists
    @GetMapping
    public ResponseEntity<ArrayList<WishLists>> getAllWishLists() {
        return ResponseEntity.ok(service.getAllWishLists());
    }
}
