--1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT p.name FROM product as p INNER JOIN types as t ON t.id = p.type_id WHERE t.name = 'СЫР';
--2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT name FROM product WHERE name LIKE '%мороженное%';
--3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT p.name FROM product as p INNER JOIN types as t ON t.id = p.type_id
WHERE p.expired_date
BETWEEN date_trunc('month', now() + interval '1 month') -- 01.05.2019
AND date_trunc('month', now() + interval '1 month') + interval '1 month'; -- 01.06.2019
--4. Написать запрос, который выводит самый дорогой продукт.
SELECT p.name, p.price FROM product as p INNER JOIN types as t ON t.id = p.type_id ORDER BY p.price DESC LIMIT 1;
--или
SELECT p.name, p.price FROM product as p INNER JOIN types as t ON t.id = p.type_id
WHERE p.price IN ( SELECT max(price) FROM product);
--5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT t.name, count(p.name) as count FROM product as p INNER JOIN types as t ON t.id = p.type_id GROUP BY t.name;
--6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT t.name, p.name FROM product as p INNER JOIN types as t ON t.id = p.type_id WHERE t.name IN('СЫР', 'МОЛОКО');
--7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
SELECT t.name FROM product as p INNER JOIN types as t ON t.id = p.type_id GROUP BY t.name HAVING COUNT(p.name) < 10;
--8. Вывести все продукты и их тип.
SELECT p.name, t.name FROM product as p INNER JOIN types as t ON t.id = p.type_id;