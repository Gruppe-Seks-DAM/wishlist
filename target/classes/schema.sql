CREATE TABLE WISHLISTS (

    id BIGINT AUTO_INCREMENT primary key,
    title varchar(100) not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_shared boolean DEFAULT FALSE,
    share_token varchar(36)
);

CREATE TABLE IF NOT EXISTS WISHES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(500),
    price DECIMAL(10,2),
    wishlist_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE
);