package com.example.wishlist.controller;

import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String getAllWishLists(Model model) {
        ArrayList<WishList> wishlist = service.getAllWishLists();
        model.addAttribute("wishlists", wishlist != null ? wishlist : new ArrayList<>());

        return "wishlists";
    }

    // Vis form
    @GetMapping("/new")
    public String createWishListForm(Model model) {
        model.addAttribute("wishlist", new WishList());
        return "wishlists/new";
    }

    // Modtag og gem data i db fra form
    @PostMapping
    public String createWishList(@Valid @ModelAttribute("wishlist") WishList wishlist,
                                 BindingResult result) {
        if (result.hasErrors()) return "wishlists/new";

        service.createWishList(wishlist);
        return "redirect:/wishlists"; // liste oversigt (US2)
    }
}
