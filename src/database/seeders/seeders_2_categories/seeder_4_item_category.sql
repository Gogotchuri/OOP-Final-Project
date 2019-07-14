DELETE FROM item_categories WHERE true;

INSERT INTO item_categories(id, name, type_id, brand_id)
VALUES  (1, 'prius', 1, 1),
        (2, 'freezer3000', 2, 2),
        (3, 'cola flavored', 3, 3),
        (4, 'iphone 23', 4, 4),
        (5, 'high heel shoes', 5, 5),
        (6, 'baguette', 6, 6),
        (7, 'killer flavor', 7, 7),
        (8, 'coca-cola cherry', 8, 8),
        (9, 'audi RS', 1, 9),
        (10, 'chili flavored', 9, 10),
        (11, 'iphone 23S', 4, 4),
        (12, 'iphone 23SSSSSSSSSSSSSSSSSSSSSS', 4, 4);