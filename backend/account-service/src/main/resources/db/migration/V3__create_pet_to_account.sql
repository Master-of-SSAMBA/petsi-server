DROP TABLE IF EXISTS pet_to_account;

CREATE TABLE pet_to_account (
    pet_to_account_id BIGINT NOT NULL AUTO_INCREMENT,
    pet_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    PRIMARY KEY (pet_to_account_id),
    CONSTRAINT fk_pet_to_account_id FOREIGN KEY (account_id) REFERENCES account(account_id)
)