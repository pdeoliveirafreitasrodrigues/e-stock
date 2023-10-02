-- V1__create_table

CREATE TABLE deposit (
    id serial PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE product (
    id serial PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    cost_price NUMERIC(10, 2)
);

CREATE TABLE product_stock (
    id serial PRIMARY KEY,
    product_id BIGINT NOT NULL,
    deposit_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_cost_price NUMERIC(10, 2) NOT NULL,

    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (deposit_id) REFERENCES deposit(id)
);
