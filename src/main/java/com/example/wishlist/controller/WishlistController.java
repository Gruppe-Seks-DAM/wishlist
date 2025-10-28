package com.example.wishlist.controller;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlists")
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    // Vis form
    @GetMapping("/new")
    public String createWishListForm(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "wishlists/new";
    }

    // Modtag og gem data i db fra form
    // kun titel for nu i US1.
    @PostMapping
    public String createWishlist(@Valid @ModelAttribute("wishlist") Wishlist wishlist,
                                 BindingResult result) {
        if (result.hasErrors()) return "wishlists/new";

        service.createWishlist(wishlist);
        return "redirect:/wishlists";
    }
}
