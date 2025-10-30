package com.example.wishlist.repository;

import com.example.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbc;

    public WishlistRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void createWishlist(Wishlist wishlist) {
        String sql = """
                INSERT INTO wishlists (title, created_at, is_shared, share_token) 
                VALUES (?, ?,  ?, ?)
                              """;
        jdbc.update(sql,
                wishlist.getTitle(),
                wishlist.getCreatedAt(),
                wishlist.getIsShared(),
                wishlist.getShareToken());
    }
}
