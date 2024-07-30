CREATE DATABASE rubrica;

USE rubrica;

CREATE TABLE persone
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(50),
    cognome   VARCHAR(50),
    indirizzo VARCHAR(100),
    telefono  VARCHAR(20),
    eta       INT
);

CREATE TABLE utenti
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO utenti (username, password)
VALUES ('admin', 'admin');
