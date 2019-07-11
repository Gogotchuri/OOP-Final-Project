DELETE FROM item_categories WHERE true;

INSERT INTO item_categories(id, name, type_id, brand_id)
VALUES (1, 'item1', 1, 1),
       (2, 'item2', 2, 2),
       (3, 'item3', 3, 3);