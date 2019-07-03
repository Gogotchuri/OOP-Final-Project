
CREATE TABLE users
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,

  first_name VARCHAR(64),
  last_name VARCHAR(64),

  email VARCHAR(128) UNIQUE,
  phone_number VARCHAR(32) UNIQUE,

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE process_statuses
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  status VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE deals
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  status_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (status_id) REFERENCES process_statuses(id),

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now()
);


CREATE TABLE item_categories
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64)NOT NULL UNIQUE
);


CREATE TABLE items
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  user_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade,

  item_category_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (item_category_id) REFERENCES item_categories(id),
  
  description VARCHAR(512),
  name VARCHAR(32) NOT NULL,

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE owned_items
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  deal_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (deal_id) REFERENCES deals(id) on delete cascade,
  item_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (item_id) REFERENCES items(id) on delete cascade,

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE wanted_items
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  deal_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (deal_id) REFERENCES deals(id) on delete cascade,
  
  item_category_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (item_category_id) REFERENCES item_categories(id),

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now()
);


CREATE TABLE cycles
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  status_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (status_id) REFERENCES process_statuses(id),

  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE offered_cycles
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  deal_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (deal_id) REFERENCES deals(id),

  cycle_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (cycle_id) REFERENCES cycles(id),
);


CREATE TABLE chats
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  
  cycle_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (cycle_id) REFERENCES cycles(id) on delete cascade,

  updated_at TIMESTAMP DEFAULT now()
);


CREATE TABLE messages
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  chat_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY(chat_id) REFERENCES chats(id) on delete cascade,

  body VARCHAR(512) NOT NULL,

  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE image_categories
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE item_images
(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,

  image_category_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY(image_category_id) REFERENCES image_categories(id),

  url VARCHAR(256) NOT NULL UNIQUE,
  user_id INT(6) UNSIGNED NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade,
  item_id INT(6) UNSIGNED DEFAULT NULL,
  FOREIGN KEY (item_id) REFERENCES items(id) on delete cascade,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE profile_images
(
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(256) NOT NULL UNIQUE,
    user_id INT(6) UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade,
    created_at TIMESTAMP DEFAULT now()
);
