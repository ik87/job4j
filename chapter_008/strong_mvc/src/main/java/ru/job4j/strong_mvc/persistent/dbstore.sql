-- create roles
CREATE TABLE Roles
(
    id   serial primary key,
    name varchar(50)
);

-- create users
CREATE TABLE Users
(
    id      serial primary key,
    role_id int references Roles (id),
    name    varchar(50),
    login   varchar(50),
    password varchar(50),
    email   varchar(50),
    created varchar(50),
    photoId varchar(50)
);

-- insert data into roles
INSERT INTO Roles(name) VALUES ('Admin'), ('User');

-- add admin
INSERT INTO Users(name, role_id, login, email, password)
VALUES ('IK', 1, 'admin', 'd_dexter@mail.ru', 'root')