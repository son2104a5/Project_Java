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
    select * from product where id = id_in and status = true;
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
    brand_in varchar(100)
)
begin
    select * from product where brand = brand_in and status = true and stock != 0;
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
    update customer
    set status = false where id = id_in and status = true;
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

insert into Product (name, brand, price, stock, status) values
                                                            ('iPhone 15 Pro Max 256GB', 'Apple', 1199.00, 25, 1),
                                                            ('iPhone 15 128GB', 'Apple', 999.00, 30, 1),
                                                            ('iPhone 14 128GB', 'Apple', 799.00, 28, 1),
                                                            ('iPhone 13 Mini 128GB', 'Apple', 699.00, 20, 1),
                                                            ('Samsung Galaxy S24 Ultra', 'Samsung', 1299.00, 18, 1),
                                                            ('Samsung Galaxy S23 FE', 'Samsung', 699.00, 35, 1),
                                                            ('Samsung Galaxy A54 5G', 'Samsung', 449.00, 50, 1),
                                                            ('Xiaomi 13T Pro 12GB/512GB', 'Xiaomi', 649.00, 32, 1),
                                                            ('Xiaomi Redmi Note 13 5G', 'Xiaomi', 299.00, 60, 1),
                                                            ('Xiaomi POCO F5 Pro', 'Xiaomi', 479.00, 25, 1),
                                                            ('OPPO Reno10 5G 256GB', 'OPPO', 499.00, 29, 1),
                                                            ('OPPO A78 128GB', 'OPPO', 249.00, 45, 1),
                                                            ('OPPO Find N3 Flip', 'OPPO', 999.00, 15, 1),
                                                            ('Realme 11 Pro+ 512GB', 'Realme', 469.00, 40, 1),
                                                            ('Realme C55 128GB', 'Realme', 189.00, 55, 1),
                                                            ('Realme Narzo 60x 5G', 'Realme', 219.00, 42, 1),
                                                            ('Vivo V27e 256GB', 'Vivo', 379.00, 38, 1),
                                                            ('Vivo Y22s 128GB', 'Vivo', 219.00, 52, 1),
                                                            ('Vivo X90 Pro', 'Vivo', 899.00, 12, 1),
                                                            ('Asus ROG Phone 7 16GB', 'Asus', 1099.00, 10, 1),
                                                            ('Nokia G22 64GB', 'Nokia', 159.00, 65, 1),
                                                            ('Nokia X30 5G', 'Nokia', 359.00, 30, 1),
                                                            ('Sony Xperia 1 V 256GB', 'Sony', 1399.00, 9, 1),
                                                            ('Motorola Edge 40', 'Motorola', 599.00, 22, 1),
                                                            ('Huawei Nova 11i', 'Huawei', 399.00, 26, 1);

insert into Customer (name, phone, email, address) values
                                                       ('Nguyễn Văn A', '0901234567', 'nguyenvana@gmail.com', 'Hà Nội'),
                                                       ('Trần Thị B', '0902234567', 'tranthib@gmail.com', 'TP.HCM'),
                                                       ('Lê Văn C', '0903234567', 'levanc@gmail.com', 'Đà Nẵng'),
                                                       ('Phạm Thị D', '0904234567', 'phamthid@gmail.com', 'Cần Thơ'),
                                                       ('Hoàng Văn E', '0905234567', 'hoangvane@gmail.com', 'Nghệ An'),
                                                       ('Đỗ Thị F', '0906234567', 'dothif@gmail.com', 'Hải Phòng'),
                                                       ('Bùi Văn G', '0907234567', 'buivang@gmail.com', 'Nam Định'),
                                                       ('Ngô Thị H', '0908234567', 'ngothih@gmail.com', 'Huế'),
                                                       ('Vũ Văn I', '0909234567', 'vuvani@gmail.com', 'Quảng Ninh'),
                                                       ('Đặng Thị J', '0910234567', 'dangthij@gmail.com', 'Bình Dương'),
                                                       ('Lý Văn K', '0911234567', 'lyvank@gmail.com', 'Đồng Nai'),
                                                       ('Tạ Thị L', '0912234567', 'tatihl@gmail.com', 'Bắc Ninh'),
                                                       ('Cao Văn M', '0913234567', 'caovanm@gmail.com', 'Vĩnh Long'),
                                                       ('Trịnh Thị N', '0914234567', 'trinhthin@gmail.com', 'Phú Yên'),
                                                       ('Dương Văn O', '0915234567', 'duongvano@gmail.com', 'Long An');
