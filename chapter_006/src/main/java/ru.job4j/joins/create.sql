CREATE DATABASE task_1733;
\c task_1733

CREATE TABLE Carbody (
	Id serial PRIMARY KEY,
	Name varchar(50)
);

CREATE TABLE Engine(
	Id serial PRIMARY KEY,
	Name varchar(50)
);

CREATE TABLE Transmission (
	Id serial PRIMARY KEY,
	Name varchar(50)
);

CREATE TABLE Car (
	Id serial PRIMARY KEY,
	Name varchar(50),
	Engine_Id int REFERENCES Engine(Id),
	Carbody_Id int REFERENCES Carbody(Id),
	Transmission_Id int REFERENCES Transmission(Id)
);

INSERT INTO Carbody(Name) VALUES (''E30''), (''E36''), (''E46''), (''E34''), (''E39''), (''E60''),  (''E28''), (''E30'');
INSERT INTO Engine(Name) VALUES (''1.6-2.0 L M10 I4''), (''2.0-2.3 L M20 I6''), (''2.0 L N46 I4''), (''2.0-2.8 L M52 I6''), (''2.5 L M51 turbo 6-cyl'');
INSERT INTO Transmission(Name) VALUES (''MT''), (''AT''), (''Robot'');
INSERT INTO Car(Name, Carbody_Id, Engine_Id, Transmission_Id)
VALUES (''BMW 3 Series'', 1, 1, 1), (''BMW 3 Series'', 2, 2, 2), (''BMW 3 Series'', 3, 2, 2),
(''BMW 5 Series'', 4, 2, 1), (''BMW 5 Series'', 5, 3, 2), (''BMW 5 Series'', 6, 4, 2);