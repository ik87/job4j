--create new database tracker
CREATE DATABASE task_1093;

--use tracker
\c task_1093;

--create tables
CREATE TABLE Roles (
  Roles_ID serial primary key,
  Name varchar(50)
);

CREATE TABLE Role (
  Role_ID serial primary key,
  Name varchar(10)
);


CREATE TABLE Role_bridge (
   Role_bridge_ID serial primary key,
  Role_ID int references Role(Role_ID),
  Roles_ID int references Roles(Roles_ID)
);


CREATE TABLE Users (
  User_ID serial primary key,
  Role_ID int references Role(Role_ID),
  FirstName varchar(50),
  LastName varchar(50),
  Email varchar(50)
);

CREATE TABLE Category (
  Category_ID serial primary key,
  Name varchar(50)
);

CREATE TABLE States (
  State_ID serial primary key,
  Name varchar(50)
);

CREATE TABLE Item (
  Item_ID serial primary key,
  Name varchar(50),
  User_ID int references Users(User_ID),
  Category_ID int references Category(Category_ID),
  State_ID int references States(State_ID)
);

CREATE TABLE Comments (
  Comment_ID serial primary key,
  Text text,
  Item_ID int references Item(Item_ID)
);

CREATE TABLE Attach (
  Attach_ID serial primary key,
  File_name varchar(50),
  Item_ID int references Item(Item_ID)
);

--fill tables
--insert into Role,  Roles and join together use Role_bridge 
INSERT INTO Role(Name) VALUES ('Admin'), ('User');
INSERT INTO Roles (Name) VALUES ('Create'), ('Delete');
INSERT INTO Role_bridge (Role_ID, Roles_ID) VALUES (1, 1), (2, 1), (2, 2);
--insert into Users
INSERT INTO Users (FirstName, LastName, Role_ID , Email)
VALUES ('John', 'Smith', 1, 'john@gmail.com'), ('Dan', 'Brown', 2, 'brown@gmail.com');
--insert into Category and State
INSERT INTO Category (Name) VALUES ('Remark'), ('suggestion');
INSERT INTO States (Name) VALUES ('open'), ('close');
--Insert into Item
INSERT INTO Item (Name, User_ID, Category_ID, State_ID) 
VALUES ('How about ...', 1, 2, 1), ('I found some poblems ...', 2, 2, 1), ('I think it wont be work ...', 2, 1, 2);
--Insert into comments
INSERT INTO Comments (Text, Item_ID) 
VALUES ('Yes its greate Idea!', 1), ('I know. Im working on it', 2);
--Insert into attach
INSERT INTO Attach (File_name, Item_ID) VALUES ('image.jpg', 2);







