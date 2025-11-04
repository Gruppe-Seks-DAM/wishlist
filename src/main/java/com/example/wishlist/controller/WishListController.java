package com.example.wishlist.controller;

import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // GET /wishlists/{id} - View single wishlist
    @GetMapping("/{id}")
    public String getWishlistById(@PathVariable Long id, Model model) {
        WishList wishlist = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found with id: " + id));
        model.addAttribute("wishlist", wishlist);
        // Add this when you have wishes

//         List<Wish> wishes = wishRepository.findByWishlistId(id);
//         model.addAttribute("wishes", wishes);
        return "wishlists/details"; // This will show the individual wishlist page
    }

    @GetMapping("/{id}/edit")
    public String editWishlistForm(@PathVariable Long id, Model model) {
        WishList wishlist = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        model.addAttribute("wishlist", wishlist);
        return "wishlists/edit";
    }

    @PatchMapping("/{id}")
    public String updateWishlistTitle(@PathVariable Long id,
                                      @RequestParam String title,
                                      RedirectAttributes redirectAttributes) {

        // Manual validation - simple and works
        if (title == null || title.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Titel må ikke være tom.");
            return "redirect:/wishlists/" + id + "/edit";
        }

        if (title.length() > 100) {
            redirectAttributes.addFlashAttribute("errorMessage", "Titel må være højst på 100 tegn");
            return "redirect:/wishlists/" + id + "/edit";
        }

        try {
            service.updateTitle(id, title);
            redirectAttributes.addFlashAttribute("successMessage", "Ønskelisten er opdateret");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fejl ved opdatering: " + e.getMessage());
        }
        return "redirect:/wishlists";
    }

    @DeleteMapping("/{id}")
    public String deleteWishlist(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ønskelisten er slettet");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fejl ved sletning: " + e.getMessage());
        }
        return "redirect:/wishlists";
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

    // Vis form for rediger ønske
    @GetMapping("/{wid}/wishes/{id}/edit")
    public String editWishForm(@PathVariable Long wid, @PathVariable Long id, Model model) {
        Wish wish = service.getWish(wid, id);
        model.addAttribute("wish", wish);
        model.addAttribute("wid", wid);
        return "editWish";
    }

    // PATCH /wishlists/{wid}/wishes/{id} - opdaterer et ønske
    @PatchMapping("/{wid}/wishes/{id}")
    public String updateWish(@PathVariable Long wid,
                             @PathVariable Long id,
                             @ModelAttribute("wish") Wish wish,
                             RedirectAttributes redirectAttributes) {
        try {
            wish.setId(id);
            wish.setWishlistId(wid);
            service.updateWish(wid, wish);
            redirectAttributes.addFlashAttribute("successMessage", "Ønske opdateret.");
            return "redirect:/wishlists/" + wid;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fejl ved opdatering: " + e.getMessage());
            return "redirect:/wishlists/" + wid + "/wishes/" + id + "/edit";
        }
    }
}