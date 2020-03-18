SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS clients (
  client_id int PRIMARY KEY auto_increment,
  username VARCHAR,
  clientName VARCHAR,
  clientPhone VARCHAR,
  password VARCHAR,
  role VARCHAR,
);

CREATE TABLE IF NOT EXISTS stylists (
  stylist_id int PRIMARY KEY auto_increment,
  stylistEmail VARCHAR,
  stylistName VARCHAR,
  stylistPhone VARCHAR,
  stylistDept VARCHAR,
  password VARCHAR,
  role VARCHAR,
);