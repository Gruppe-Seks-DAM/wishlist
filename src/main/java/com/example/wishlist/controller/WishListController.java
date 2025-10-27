package com.example.wishlist.controller;

import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/wishlists")
public class WishListController {

    private final WishListService service;

    public WishListController(WishListService service) {
        this.service = service;
    }

    // GET /wishlists
    @GetMapping
    public ResponseEntity<ArrayList<WishList>> getAllWishLists() {
        return ResponseEntity.ok(service.getAllWishLists());
    }
}
