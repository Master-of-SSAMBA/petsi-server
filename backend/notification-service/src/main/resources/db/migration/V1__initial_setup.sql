DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
    notice_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    is_read TINYINT NOT NULL,
    link_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    read_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (notice_id)
);