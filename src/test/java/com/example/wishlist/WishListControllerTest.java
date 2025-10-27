package com.example.wishlist;

import com.example.wishlist.controller.WishListController;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishListController.class)
public class WishListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishListService service;

    @Test
    public void testGetAllWishListsViewAndModel() throws Exception {
        List<WishList> mockList = List.of(new WishList("Test List"));
        Mockito.when(service.getAllWishLists()).thenReturn(new ArrayList<>(mockList));

        mockMvc.perform(get("/wishlists"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("wishlists"))
                .andExpect(model().attribute("wishlists", mockList))
                .andExpect(view().name("wishlist-view"));
    }
}
