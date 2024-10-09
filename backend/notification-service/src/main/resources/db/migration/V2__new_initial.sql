DROP TABLE IF EXISTS notice;

DROP TABLE IF EXISTS notification;
CREATE TABLE notification (
    notification_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    is_read TINYINT NOT NULL,
    link_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    read_at TIMESTAMP NULL,
    PRIMARY KEY (notification_id)
);

DROP TABLE IF EXISTS user_token;
CREATE TABLE user_token (
    user_token_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    token varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    PRIMARY KEY (user_token_id)
);