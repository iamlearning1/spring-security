ALTER TABLE customers
    ADD CONSTRAINT uc_customers_email UNIQUE (email);

ALTER TABLE customers
    ALTER COLUMN email SET NOT NULL;
