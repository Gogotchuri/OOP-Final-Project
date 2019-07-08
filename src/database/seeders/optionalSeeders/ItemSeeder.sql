DELETE FROM items WHERE true;

INSERT INTO items(id, user_id, item_category_id, description, name)
VALUES (1, 1, 1, 'phone in a good condition', 'samsung phone'),
       (2, 1, 2, 'nice car', 'Tesla car'),
       (3, 1, 3, 'literally just a duck, don\'t ask', 'quacky duck'),
       (4, 3, 4, 'refrigerator to keep your condoms cool', 'cooler 3000'),
       (5, 1, 5, 'anti-baby matter, AKA condoms', 'magnum dong'),
       (6, 2, 1, 'phone in a good condition', 'samsung phone'),
       (7, 2, 4, 'refrigerator to keep your condoms cool', 'cooler 3000'),
       (8, 2, 6, '420', 'sum gud shit youknowwhatimean?'),
       (9, 2, 7, 'laptop that can only go to porn sites', 'laptop brazzers-69'),
       (10, 3, 6, '420', 'sum gud shit youknowwhatimean?'),
       (11, 3, 7, 'laptop to watch some porn', 'laptop brazzers-69'),
       (12, 3, 8, 'ice cream, in a melted condition', 'GURJAANIS NAYINI'),
       (13, 3, 9, 'just some scissors', 'krichi-krichi-9001'),
       (14, 3, 10, 'fanny pack, for all your weed containment needs', 'whats-inside-911'),
       (15, 3, 3, 'literally just a duck, don\'t ask', 'quacky duck');




/* DEAL CYCLE HERE:
   USER 1 WANTS: ITEM 1
   USER 1 HAS: ITEM 3
   USER 2 WANTS: ITEM 4
   USER 2 HAS: ITEM 1
   USER 3 WANTS: ITEM 3
   USER 3 HAS: ITEM 4



 */