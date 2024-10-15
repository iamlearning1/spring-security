CREATE SEQUENCE IF NOT EXISTS customers_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE customers
(
    id       BIGINT NOT NULL,
    email    VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255),
    CONSTRAINT pk_customers PRIMARY KEY (id)
);
