INSERT INTO ingredients(name)
VALUES ('помидор'),
       ('сыр'),
       ('курица'),
       ('бекон'),
       ('лук'),
       ('ананас'),
       ('сладкий перец'),
       ('томатный соус'),
       ('соус песто'),
       ('соус карри'),
       ('соус барбекю'),
       ('шампиньоны'),
       ('охотничьи колбаски'),
       ('салями');
       
INSERT INTO pizzas(name, price)
VALUES ('гавайская', 1000),
       ('охотничья', 800),
       ('салями ранч', 900),
       ('цыплёнок карри', 1100),
       ('мясной микс', 1300);
       
INSERT INTO pizza_ingredients
SELECT pizza_id, ingredient_id
  FROM pizzas CROSS JOIN ingredients
 WHERE (pizzas.name = 'гавайская' AND
       ingredients.name IN ('ананас', 'курица', 'соус песто', 'cыр'))
       OR (pizzas.name = 'охотничья' AND
       ingredients.name IN ('охотничьи колбаски', 'томатный соус', 'шампиньоны'))
       OR (pizzas.name = 'салями ранч' AND
       ingredients.name IN ('салями', 'томатный соус', 'лук'))
       OR (pizzas.name = 'цыплёнок карри' AND
       ingredients.name IN ('курица', 'соус карри', 'сладкий перец', 'соус песто'))
       OR (pizzas.name = 'мясной микс' AND
       ingredients.name IN ('курица', 'бекон', 'салями', 'томатный соус', 'сыр'));
      
INSERT INTO users(role, password, login)
VALUES ('ADMIN', 'secret', 'dzen'),
       ('STAFF', 'scrscr', 'parfen'),
       ('CLIENT', 'fpm', 'fpm_student');

INSERT INTO orders(status, user_id)
SELECT 'COMPLETED', user_id
  FROM users
 WHERE login = 'dzen';

INSERT INTO order_pizzas(order_id, pizza_id)
SELECT (SELECT MAX(order_id) FROM orders), pizza_id
  FROM pizzas
 WHERE name IN ('гавайская', 'охотничья');

