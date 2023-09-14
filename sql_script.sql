drop database user_data;
create database if not exists user_data collate utf8mb4_general_ci;
use user_data;

create table smtp_config(
	id INT AUTO_INCREMENT PRIMARY KEY,
    host varchar(100) not null,
    port int not null,
    username varchar(100) not null,
    password varchar(50) not null
)engine=InnoDB;


CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(68) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    enabled tinyint DEFAULT 0,
    phone varchar(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    smtp_config_id int,
    job_title varchar(20),
    website varchar(50),
    github varchar(50),
	twitter varchar(50),
    instagram varchar(50),
    facebook varchar(50)
)engine=InnoDB;

alter table users add constraint fk_users_smtp foreign key(smtp_config_id) references smtp_config(id);

CREATE TABLE role (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)engine=InnoDB;

CREATE TABLE user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id)
)engine=InnoDB;

alter table user_roles add constraint fk_userRoles_users foreign key(user_id) references users(id);
alter table user_roles add constraint fk_userRoles_role foreign key(role_id) references role(id);

CREATE TABLE token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    token_value VARCHAR(100) UNIQUE NOT NULL,
    epiration_datetime timestamp,
    tokentype VARCHAR(50) -- You can specify the type of token, e.g., "email_verification", "password_reset", etc.
)engine=InnoDb;
alter table token add constraint fk_token_users foreign key(user_id) references users(id);

CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    StreetAddress VARCHAR(255),
    City VARCHAR(50),
    StateProvince VARCHAR(50),
    PostalCode VARCHAR(20),
    Country VARCHAR(50),
    AddressType VARCHAR(20)
)engine=InnoDB;
alter table address add constraint fk_address_users foreign key(user_id) references users(id);
CREATE INDEX index_country
ON address (Country);
CREATE INDEX index_city
ON address (City);

CREATE TABLE ShippingAddress (
    ShippingAddressID INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    StreetAddress VARCHAR(255),
    City VARCHAR(50),
    StateProvince VARCHAR(50),
    PostalCode VARCHAR(20),
    Country VARCHAR(50)
)engine=InnoDB;
alter table ShippingAddress add constraint fk_shippingaddress_users foreign key(user_id) references users(id);
CREATE INDEX index_country
ON ShippingAddress (Country);
CREATE INDEX index_city
ON ShippingAddress (City);

CREATE TABLE guest_data (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    role_id int not null ,
    data json,
    created_at TIMESTAMP DEFAULT NOW()
);


CREATE TABLE Product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT, -- Reference to the Category table
    SKU VARCHAR(50) UNIQUE NOT NULL, -- Stock Keeping Unit (unique identifier)
    ProductName VARCHAR(255) NOT NULL,
    Description TEXT,
    RegularPrice DECIMAL(10, 2) NOT NULL, -- Regular (non-sale) price
    SalePrice DECIMAL(10, 2), -- Sale price (optional)
    CostPrice DECIMAL(10, 2), -- Cost price of the product
    StockQuantity INT NOT NULL,
    Weight DECIMAL(8, 2), -- Product weight in kilograms (optional)
    ReleaseDate DATE, -- Date when the product was released (optional)
    IsFeatured BOOLEAN DEFAULT FALSE, -- Flag for featured products (optional)
    IsActive BOOLEAN DEFAULT TRUE, -- Flag to indicate if the product is active
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ProductImages JSON, -- JSON array of image URLs or references
    VariantColors varchar(100),
    VariantSizes varchar(100)-- JSON array of variant colors (e.g., ["Red", "Blue", "Green"])
    );
    
    CREATE INDEX index_productName
ON product (ProductName);

   

CREATE TABLE Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    parentCategory_id INT, -- For hierarchical categories (optional)
    CategoryName VARCHAR(255) NOT NULL,
    IsActive BOOLEAN DEFAULT TRUE, -- Flag to indicate if the category is active
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);




-- Create the ParentCategory table
CREATE TABLE ParentCategory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ParentCategoryName VARCHAR(255) NOT NULL,
    IsActive BOOLEAN DEFAULT TRUE, -- Flag to indicate if the parent category is active
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

alter table Product add constraint fk_product_category foreign key (category_id) references category(id);
alter table category add constraint fk_category_parentCategory foreign key (parentCategory_id) references parentCategory(id);

insert into smtp_config values
(1,"smtp.gmail.com","587","marouan.akechtah@gmail.com","vpqhwltkpllapuvr");

INSERT INTO role (id,name) VALUES (1,'ROLE_CLIENT');

-- Insert the 'admin' role
INSERT INTO role (id,name) VALUES (2,'ROLE_ADMIN');

-- Insert the 'manager' role
INSERT INTO role (id,name) VALUES (3,'ROLE_MANAGER');

-- Insert the 'guest' role
INSERT INTO role (id,name) VALUES (4,'ROLE_GUEST');


