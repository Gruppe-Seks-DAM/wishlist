package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

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
                new WishList(rs.getLong("id"),
                        rs.getString("title"),
                        rs.getDate("created_at").toLocalDate().atStartOfDay(),
                        rs.getBoolean("is_shared"),
                        rs.getString("share_token"))
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

    public Optional<WishList> findById(Long id) {
        String sql = """
            SELECT id, title, created_at, is_shared, share_token 
            FROM wishlists 
            WHERE id = ?
            """;

        try {
            WishList wishlist = jdbc.queryForObject(sql, new WishListRowMapper(), id);
            return Optional.ofNullable(wishlist);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean updateTitle(Long id, String newTitle) {
        String sql = "UPDATE wishlists SET title = ? WHERE id = ?";
        int affectedRows = jdbc.update(sql, newTitle, id);
        return affectedRows > 0;
    }

    public boolean delete(Long id) {
        // First delete associated wishes (due to foreign key constraints)
        String deleteWishesSql = "DELETE FROM wishes WHERE wishlist_id = ?";
        jdbc.update(deleteWishesSql, id);

        // Then delete the wishlist
        String deleteWishlistSql = "DELETE FROM wishlists WHERE id = ?";
        int affectedRows = jdbc.update(deleteWishlistSql, id);
        return affectedRows > 0;
    }

    // RowMapper for WishList
    private static class WishListRowMapper implements RowMapper<WishList> {
        @Override
        public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
            WishList wishlist = new WishList();
            wishlist.setId(rs.getLong("id"));
            wishlist.setTitle(rs.getString("title"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                wishlist.setCreatedAt(createdAt.toLocalDateTime());
            }

            wishlist.setIsShared(rs.getBoolean("is_shared"));

            String shareToken = rs.getString("share_token");
            wishlist.setShareToken(shareToken);

            return wishlist;
        }
    }

//    public boolean update(Long wid, Wish w) {
//        String sql = """
//      UPDATE wishes
//      SET name = ?, description = ?, url = ?, price = ?
//      WHERE id = ? AND wishlist_id = ?
//    """;
//        int rows = jdbc.update(sql,
//                w.getName(), w.getDescription(), w.getUrl(), w.getPrice(),
//                w.getId(), wid);
//        return rows > 0;
//    }

//    public boolean delete(Long wid, Long id) {
//        String sql = "DELETE FROM wishes WHERE id = ? AND wishlist_id = ?";
//        return jdbc.update(sql, id, wid) > 0;
//    }
}
