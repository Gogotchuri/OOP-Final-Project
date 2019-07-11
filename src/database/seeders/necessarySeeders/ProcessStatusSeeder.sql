DELETE FROM process_statuses WHERE true;

INSERT INTO process_statuses(id, status)
VALUES (1, 'ongoing'),
       (2, 'completed'),
       (3, 'waiting');