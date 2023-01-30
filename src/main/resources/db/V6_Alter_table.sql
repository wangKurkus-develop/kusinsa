ALTER TABLE device modify COLUMN user_id bigint;

CREATE TABLE notification_group(
                                   id BIGINT NOT NULL,
                                   product_id BIGINT NOT NULL,
                                   status VARCHAR(30) default 'RECRUIT',
                                   deleted boolean default false,
                                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY(id),
                                   FOREIGN KEY(product_id) REFERENCES PRODUCT(id)
);

CREATE TABLE notification_group_user(
                                        id BIGINT NOT NULL,
                                        notification_group_id BIGINT NOT NULL,
                                        user_id BIGINT NOT NULL,
                                        status VARCHAR(30) default 'WAIT',
                                        deleted boolean default false,
                                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY(id),
                                        FOREIGN KEY(user_id) REFERENCES user(id),
                                        FOREIGN KEY(notification_group_id) REFERENCES notification_group(id)
);
