SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS authors (
  client_id int PRIMARY KEY auto_increment,
  username VARCHAR,
  clientName VARCHAR,
  clientPhone VARCHAR,
  password VARCHAR,
);