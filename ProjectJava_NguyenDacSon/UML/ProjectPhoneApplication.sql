create database Project_Phone_Application;
use Project_Phone_Application;

create table Admin(
    id int primary key auto_increment,
    username varchar(50) not null unique,
    password varchar(255) not null
);

create table Product(
    id int primary key auto_increment,
    name varchar(100) not null,
    brand varchar(50) not null,
    price decimal(12, 2) not null,
    stock int not null
);

create table Customer(
    id int primary key auto_increment,
    name varchar(100) not null,
    phone varchar(20),
    email varchar(100) unique,
    address varchar(255)
);

create table Invoice(
    id int primary key auto_increment,
    customer_id int,
    foreign key (customer_id) references Customer (id),
    created_at  datetime default current_timestamp,
    total_amount decimal(12, 2) not null
);

create table Invoice_detail(
    id int primary key auto_increment,
    invoice_id int,
    foreign key (invoice_id) references Invoice(id),
    product_id int,
    foreign key (product_id) references Product(id),
    quantity int not null,
    unit_price decimal(12, 2) not null
);

DELIMITER //
-- ProductService
create procedure display_all_product()
begin
    select * from product;
end;

create procedure add_product(
    name_in varchar(100),
    brand_in varchar(100),
    price_in decimal(12, 2),
    stock_in int
)
begin
    insert into product(name, brand, price, stock)
        values (name_in, brand_in, price_in, stock_in);
end;

create procedure find_product_by_id(
    id_in int
)
begin
    select * from product where id = id_in;
end;

create procedure update_product_name(
    id_in int,
    name_in varchar(100)
)
begin
    update product
        set name = name_in where id = id_in;
end;

create procedure update_product_brand(
    id_in int,
    brand_in varchar(100)
)
begin
    update product
    set brand = brand_in where id = id_in;
end;

create procedure update_product_price(
    id_in int,
    price_in decimal(12, 2)
)
begin
    update product
    set price = price_in where id = id_in;
end;

create procedure update_product_stock(
    id_in int,
    stock_in int
)
begin
    update product
    set stock = stock_in where id = id_in;
end;

create procedure delete_product(
    id_in int
)
begin
    delete from product where id = id_in;
end;

create procedure find_product_by_brand(
    brand_in varchar(100)
)
begin
    select * from product where brand = brand_in;
end;

create procedure find_product_by_price_amount(
    min_price decimal(12, 2),
    max_price decimal(12, 2)
)
begin
    select * from product where price between min_price and max_price;
end;

create procedure find_product_by_stock_amount(
    min_stock int,
    max_stock int
)
begin
    select * from product where stock between min_stock and max_stock;
end;
DELIMITER //

