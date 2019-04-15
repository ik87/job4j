--1. Вывести список всех машин и все привязанные к ним детали.
SELECT c.name car, e.name engine, b.name body, t.name transmission
FROM car as c
INNER JOIN engine as e ON e.id = c.engine_id
INNER JOIN carbody as b ON b.id = c.carbody_id
INNER JOIN transmission as t ON t.id = c.transmission_id;

--2. Вывести отдельно детали, которые не используются в машине, кузова, двигатели, коробки передач.
--Двигатель
SELECT e.name engine
FROM car as c
RIGHT JOIN engine as e ON e.id = c.engine_id WHERE c.id is NULL;
--Рама
SELECT b.name carbody
FROM car as c
RIGHT JOIN carbody as b ON b.id = c.carbody_id WHERE c.id is NULL;
--Коробка передач
SELECT t.name transmission
FROM car as c
RIGHT JOIN transmission as t ON t.id = c.transmission_id WHERE c.id is NULL;