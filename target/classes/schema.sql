CREATE TABLE WISHLISTS (

    id BIGINT AUTO_INCREMENT primary key,
    title varchar(100) not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_shared boolean DEFAULT FALSE,
    share_token varchar(36)
);

CREATE TABLE IF NOT EXISTS wishes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) CHECK (price >= 0),
    url VARCHAR(500),
    wishlist_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE
);