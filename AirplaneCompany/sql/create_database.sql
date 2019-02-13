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
INSERT INTO airports (name) VALUES ('Tokyo Haneda Airport');
INSERT INTO airports (name) VALUES ('London Heathrow Airport');
INSERT INTO airports (name) VALUES ('Frankfurt Airport');

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
INSERT INTO users (username, password, dateRegistration, role) VALUES ('pera', '123', '2018-9-2', 'REGISTERED_USER');

CREATE TABLE flights (
	id BIGINT AUTO_INCREMENT NOT NULL,
	numberF VARCHAR(10) NOT NULL,
	dateGoing DATE NOT NULL,
	dateComing DATE NOT NULL,
	goingAirport BIGINT NOT NULL,
	comingAirport BIGINT NOT NULL,
	seatNumber BIGINT NOT NULL,
	priceTicket DOUBLE NOT NULL,
	deleted BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY(id),
	FOREIGN KEY (goingAirport) REFERENCES airports(id) ON DELETE RESTRICT,
	FOREIGN KEY (comingAirport) REFERENCES airports(id) ON DELETE RESTRICT
);

INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('MF-21', '2018-11-1', '2018-12-7', 1, 2, 300, 1580.05);	
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('UO-74', '2018-10-6', '2018-11-7', 1, 3, 120, 1500.05);	
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('LS-45', '2018-12-9', '2018-12-18', 2, 1, 190, 1850.05);	
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('SV-36', '2018-11-12', '2018-12-30', 3, 1, 150, 1650.75);
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('PL-49', '2018-10-9', '2018-11-18', 4, 1, 260, 1878.05);	
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('ER-39', '2018-11-20', '2018-12-15', 1, 4, 350, 2450.75);
	
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('RF-45', '2019-3-10', '2019-3-17', 5, 4, 250, 2150.75);
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('GH-23', '2019-3-18', '2019-3-22', 4, 5, 170, 2145.75);
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('QW-67', '2019-4-15', '2019-4-25', 3, 4, 370, 1859.75);
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('TY-28', '2019-4-26', '2019-4-30', 4, 3, 260, 3259.75);
INSERT INTO flights (numberF, dateGoing, dateComing, goingAirport, comingAirport, seatNumber, priceTicket)
	VALUES ('YH-12', '2019-3-11', '2019-3-24', 1, 5, 410, 1259.75);

	
CREATE TABLE tickets (
	id BIGINT AUTO_INCREMENT,
	goingFlight BIGINT NOT NULL,
	reverseFlight BIGINT NOT NULL,
	seatOnGoingFlight BIGINT NOT NULL,
	seatOnReverseFlight BIGINT NOT NULL,
	dateReservation DATE,
	dateOfSaleTicket DATE,
	userCreateReservationOrSaleTicket BIGINT NOT NULL,
	firstNamePassenger VARCHAR(20) NOT NULL,
	lastNamePassenger VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (goingFlight) REFERENCES flights(id) ON DELETE RESTRICT,
	FOREIGN KEY (reverseFlight) REFERENCES flights(id) ON DELETE RESTRICT
);

INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket, userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)
	VALUES (1, 3, 5, 18, NULL, '2018-11-1', 1, 'Vladimir', 'Putin');
INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket, userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)
	VALUES (1, 3, 18, 78, NULL, '2018-11-1', 1, 'Nikola', 'Djuricko');
INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket, userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)
	VALUES (2, 4, 18, 23, NULL, '2018-10-6', 1, 'Zdravko', 'Colic');
INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket, userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)
	VALUES (2, 4, 96, 85, NULL, '2018-10-6', 1, 'Srecko', 'Sojic');
INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket, userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)
	VALUES (5, 6, 48, 74, NULL, '2018-10-9', 1, 'Dino', 'Merlin');
INSERT INTO tickets (goingFlight, reverseFlight, seatOnGoingFlight, seatOnReverseFlight, dateReservation, dateOfSaleTicket, userCreateReservationOrSaleTicket, firstNamePassenger, lastNamePassenger)
	VALUES (5, 6, 14, 36, NULL, '2018-10-9', 1, 'Voja', 'Brajovic');
