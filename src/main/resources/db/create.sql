SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS authors (
  client_id int PRIMARY KEY auto_increment,
  username VARCHAR,
  client_name VARCHAR,
  client_phone VARCHAR,
  password VARCHAR,
);