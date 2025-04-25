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
    stock int            not null,
    status bit
);

create table Customer
(
    id      int primary key auto_increment,
    name    varchar(100) not null,
    phone   varchar(20),
    email   varchar(100) unique,
    address varchar(255),
    status bit
);

create table Invoice
(
    id           int primary key auto_increment,
    customer_id  int,
    foreign key (customer_id) references Customer (id),
    created_at   datetime default current_timestamp,
    total_amount decimal(12, 2) not null,
    status bit
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
    select * from product where status = true and stock != 0;
end;

create procedure display_all_product_per_page(
    index_page int
)
begin
    select * from product
    where status = true and stock != 0
    limit 5 offset index_page;
end;

create procedure add_product(
    name_in varchar(100),
    brand_in varchar(100),
    price_in decimal(12, 2),
    stock_in int
)
begin
    insert into product(name, brand, price, stock, status)
    values (name_in, brand_in, price_in, stock_in, true);
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
    update product
    set status = false where id = id_in and status = true;
end;

create procedure find_product_by_brand(
    p_brand varchar(100)
)
begin
    select * from product where binary brand = binary p_brand and status = true and stock != 0;
end;

create procedure find_product_by_price_amount(
    min_price decimal(12, 2),
    max_price decimal(12, 2)
)
begin
    select * from product where price between min_price and max_price and status = true and stock != 0;
end;

create procedure find_product_by_stock_amount(
    min_stock int,
    max_stock int
)
begin
    select * from product where stock between min_stock and max_stock and status = true and stock != 0;
end;
DELIMITER //

-- CustomerService
DELIMITER //
create procedure display_all_customer()
begin
    select * from customer;
end;

create procedure display_all_customer_per_page(
    index_page int
)
begin
    select * from customer limit 5 offset index_page;
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
    insert into customer(name, phone, email, address, status)
    values (name_in, phone_in, email_in, address_in, true);
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
    delete from customer
    where id = id_in and status = true;
end;
DELIMITER //

-- InvoiceService
DELIMITER //
create procedure display_all_invoice()
begin
    select * from invoice;
end;

create procedure display_all_invoice_per_page(
    index_page int
)
begin
    select * from invoice limit 5 offset index_page;
end;

create procedure add_invoice(
    p_customer_id int,
    p_total_amount decimal(12, 2)
)
begin
    insert into invoice(customer_id, total_amount, status)
    values (p_customer_id,  p_total_amount, true);
end;

create procedure search_invoice_by_customer_name(
    p_customer_name varchar(100)
)
begin
    select i.*
    from invoice i
             join customer c on i.customer_id = c.id
    where c.name like concat('%', p_customer_name, '%');
end;

create procedure search_invoice_by_date(
    p_date_in date,
    p_date_out date
)
begin
    select * from invoice
    where created_at >= p_date_in
      and created_at < date_add(p_date_out, interval 1 day);
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

    update product
        set stock = stock - p_quantity where id = p_product_id;
end;
DELIMITER //

-- StatisticService
DELIMITER //
create procedure total_date_revenue(
    p_date_in date,
    p_date_out date
)
begin
    select * from invoice
    where created_at >= p_date_in
      and created_at < date_add(p_date_out, interval 1 day);
end;

create procedure total_month_revenue(
    p_month_in int,
    p_year_in int,
    p_month_out int,
    p_year_out int
)
begin
    declare start_date date;
    declare end_date date;

    set start_date = str_to_date(concat(p_year_in, '-', p_month_in, '-01'), '%Y-%m-%d');
    set end_date = last_day(str_to_date(concat(p_year_out, '-', p_month_out, '-01'), '%Y-%m-%d'));

    select * from invoice
    where created_at between start_date and end_date;
end;

create procedure total_year_revenue(
    p_year_in int,
    p_year_out int
)
begin
    select * from invoice
    where created_at >= str_to_date(concat(p_year_in, '-01-01'), '%Y-%m-%d')
      and created_at <  str_to_date(concat(p_year_out + 1, '-01-01'), '%Y-%m-%d');
end;
DELIMITER //