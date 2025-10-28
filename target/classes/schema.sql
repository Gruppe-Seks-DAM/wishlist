CREATE TABLE WISHLISTS (

    id BIGINT AUTO_INCREMENT primary key,
    title varchar(100) not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_shared boolean DEFAULT FALSE,
    share_token varchar(36)
);

