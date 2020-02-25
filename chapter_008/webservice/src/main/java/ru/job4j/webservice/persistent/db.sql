DROP TABLE Users;
DROP TABLE Roles;
--add roles
CREATE TABLE Roles (
   role_id serial primary key,
   role varchar(10)
);

-- add users
CREATE TABLE Users (
   user_id serial primary key,
   role_id int references Roles(role_id),
   photo bytea,
   login varchar(50) UNIQUE,
   email varchar(50) UNIQUE,
   password varchar(50),
   created timestamp
);

-- set tables as unique
-- ALTER TABLE users ADD UNIQUE ("login" , "email");

INSERT INTO Roles(role) VALUES ('admin'),('user');

INSERT INTO Users(role_id, login, email, password, created)
VALUES
(1, 'Jack', 'jack@gmail.com', 'toor', '2019-10-10 22:50'),
(2, 'Mark', 'mark@gmail.com', '123', '2019-10-10 22:50'),
(2, 'John', 'john@gmail.com', '321', '2020-01-17 22:50');

-- add user
-- INSERT INTO Users(role_id, login, email, password, created) VALUES (?, ?, ?, ?, ?)

-- get user
-- SELECT user_id, u.role_id, role, login, email, password, created
-- FROM users u JOIN roles r ON r.role_id = u.role_id