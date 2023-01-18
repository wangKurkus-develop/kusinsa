ALTER TABLE orders DROP order_status;
ALTER TABLE orders DROP order_history;


ALTER TABLE orders ADD total_used_point BIGINT NOT NULL;
ALTER TABLE orders ADD total_obtain_point BIGINT NOT NULL;

CREATE TABLE ORDERS_PRODUCT_HISTORY(
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       order_id BIGINT NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       product_id BIGINT NOT NULL,
                                       price BIGINT NOT NULL,
                                       obtain_point BIGINT NOT NULL,
                                       delivery_status VARCHAR(20) default 'PRODUCT_READY',
                                       order_status VARCHAR(20) default 'PAYMENT_COMPLETE',
                                       deleted boolean default false,
                                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       PRIMARY KEY (id),
                                       FOREIGN KEY(order_id) REFERENCES ORDERS(id),
                                       FOREIGN KEY(user_id) REFERENCES USER(id),
                                       FOREIGN KEY(product_id) REFERENCES PRODUCT(id)
);