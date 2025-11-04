package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> findByWishlistId(Long wishlistId) {
        String sql = "SELECT * FROM wishes WHERE wishlist_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new WishRowMapper(), wishlistId);
    }

    public void save(Wish wish) {
        if (wish.getId() == null) {
            insert(wish);
        } else {
            update(wish);
        }
    }

    private void insert(Wish wish) {
        String sql = """
            INSERT INTO wishes (title, price, url, wishlist_id, created_at) 
            VALUES (?, ?, ?, ?, NOW())
            """;

        jdbcTemplate.update(sql,
                wish.getTitle(),
                wish.getPrice(),
                wish.getUrl(),
                wish.getWishlistId());
    }

    private void update(Wish wish) {
        String sql = """
            UPDATE wishes 
            SET title = ?, price = ?, url = ? 
            WHERE id = ?
            """;

        jdbcTemplate.update(sql,
                wish.getTitle(),
                wish.getPrice(),
                wish.getUrl(),
                wish.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM wishes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Wish> findById(Long id) {
        String sql = "SELECT * FROM wishes WHERE id = ?";
        try {
            Wish wish = jdbcTemplate.queryForObject(sql, new WishRowMapper(), id);
            return Optional.ofNullable(wish);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class WishRowMapper implements RowMapper<Wish> {
        @Override
        public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
            Wish wish = new Wish();
            wish.setId(rs.getLong("id"));
            wish.setTitle(rs.getString("title"));
            wish.setPrice(rs.getBigDecimal("price"));
            wish.setUrl(rs.getString("url"));
            wish.setWishlistId(rs.getLong("wishlist_id"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                wish.setCreatedAt(createdAt.toLocalDateTime());
            }
            return wish;
        }
    }
}