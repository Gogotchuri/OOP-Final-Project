DELETE FROM owned_items WHERE true;

INSERT INTO owned_items(id, deal_id, item_id)
VALUES (1, 1, 3),
       (2, 2, 1),
       (3, 3, 4);