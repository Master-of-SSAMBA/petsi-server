DROP TABLE IF EXISTS user_token;
CREATE TABLE user_token (
    user_token_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    token varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    PRIMARY KEY (user_token_id)
);