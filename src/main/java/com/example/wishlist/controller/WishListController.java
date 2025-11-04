package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishRepository;
import com.example.wishlist.service.WishListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/wishlists")
public class WishListController {
    private final WishListService service;
    private final WishRepository wishRepository;

    public WishListController(WishListService service, WishRepository wishRepository) {
        this.service = service;
        this.wishRepository = wishRepository;
    }

    // GET /wishlists
    @GetMapping
    public String getAllWishLists(Model model) {
        ArrayList<WishList> wishlist = service.getAllWishLists();
        model.addAttribute("wishlists", wishlist != null ? wishlist : new ArrayList<>());
        return "wishlists";
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


    @GetMapping("/{wishlistId}/wishes/{wishId}/edit")
    public String editWishForm(@PathVariable Long wishlistId,
                               @PathVariable Long wishId,
                               Model model) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new RuntimeException("Wish not found"));
        model.addAttribute("wish", wish);
        return "wishlists/editWish";
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
            wishRepository.save(wish);
            redirectAttributes.addFlashAttribute("successMessage", "Ønske opdateret.");
            return "redirect:/wishlists/" + wid;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fejl ved opdatering: " + e.getMessage());
            return "redirect:/wishlists/" + wid + "/wishes/" + id + "/edit";
        }
    }

    // GET /wishlists/{id} - View single wishlist WITH wishes
    @GetMapping("/{id}")
    public String getWishlistById(@PathVariable Long id, Model model) {
        WishList wishlist = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found with id: " + id));

        // Get wishes for this wishlist
        List<Wish> wishes = wishRepository.findByWishlistId(id);

        // Add empty wish for the form
        Wish newWish = new Wish();
        newWish.setWishlistId(id);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wishes", wishes);
        model.addAttribute("newWish", newWish); // For the form

        return "wishlists/details";
    }

    // POST /wishlists/{id}/wishes - Add new wish
    @PostMapping("/{id}/wishes")
    public String addWishToWishlist(@PathVariable Long id,
                                    @Valid @ModelAttribute("newWish") Wish wish,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {

        // Debug: Print the wish object to see what's being submitted
        System.out.println("Received wish: " + wish.getTitle() + ", ID: " + wish.getId());

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Valideringsfejl: " +
                    result.getFieldError().getDefaultMessage());
            return "redirect:/wishlists/" + id;
        }

        try {
            // Ensure the wishlistId is set and ID is null (for new wish)
            wish.setWishlistId(id);
            wish.setId(null); // Force new ID
            wishRepository.save(wish);
            redirectAttributes.addFlashAttribute("successMessage", "Ønske tilføjet!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fejl ved tilføjelse: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/wishlists/" + id;
    }

    // DELETE /wishlists/{wishlistId}/wishes/{wishId} - Remove wish
    @DeleteMapping("/{wishlistId}/wishes/{wishId}")
    public String deleteWish(@PathVariable Long wishlistId,
                             @PathVariable Long wishId,
                             RedirectAttributes redirectAttributes) {
        try {
            wishRepository.delete(wishId);
            redirectAttributes.addFlashAttribute("successMessage", "Ønske slettet!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fejl ved sletning: " + e.getMessage());
        }
        return "redirect:/wishlists/" + wishlistId;
    }
}