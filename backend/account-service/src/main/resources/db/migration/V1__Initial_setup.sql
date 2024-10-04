DROP TABLE IF EXISTS account_product;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS linked_account;
DROP TABLE IF EXISTS recurring_transaction;

CREATE TABLE account_product (
    account_product_id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    account_type_unique_no VARCHAR(255) NOT NULL,
    cycle VARCHAR(255) NOT NULL,
    account_category VARCHAR(255) NOT NULL,
    detail TEXT NOT NULL,
    min_deposit_amount INT NOT NULL,
    max_deposit_amount INT NOT NULL,
    default_interest_rate DOUBLE NOT NULL,
    max_interest_rate DOUBLE NOT NULL,
    PRIMARY KEY (account_product_id)
);

CREATE TABLE account (
    account_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    account_no VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    interest_rate DOUBLE NOT NULL DEFAULT 1.0,
    balance BIGINT NOT NULL DEFAULT 0,
    password VARCHAR(255) NOT NULL,
    account_product_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    maturity_date DATE NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'ACTIVATED',
    PRIMARY KEY (account_id),
    CONSTRAINT fk_account_product_id FOREIGN KEY (account_product_id) REFERENCES account_product(account_product_id)
);

CREATE TABLE linked_account (
    linked_account_id BIGINT NOT NULL AUTO_INCREMENT,
    account_id BIGINT NOT NULL,
    bank_name VARCHAR(255) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    PRIMARY KEY (linked_account_id),
    CONSTRAINT fk_linked_account_account_id FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE recurring_transaction (
    recurring_transaction_id BIGINT NOT NULL AUTO_INCREMENT,
    account_id BIGINT NOT NULL,
    amount INT NOT NULL,
    payment_date INT NOT NULL,
    next_transaction_date DATE NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'ACTIVATED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (recurring_transaction_id),
    CONSTRAINT fk_recurring_transaction_account_id FOREIGN KEY (account_id) REFERENCES account(account_id)
);