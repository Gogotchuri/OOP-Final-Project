DELETE FROM item_categories WHERE true;

INSERT INTO item_categories(id, type_id, brand_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3);