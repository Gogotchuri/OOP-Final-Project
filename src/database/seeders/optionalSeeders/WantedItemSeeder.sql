DELETE FROM wanted_items WHERE true;

INSERT INTO wanted_items(id, deal_id, item_category_id)
VALUES (1, 1, 1),
       (2, 2, 4),
       (3, 3, 3);