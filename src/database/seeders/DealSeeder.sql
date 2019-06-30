DELETE FROM deals WHERE true;

INSERT INTO deals(id, user_id, status_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 2, 2),
       (4, 3, 1),
       (5, 3, 1);