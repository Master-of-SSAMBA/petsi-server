ALTER TABLE user ADD COLUMN notification_status VARCHAR(255) NOT NULL AFTER user_key;
UPDATE user SET notification_status = '비활성';