DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
    notice_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    category varchar(255) NOT NULL,
    content varchar(255) NOT NULL,
    is_read tinyint NOT NULL,
    link bigint NOT NULL,
    created_at timestamp NOT NULL,
    read_at timestamp NULL DEFAULT NULL,
    PRIMARY KEY (notice_id)
);