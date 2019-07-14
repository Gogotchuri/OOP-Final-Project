DELETE FROM image_categories WHERE true;

INSERT INTO image_categories(id, name)
VALUES (1, 'profile_image'),
       (2, 'featured');