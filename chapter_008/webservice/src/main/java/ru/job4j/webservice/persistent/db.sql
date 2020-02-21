CREATE TABLE Roles (
  role_id serial primary key,
  name varchar(10)
);

CREATE TABLE Users (
  user_id serial primary key,
  role_id int references Role(Role_ID),
  photo_Id String,
  login varchar(50),
  email varchar(50),
  password varchar(50),
  created timestamp
);

INSERT INTO Roles(name) VALUES ('admin'),('user');

INSERT INTO Users(role_id, login, email, password, created)
VALUES
(1, 'Jack', 'jack@gmail.com', 'toor', 1570737000), -- 2019-10-10 22:50
(2, 'Mark', 'mark@gmail.com', '123', 1579031400), -- 2019-10-10 22:50
(2, 'John', 'john@gmail.com', '321', 1579290600); -- 2020-01-17 22:50

-- add user
INSERT INTO Users(role_id, login, email, password, created) VALUES (?, ?, ?, ?, ?)

-- get user
SELECT user_id, u.role_id, role, login, email, password, created
FROM users u JOIN roles r ON r.role_id = u.role_id