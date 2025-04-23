create database Project_Phone_Application;
use Project_Phone_Application;

create table Admin
(
    id       int primary key auto_increment,
    username varchar(50)  not null unique,
    password varchar(255) not null
);

create table Product
(
    id    int primary key auto_increment,
    name  varchar(100)   not null,
    brand varchar(50)    not null,
    price decimal(12, 2) not null,
    stock int            not null
);

create table Customer
(
    id      int primary key auto_increment,
    name    varchar(100) not null,
    phone   varchar(20),
    email   varchar(100) unique,
    address varchar(255)
);

create table Invoice
(
    id           int primary key auto_increment,
    customer_id  int,
    foreign key (customer_id) references Customer (id),
    created_at   datetime default current_timestamp,
    total_amount decimal(12, 2) not null
);

create table Invoice_detail
(
    id         int primary key auto_increment,
    invoice_id int,
    foreign key (invoice_id) references Invoice (id),
    product_id int,
    foreign key (product_id) references Product (id),
    quantity   int            not null,
    unit_price decimal(12, 2) not null
);

-- ProductService
DELIMITER //
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
    set name = name_in
    where id = id_in;
end;

create procedure update_product_brand(
    id_in int,
    brand_in varchar(100)
)
begin
    update product
    set brand = brand_in
    where id = id_in;
end;

create procedure update_product_price(
    id_in int,
    price_in decimal(12, 2)
)
begin
    update product
    set price = price_in
    where id = id_in;
end;

create procedure update_product_stock(
    id_in int,
    stock_in int
)
begin
    update product
    set stock = stock_in
    where id = id_in;
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

-- CustomerService
DELIMITER //
create procedure display_all_customer()
begin
    select * from customer;
end;

create procedure find_customer_by_id(
    id_in int
)
begin
    select * from customer where id = id_in;
end;

create procedure add_customer(
    name_in varchar(100),
    phone_in varchar(100),
    email_in varchar(100),
    address_in varchar(100)
)
begin
    insert into customer(name, phone, email, address)
    values (name_in, phone_in, email_in, address_in);
end;

create procedure update_customer_name(
    id_in int,
    name_in varchar(100)
)
begin
    update customer
    set name = name_in
    where id = id_in;
end;

create procedure update_customer_phone(
    id_in int,
    phone_in varchar(100)
)
begin
    update customer
    set phone = phone_in
    where id = id_in;
end;

create procedure update_customer_email(
    id_in int,
    email_in varchar(100)
)
begin
    update customer
    set email = email_in
    where id = id_in;
end;

create procedure update_customer_address(
    id_in int,
    address_in varchar(100)
)
begin
    update customer
    set address = address_in
    where id = id_in;
end;

create procedure delete_customer(
    id_in int
)
begin
    delete from customer where id = id_in;
end;
DELIMITER //

-- InvoiceService
DELIMITER //
create procedure display_all_invoice()
begin
    select * from invoice;
end;

create procedure add_invoice(
    p_customer_id int,
    p_total_amount decimal(12, 2)
)
begin
    insert into invoice(customer_id, total_amount)
    values (p_customer_id,  p_total_amount);
end;

create procedure search_invoice_by_customer_name(
    p_customer_name varchar(100)
)
begin
    declare cus_id int;
    select id into cus_id from customer where name = p_customer_name;
    select * from invoice where customer_id = cus_id;
end;

create procedure search_invoice_by_date(
    p_date_in date,
    p_date_out date
)
begin
    select * from invoice where created_at between p_date_in and p_date_out;
end;

create procedure update_total_amount(
    p_id int,
    p_total_amount decimal(12, 2)
)
begin
    update invoice
        set total_amount = p_total_amount where id = p_id;
end;
DELIMITER //

-- InvoiceDetailService
DELIMITER //
create procedure display_invoice_detail()
begin
    select * from invoice_detail;
end;

create procedure display_invoice_detail_by_invoice_id(
    p_invoice_id int
)
begin
    select * from invoice_detail where invoice_id = p_invoice_id;
end;

create procedure add_invoice_detail(
    p_invoice_id int,
    p_product_id int,
    p_quantity int,
    p_unit_price decimal(12, 2)
)
begin
    insert into invoice_detail(invoice_id, product_id, quantity, unit_price)
        values (p_invoice_id, p_product_id, p_quantity, p_unit_price);
end;
DELIMITER //

-- StatisticService
DELIMITER //
create procedure total_date_revenue(
    p_date date
)
begin
    select * from invoice where date(created_at) = p_date;
end;

create procedure total_month_revenue(
    p_month int,
    p_year int
)
begin
    select * from invoice where month(created_at) = p_month and year(created_at) = p_year;
end;

create procedure total_year_revenue(
    p_year int
)
begin
    select * from invoice where year(created_at) = p_year;
end;
DELIMITER //