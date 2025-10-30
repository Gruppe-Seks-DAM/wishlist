package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbc;

    public WishListRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public ArrayList<WishList> getAllWishLists(){
        String sql_query = """
                SELECT * FROM wishlists;
                """;

        return (ArrayList<WishList>) jdbc.query(sql_query, (rs, i) ->
                new WishList(rs.getLong("id"),rs.getString("title"), rs.getDate("createdAt").toLocalDate().atStartOfDay(),rs.getBoolean("isShared"),rs.getString("shareToken"))
        );
    }

    public void createWishList(WishList wishlist) {
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
