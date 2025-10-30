package com.example.wishlist.service;

import com.example.wishlist.model.WishList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishListService {

    public ArrayList<WishList> getAllWishLists() {
        ArrayList<WishList> wishLists = new ArrayList<>();
        wishLists.add(new WishList("Birthday"));
        wishLists.add(new WishList("Christmas"));
        return wishLists;
    }
}
