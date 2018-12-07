DROP SCHEMA IF EXISTS airplanes;
CREATE SCHEMA airplanes DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE airplanes;

CREATE TABLE airports (
	id BIGINT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY(id)
);

INSERT INTO airports (name) VALUES ('Airport Nikola Tesla Belgrade');
INSERT INTO airports (name) VALUES ('Airport Qatar Airways');

CREATE TABLE users (
	id BIGINT AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(25) NOT NULL,
	dateRegistration DATE NOT NULL,
	role ENUM('REGISTERED_USER', 'ADMIN') NOT NULL DEFAULT 'REGISTERED_USER',
	blocked BOOLEAN NOT NULL DEFAULT FALSE,
	deleted BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY(id)
);

INSERT INTO users (username, password, dateRegistration, role) VALUES ('mare', '123', '2018-6-3', 'ADMIN');
INSERT INTO users (username, password, dateRegistration, role) VALUES ('zare', '123', '2018-8-2', 'REGISTERED_USER');

CREATE TABLE flights (
	id BIGINT AUTO_INCREMENT,
	numberF VARCHAR(10) NOT NULL,
	dateGoing DATE NOT NULL,
	dateComing DATE NOT NULL,
	goingAirport BIGINT NOT NULL,
	comingAirport BIGINT NOT NULL,
	seatNumber BIGINT NOT NULL,
	priceTicket DOUBLE NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (goingAirport) REFERENCES airports(id) ON DELETE RESTRICT,
	FOREIGN KEY (comingAirport) REFERENCES airports(id) ON DELETE RESTRICT
);

INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('MF-21', '2018-6-1', '2018-6-7', 1, 2, 120, 1500.05);
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('LS-45', '2018-8-3', '2018-8-15', 2, 1, 120, 1850.05);