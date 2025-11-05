package com.example.wishlist;

import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import com.example.wishlist.service.WishListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // ← Starts FULL application context
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishListIntegrationTest {

    @Autowired
    private WishListService service;      // ← Real service

    @Autowired
    private WishListRepository repository; // ← Real repository
    @Autowired
    private WishListRepository wishListRepository;

    //    Integration test
    @Test
    public void testCreateAndFindWishList() {
        // Don't set ID - let database auto-generate
        WishList wishlist = new WishList(null, "testTitle", LocalDateTime.now(), true, "shareToken");
        int ret = repository.createWishList(wishlist);
        assertEquals(1, ret);

        // Get all wishlists and find the one we just created
        List<WishList> found = service.getAllWishLists();
        WishList createdWishList = found.stream()
                .filter(wl -> "testTitle".equals(wl.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(createdWishList);
        assertEquals("testTitle", createdWishList.getTitle());
    }
}