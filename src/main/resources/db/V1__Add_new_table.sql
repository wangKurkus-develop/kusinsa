--  device 추가
CREATE TABLE DEVICE(
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       user_id BIGINT NOT NULL,
                       device_token VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       PRIMARY KEY (id),
                       FOREIGN KEY(user_id) REFERENCES USER(id)
);