DELETE FROM process_statuses WHERE true;

INSERT INTO process_statuses(id, status)
VALUES (1, 'active'),
       (2, 'done');