DROP TABLE Users;
DROP TABLE Roles;
--add roles
CREATE TABLE Roles (
   roleId serial primary key,
   role varchar(10)
);

-- add users
CREATE TABLE Users (
   userId serial primary key,
   roleId int references Roles(roleId),
   photo bytea,
   login varchar(50) UNIQUE,
   email varchar(50) UNIQUE,
   password varchar(50),
   created timestamp
);

-- set tables as unique
-- ALTER TABLE users ADD UNIQUE ("login" , "email");

INSERT INTO Roles(role) VALUES ('admin'),('user');

INSERT INTO Users(roleId, login, email, password, created)
VALUES
(1, 'Jack', 'jack@gmail.com', 'toor', '2019-10-10 22:50'),
(2, 'Mark', 'mark@gmail.com', '123', '2019-10-10 22:50'),
(2, 'John', 'john@gmail.com', '321', '2020-01-17 22:50');

-- add user
-- INSERT INTO Users(roleId, login, email, password, created) VALUES (?, ?, ?, ?, ?)

-- get user
-- SELECT userId, u.roleId, role, login, email, password, created
-- FROM users u JOIN roles r ON r.roleId = u.roleId