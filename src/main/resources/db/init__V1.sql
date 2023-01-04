CREATE TABLE USER(
                     id BIGINT NOT NULL AUTO_INCREMENT,
                     email VARCHAR(50) NOT NULL,
                     password VARCHAR(255) NOT NULL,
                     phone_number VARCHAR(50) NOT NULL,
                     address VARCHAR(255) NOT NULL,
                     name VARCHAR(20) NOT NULL,
                     type VARCHAR(20) DEFAULT 'USER',
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                     PRIMARY KEY (id)
);

CREATE TABLE POINT(
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      score BIGINT DEFAULT 0,
                      division VARCHAR(50) NOT NULL,
                      content VARCHAR(100) NOT NULL,
                      user_id BIGINT NOT NULL,
                      FOREIGN KEY(user_id) REFERENCES USER(id),
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      PRIMARY KEY (id)
);

CREATE TABLE CATEGORY(
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(50) NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (id)
);

CREATE TABLE BRAND(
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      PRIMARY KEY (id)
);

--  매우중요
CREATE TABLE PRODUCT(
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(50) NOT NULL,
                        price BIGINT NOT NULL,
                        content VARCHAR(255) NOT NULL,
                        steams BIGINT DEFAULT 0,
                        category_id BIGINT NOT NULL,
                        brand_id BIGINT NOT NULL,
                        image LONGTEXT NOT NULL,
                        status VARCHAR(20) DEFAULT 'SALE',
                        FOREIGN KEY(category_id) REFERENCES CATEGORY(id),
                        FOREIGN KEY(brand_id) REFERENCES BRAND(id),
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (id)
);

CREATE TABLE REVIEW(
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       product_id BIGINT NOT NULL,
                       image LONGTEXT NOT NULL,
                       content varchar(255) NOT NULL,
                       score int NOT NULL,
                       FOREIGN KEY(product_id) REFERENCES PRODUCT(id),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       PRIMARY KEY (id)
);

CREATE TABLE STOCK_HISTORY(
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              stock BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              FOREIGN KEY(product_id) REFERENCES PRODUCT(id),
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (id)
);

CREATE TABLE ORDERS(
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       user_id BIGINT NOT NULL,
                       order_price BIGINT NOT NULL,
                       order_status VARCHAR(50) DEFAULT 'PREPARE_DELIVERY',
                       delivery_address VARCHAR(255) NOT NULL,
                       order_history LONGTEXT NOT NULL,
                       FOREIGN KEY(user_id) REFERENCES USER(id),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       PRIMARY KEY (id)
);

