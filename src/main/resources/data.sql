INSERT INTO wishlists (title, created_at, is_shared, share_token)
VALUES
    ('Test 1', '2025-12-24 12:00:00', 'TRUE', 'test1share'),
    ('Test 2', '2024-12-24 12:00:00', 'FALSE', 'test2share');

INSERT INTO wishlists (title, created_at, share_token)
VALUES
    ('Test 3', '2030-12-24 12:00:00', 'test3share');

INSERT INTO wishlists (title, is_shared, share_token) VALUES
    ('Fødselsdagsønsker', TRUE, 'abc123'),
    ('Juleønsker', FALSE, NULL),
    ('Wishlist til sommer', TRUE, 'def456');

INSERT INTO wishes (title, price, url, wishlist_id) VALUES
    ('Ny smartphone', 5999.00, 'https://example.com/phone', 1),
    ('Bog om programming', 299.95, 'https://example.com/book', 1),
    ('Træningsudstyr', 1500.00, NULL, 2);
