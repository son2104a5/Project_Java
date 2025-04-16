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
)