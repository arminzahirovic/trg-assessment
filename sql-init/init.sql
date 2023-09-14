CREATE DATABASE IF NOT EXISTS fleetservice;
CREATE DATABASE IF NOT EXISTS fleetsimulator;

USE fleetservice;

CREATE TABLE IF NOT EXISTS driver (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
 );

INSERT INTO driver(name)
VALUES
    ('Bill Maldonado'),
    ('Aliya Jensen'),
    ('Louis Valentine'),
    ('Lucia Nielsen'),
    ('Jean Randolph');

CREATE TABLE IF NOT EXISTS car (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    driver_id INT,
    CONSTRAINT FK_DRIVER_ID FOREIGN KEY (driver_id) REFERENCES driver (id),
    CONSTRAINT UC_Driver UNIQUE (driver_id)

);

INSERT INTO car(brand, model)
VALUES
    ('BMW', '3'),
    ('BMW', '5'),
    ('Audi', 'A3'),
    ('Audi', 'A4'),
    ('Mercedes', 'B'),
    ('Mercedes', 'C'),
    ('Volkswagen', 'Golf');

USE fleetsimulator;

CREATE TABLE state (
    car_id INT PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    speed DOUBLE NOT NULL,
    driver_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);