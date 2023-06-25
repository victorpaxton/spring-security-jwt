create table users (
	id bigserial primary key,
	name varchar(100) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	roles text not null
);

create table products (
	id bigserial primary key,
	name varchar(100) not null,
	description varchar(255),
	product_type varchar(100) not null,
	quantity int not null,
	price real not null check (price >= 200 and price <= 500000),
	supplier_name varchar(255),
	supplier_code varchar(100) not null
);

insert into products (name, description, product_type, quantity, price, supplier_name, supplier_code) values ('Android Mobile', 'Samsung Galaxy', 'Electronics', 1, 10000, 'Sams Digigtal', '76324dgf');
insert into products (name, description, product_type, quantity, price, supplier_name, supplier_code) values ('Iphone 13', 'Apollo', 'Electronics', 1, 80000, 'iPlannet', '45676hhf');
insert into products (name, description, product_type, quantity, price, supplier_name, supplier_code) values ('Camera', 'Cannon', 'Electronics', 2, 80000, 'Relliance Digigtal', '75675gdh');
insert into products (name, description, product_type, quantity, price, supplier_name, supplier_code) values ('Mixer Grinder', 'Bosch', 'Kitchen and Home', 1, 6999, 'BK Market', '75395fsn');
insert into products (name, description, product_type, quantity, price, supplier_name, supplier_code) values ('Geysers', 'Havells', 'Kitchen and Home', 1, 3469, 'John world', '80496fgh');
insert into products (name, description, product_type, quantity, price, supplier_name, supplier_code) values ('Watch', 'Enticer Men Analog', 'Electronics', 1, 5995, 'Sams Digigtal', '76324dgf');


select * from products;

delete from products where id = 9;

select * from users;

insert into users (name, email, password, roles) values ('abc', 'abc@gmail.com', '123', 'ROLE_ADMIN,ROLE_USER');
