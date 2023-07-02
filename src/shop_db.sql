--For postgreSQL
CREATE DATABASE shop_db;

CREATE TABLE customers (
    email VARCHAR NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    money NUMERIC(10, 2) NOT NULL
);

CREATE TABLE products (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL,
    price NUMERIC(10, 3) NOT NULL,
    countable BOOLEAN NOT NULL
);

CREATE TABLE orders (
    serial_number INT NOT NULL,
    customer_email VARCHAR NOT NULL,
    time_stamp timestamp NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_customer_email_orders FOREIGN KEY (customer_email) REFERENCES customers (email),
    CONSTRAINT fk_product_id_orders FOREIGN KEY (product_id) REFERENCES products (id)
);

INSERT INTO products (name, price, countable)
VALUES
    ('Potato', 1.70, true),
    ('Cherry', 0.50, true),
    ('Onion', 4, true),
    ('Egg', 7, true),
    ('Sausage', 12.44, true),
    ('Bun', 8.56, true),
    ('Orange', 16, true),
    ('NEmik', 20.3, true),
    ('Sugar', 0.032, false),
    ('Meat', 0.14, false),
    ('Flour', 0.019, false),
    ('Salt', 0.026, false),
    ('Rise', 0.067, false);