set search_path to public;

create domain MassGrams as int check ( value >= 0);
create type OrderStatus as enum ('IN_PROCESS', 'REJECTED', 'COMPLETED');
create type UserRole as enum ('CLIENT', 'STAFF', 'ADMIN')

create table users (
    user_id int primary key generated always as identity,
    role UserRole not null,
    password varchar,
    login varchar
);

create table pizzas (
    pizza_id int primary key generated always as identity,
    name varchar not null,
    price int not null
);

create table ingredients (
    ingredient_id int primary key generated always as identity,
    name varchar not null
);

create table pizza_ingredients (
    pizza_id int references pizzas (pizza_id) on delete cascade,
    ingredient_id int references ingredients (ingredient_id) on delete restrict,
    amount MassGrams not null
);

create table orders (
    order_id int primary key generated always as identity,
    status OrderStatus not null,
    user_id int references users (user_id) on delete cascade
);

create table order_pizzas (
    order_id int references orders (order_id) on delete cascade,
    pizza_id int references pizzas (pizza_id) on delete restrict
);
