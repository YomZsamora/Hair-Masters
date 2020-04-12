-- SET MODE PostgreSQL;

-- CREATE TABLE IF NOT EXISTS clients (
--   client_id int PRIMARY KEY auto_increment,
--   username VARCHAR,
--   clientName VARCHAR,
--   clientPhone VARCHAR,
--   password VARCHAR,
--   role VARCHAR,
-- );

-- CREATE TABLE IF NOT EXISTS stylists (
--   stylist_id int PRIMARY KEY auto_increment,
--   stylistEmail VARCHAR,
--   stylistName VARCHAR,
--   stylistPhone VARCHAR,
--   stylistDept VARCHAR,
--   password VARCHAR,
--   role VARCHAR,
-- );

-- CREATE TABLE IF NOT EXISTS bookings (
--   booking_id int PRIMARY KEY auto_increment,
--   booking_date DATE,
--   start_at TIME,
--   end_at TIME,
--   client int,
--   stylist int,
-- );



--CREATE DATABASE hair_masters;
\c hair_masters;
--CREATE TABLE clients (client_id SERIAL PRIMARY KEY,username VARCHAR,clientName VARCHAR, clientPhone VARCHAR,password VARCHAR,role VARCHAR);
--CREATE TABLE stylists (stylist_id SERIAL PRIMARY KEY, stylistEmail VARCHAR, stylistName VARCHAR, stylistPhone VARCHAR, stylistDept VARCHAR, password VARCHAR, role VARCHAR);
CREATE TABLE bookings (booking_id SERIAL PRIMARY KEY, booking_date DATE, start_at VARCHAR, end_at VARCHAR, client VARCHAR, stylist VARCHAR);
