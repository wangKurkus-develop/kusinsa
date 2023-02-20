-- brand
CREATE TABLE IF NOT EXISTS `brand` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(100) NOT NULL,
                                       `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       `deleted` tinyint(1) DEFAULT '0',
                                       PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- category
CREATE TABLE IF NOT EXISTS `category` (
                                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                          `name` varchar(50) NOT NULL,
                                          `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          `deleted` tinyint(1) DEFAULT '0',
                                          PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `users` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `email` varchar(50) NOT NULL,
                                       `password` varchar(255) NOT NULL,
                                       `phone_number` varchar(50) NOT NULL,
                                       `address` varchar(255) NOT NULL,
                                       `name` varchar(20) NOT NULL,
                                       `type` varchar(20) DEFAULT 'USER',
                                       `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       `deleted` tinyint(1) DEFAULT '0',
                                       PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- product

CREATE TABLE IF NOT EXISTS `product` (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `name` varchar(50) NOT NULL,
                                         `price` bigint(20) NOT NULL,
                                         `content` varchar(255) NOT NULL,
                                         `likes` bigint(20) DEFAULT '0',
                                         `category_id` bigint(20) NOT NULL,
                                         `brand_id` bigint(20) NOT NULL,
                                         `status` varchar(20) DEFAULT 'SALE',
                                         `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         `stock` bigint(20) DEFAULT '0',
                                         `origin_image_path` longtext,
                                         `thumbnail_image_path` longtext,
                                         `deleted` tinyint(1) DEFAULT '0',
                                         PRIMARY KEY (`id`),
                                         CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                         CONSTRAINT `product_ibfk_2` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- device
CREATE TABLE IF NOT EXISTS `device` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `user_id` bigint(20) DEFAULT NULL,
                                        `device_token` varchar(255) NOT NULL,
                                        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`id`),
                                        KEY `user_id` (`user_id`),
                                        CONSTRAINT `device_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- notification_group
CREATE TABLE IF NOT EXISTS `notification_group` (
                                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                    `product_id` bigint(20) NOT NULL,
                                                    `status` varchar(30) DEFAULT 'RECRUIT',
                                                    `deleted` tinyint(1) DEFAULT '0',
                                                    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                    `unique_key` varchar(255) NOT NULL,
                                                    PRIMARY KEY (`id`),
                                                    KEY `product_id` (`product_id`),
                                                    CONSTRAINT `notification_group_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- notification_group_user
CREATE TABLE IF NOT EXISTS `notification_group_user` (
                                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                         `notification_group_id` bigint(20) NOT NULL,
                                                         `user_id` bigint(20) NOT NULL,
                                                         `status` varchar(30) DEFAULT 'WAIT',
                                                         `deleted` tinyint(1) DEFAULT '0',
                                                         `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                         `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                         PRIMARY KEY (`id`),
                                                         KEY `user_id` (`user_id`),
                                                         KEY `notification_group_id` (`notification_group_id`),
                                                         CONSTRAINT `notification_group_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                         CONSTRAINT `notification_group_user_ibfk_2` FOREIGN KEY (`notification_group_id`) REFERENCES `notification_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- orders

CREATE TABLE IF NOT EXISTS `orders` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `user_id` bigint(20) NOT NULL,
                                        `order_price` bigint(20) NOT NULL,
                                        `delivery_address` varchar(255) NOT NULL,
                                        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        `deleted` tinyint(1) DEFAULT '0',
                                        `total_used_point` bigint(20) NOT NULL,
                                        `total_obtain_point` bigint(20) NOT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `user_id` (`user_id`),
                                        CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- orders_product_history
CREATE TABLE IF NOT EXISTS `orders_product_history` (
                                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                        `order_id` bigint(20) NOT NULL,
                                                        `user_id` bigint(20) NOT NULL,
                                                        `product_id` bigint(20) NOT NULL,
                                                        `price` bigint(20) NOT NULL,
                                                        `obtain_point` bigint(20) NOT NULL,
                                                        `delivery_status` varchar(20) DEFAULT 'DELIVERY_READY',
                                                        `order_status` varchar(20) DEFAULT 'PAYMENT_COMPLETE',
                                                        `deleted` tinyint(1) DEFAULT '0',
                                                        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                        `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                        `quantity` int(11) NOT NULL,
                                                        PRIMARY KEY (`id`),
                                                        KEY `order_id` (`order_id`),
                                                        KEY `user_id` (`user_id`),
                                                        KEY `product_id` (`product_id`),
                                                        CONSTRAINT `orders_product_history_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                        CONSTRAINT `orders_product_history_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                        CONSTRAINT `orders_product_history_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- point
CREATE TABLE IF NOT EXISTS `point` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `score` bigint(20) DEFAULT '0',
                                       `division` varchar(50) NOT NULL,
                                       `content` varchar(100) NOT NULL,
                                       `user_id` bigint(20) NOT NULL,
                                       `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       `deleted` tinyint(1) DEFAULT '0',
                                       PRIMARY KEY (`id`),
                                       KEY `point_ibfk_1` (`user_id`),
                                       CONSTRAINT `point_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- review
CREATE TABLE IF NOT EXISTS `review` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `product_id` bigint(20) NOT NULL,
                                        `image` longtext NOT NULL,
                                        `content` varchar(255) NOT NULL,
                                        `score` int(11) NOT NULL,
                                        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        `deleted` tinyint(1) DEFAULT '0',
                                        PRIMARY KEY (`id`),
                                        KEY `product_id` (`product_id`),
                                        CONSTRAINT `review_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- zzzzzzzz

-- brand
REPLACE  INTO `brand` VALUES (1,'노스페이스','2023-01-08 05:17:52','2023-01-08 05:17:52',0);

REPLACE  INTO `brand` VALUES (2,'네셔널지오그래픽','2023-01-08 05:17:54','2023-01-08 05:17:54',0);

REPLACE  INTO `brand` VALUES (3,'아디다스','2023-01-08 05:17:55','2023-01-08 05:17:55',0);

REPLACE  INTO `brand` VALUES (4,'나이키','2023-01-08 05:17:55','2023-01-08 05:17:55',0);

REPLACE  INTO `brand` VALUES (5,'디스커버리','2023-01-08 05:17:56','2023-01-08 05:17:56',0);
REPLACE  INTO `brand` VALUES (6,'브랜드이름0','2023-02-07 01:40:19','2023-02-07 01:40:19',0);
-- category
REPLACE  INTO `category` VALUES (1,'맨투맨','2023-01-08 05:14:13','2023-01-08 05:14:13',0);
REPLACE  INTO `category` VALUES(2,'후드티','2023-01-08 05:16:19','2023-01-08 05:16:19',0);
REPLACE  INTO `category` VALUES(3,'셔츠','2023-01-08 05:16:20','2023-01-08 05:16:20',0);
REPLACE  INTO `category` VALUES(4,'롱패딩','2023-01-08 05:16:21','2023-01-08 05:16:21',0);
REPLACE  INTO `category` VALUES(5,'이름0','2023-02-07 01:37:37','2023-02-07 01:37:37',0);
REPLACE  INTO `category` VALUES(6,'이름1','2023-02-07 01:37:37','2023-02-07 01:37:37',0);
REPLACE  INTO `category` VALUES(7,'이름2','2023-02-07 01:37:37','2023-02-07 01:37:37',0);


REPLACE  INTO `users` VALUES (1,'hello@naver.com','$2a$10$eHScWw/4zoMlWEqWQy8zZ.DNJhMFr2elLUvWdgZVeig3nFBcA.xMW','010-1111-1111','쿠르쿠스시 쿠르쿠르동','최왕규','USER','2023-01-02 12:22:11','2023-01-10 12:53:36',0);
REPLACE  INTO `users` VALUES(18,'cwg@naver.com','$2a$10$KH5E3W2P3MkHWaJfi5zGaOPD60jMSLnvQ.vQZMsrrLw3bGpcwphsG','010-1234-1234','쿠르쿸쿠쿠쿠쿠','쿠쿠쿠','USER','2023-01-04 11:08:11','2023-01-10 12:53:36',0);
REPLACE  INTO `users` VALUES(19,'cwg2@naver.com','$2a$10$cyEh3ww6I3ra2MkQWErWZOXPlBhL04du5IH27/CNiU9j/.na.ZBrS','010-1234-1234','쿠르쿸쿠쿠쿠쿠','쿠쿠쿠','USER','2023-01-04 11:38:10','2023-01-10 12:53:36',0);
REPLACE  INTO `users` VALUES(20,'admin@naver.com','$2a$10$to8BSHxvQxgHIEu2lRp2GePnI5qdYz2B.hgHA2rbbOqTIwv72S9d6','010-1123-2222','주소','관리자1','ADMIN','2023-01-10 12:42:22','2023-01-10 12:51:26',0);
-- product
REPLACE  into `product` values (1, '상품1', 1000, '상품1의 설명', 0, 1, 1, 'SALE', '2023-01-09 17:40:02', '2023-01-18 22:29:27', 100, '원본경로1', '썸네일경로1', 0);
REPLACE  into `product` values(5, '테스트상품1', 10000, '테스트내용1', 0, 1, 1, 'SOLD_OUT', '2023-01-10 22:05:45', '2023-01-30 21:20:30', 0, '원본이미지경로1', '썸네일이미지경로1', 0);
REPLACE  into `product` values(11, '이름0', 100, '내용0', 3, 1, 2, 'SOLD_OUT', '2023-01-18 22:37:21', '2023-02-06 16:34:05', 66, '원본이미지0', '썸네일이미지0', 0);
REPLACE  into `product` values(12, '이름1', 101, '내용1', 4, 2, 1, 'SALE', '2023-01-18 22:37:21', '2023-02-06 16:28:18', 140, '원본이미지1', '썸네일이미지1', 0);
REPLACE  into `product` values(13, '이름2', 102, '내용2', 3, 3, 1, 'SALE', '2023-01-18 22:37:21', '2023-02-06 16:28:18', 98, '원본이미지2', '썸네일이미지2', 0);
REPLACE  into `product` values(14, '이름3', 103, '내용3', 5, 4, 1, 'SALE', '2023-01-18 22:37:21', '2023-02-06 16:28:18', 100, '원본이미지3', '썸네일이미지3', 0);
REPLACE  into `product` values(15, '이름4', 104, '내용4', 3, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-02-02 11:29:20', 198, '원본이미지4', '썸네일이미지4', 0);
REPLACE  into `product` values(16, '이름5', 105, '내용5', 3, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-02-10 10:32:17', 98, '원본이미지5', '썸네일이미지5', 0);
REPLACE  into `product` values(17, '이름6', 106, '내용6', 3, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-27 16:50:01', 100, '원본이미지6', '썸네일이미지6', 0);
REPLACE  into `product` values(18, '이름7', 107, '내용7', 4, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-27 16:50:01', 100, '원본이미지7', '썸네일이미지7', 0);
REPLACE  into `product` values(19, '이름8', 108, '내용8', 3, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-27 16:50:01', 100, '원본이미지8', '썸네일이미지8', 0);
REPLACE  into `product` values(20, '이름9', 109, '내용9', 5, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-27 16:50:01', 100, '원본이미지9', '썸네일이미지9', 0);
REPLACE  into `product` values(21, '이름10', 110, '내용10', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지10', '썸네일이미지10', 0);
REPLACE  into `product` values(22, '이름11', 111, '내용11', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지11', '썸네일이미지11', 0);
REPLACE  into `product` values(23, '이름12', 112, '내용12', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지12', '썸네일이미지12', 0);
REPLACE  into `product` values(24, '이름13', 113, '내용13', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지13', '썸네일이미지13', 0);
REPLACE  into `product` values(25, '이름14', 114, '내용14', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지14', '썸네일이미지14', 0);
REPLACE  into `product` values(26, '이름15', 115, '내용15', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지15', '썸네일이미지15', 0);
REPLACE  into `product` values(27, '이름16', 116, '내용16', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지16', '썸네일이미지16', 0);
REPLACE  into `product` values(28, '이름17', 117, '내용17', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지17', '썸네일이미지17', 0);
REPLACE  into `product` values(29, '이름18', 118, '내용18', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지18', '썸네일이미지18', 0);
REPLACE  into `product` values(30, '이름19', 119, '내용19', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지19', '썸네일이미지19', 0);
REPLACE  into `product` values(31, '이름20', 120, '내용20', 0, 1, 1, 'SALE', '2023-01-18 22:37:21', '2023-01-18 22:37:21', 100, '원본이미지20', '썸네일이미지20', 0);

-- device
REPLACE  INTO `device` VALUES (1,NULL,'testDeviceToken','2023-01-29 05:36:06','2023-01-29 05:36:06');
-- notification_group
REPLACE  INTO `notification_group` VALUES (1,11,'RECRUIT',0,'2023-01-30 07:28:05','2023-01-30 07:28:05','2023-01-30T16:28:02.261');
REPLACE  INTO `notification_group` VALUES(2,15,'RECRUIT',0,'2023-01-30 07:39:40','2023-01-30 07:39:40','2023-01-30T16:39:36.987');
REPLACE  INTO `notification_group` VALUES(3,16,'RECRUIT',0,'2023-01-30 07:47:00','2023-01-30 07:47:00','2023-01-30T16:46:57.652');
REPLACE  INTO `notification_group` VALUES(4,17,'RECRUIT',0,'2023-01-30 08:04:14','2023-01-30 08:04:14','2023-01-30T17:04:12.116');
REPLACE  INTO `notification_group` VALUES(5,18,'RECRUIT',0,'2023-01-30 08:48:45','2023-01-30 08:48:45','2023-01-30T17:48:43.302');
-- notification_group_user
REPLACE  INTO `notification_group_user` VALUES (1,1,18,'WAIT',0,'2023-01-30 07:28:05','2023-01-30 07:28:05');
REPLACE  INTO `notification_group_user` VALUES(2,1,19,'WAIT',0,'2023-01-30 07:29:58','2023-01-30 07:29:58');
REPLACE  INTO `notification_group_user` VALUES(3,2,19,'WAIT',0,'2023-01-30 07:39:40','2023-01-30 07:39:40');
REPLACE  INTO `notification_group_user` VALUES(4,3,18,'WAIT',0,'2023-01-30 07:47:00','2023-01-30 07:47:00');
REPLACE  INTO `notification_group_user` VALUES(5,4,20,'WAIT',0,'2023-01-30 08:04:14','2023-01-30 08:04:14');
REPLACE  INTO `notification_group_user` VALUES(6,4,18,'WAIT',0,'2023-01-30 08:18:18','2023-01-30 08:18:18');
REPLACE  INTO `notification_group_user` VALUES(7,4,19,'WAIT',0,'2023-01-30 08:18:18','2023-01-30 08:18:18');
REPLACE  INTO `notification_group_user` VALUES(8,4,20,'WAIT',0,'2023-01-30 08:18:18','2023-01-30 08:18:18');
REPLACE  INTO `notification_group_user` VALUES(9,5,18,'WAIT',0,'2023-01-30 08:48:45','2023-01-30 08:48:45');
-- orders
REPLACE  INTO `orders` VALUES (1,18,10000,'배송지','2023-01-19 04:52:44','2023-01-19 04:52:44',0,5000,500);
REPLACE  INTO `orders` VALUES (4,18,10000,'배송지2','2023-01-19 06:11:14','2023-01-19 06:11:14',0,5000,500);
REPLACE  INTO `orders` VALUES (5,18,10000,'배송지2','2023-01-19 06:12:03','2023-01-19 06:12:03',0,5000,500);
REPLACE  INTO `orders` VALUES (6,18,10000,'배송지2','2023-01-19 06:18:33','2023-01-19 06:18:33',0,5000,500);
REPLACE  INTO `orders` VALUES (7,18,10000,'배송지2','2023-01-19 06:31:48','2023-01-19 06:31:48',0,5000,500);
REPLACE  INTO `orders` VALUES (8,18,10000,'배송지2','2023-01-19 06:36:48','2023-01-19 06:36:48',0,5000,500);
REPLACE  INTO `orders` VALUES (9,18,10000,'배송지2','2023-01-19 07:19:13','2023-01-19 07:19:13',0,5000,500);
REPLACE  INTO `orders` VALUES (10,18,10000,'배송지2','2023-01-19 07:25:39','2023-01-19 07:25:39',0,5000,500);
REPLACE  INTO `orders` VALUES (11,18,10000,'배송지2','2023-01-19 07:38:16','2023-01-19 07:38:16',0,5000,500);
REPLACE  INTO `orders` VALUES (12,18,10000,'배송지2','2023-01-19 07:38:30','2023-01-19 07:38:30',0,5000,500);
REPLACE  INTO `orders` VALUES (13,18,10000,'배송지2','2023-01-19 07:41:19','2023-01-19 07:41:19',0,5000,500);
REPLACE  INTO `orders` VALUES (16,18,10000,'배송지2','2023-01-24 07:13:29','2023-01-24 07:13:29',0,5000,500);
REPLACE  INTO `orders` VALUES (17,18,10000,'배송지2','2023-02-02 02:27:16','2023-02-02 02:27:16',0,5000,500);
REPLACE  INTO `orders` VALUES (18,18,10000,'배송지2','2023-02-02 02:27:48','2023-02-02 02:27:48',0,5000,500);
REPLACE  INTO `orders` VALUES (19,18,10000,'배송지2','2023-02-02 02:29:20','2023-02-02 02:29:20',0,5000,500);
REPLACE  INTO `orders` VALUES (20,18,10000,'배송지2','2023-02-10 01:32:17','2023-02-10 01:32:17',0,5000,500);
-- orders_product_history
REPLACE  INTO `orders_product_history` VALUES (1,1,18,5,3000,500,'DELIVERY_READY','REFUND',0,'2023-01-19 04:52:44','2023-01-20 07:26:01',2);
REPLACE  INTO `orders_product_history` VALUES(2,4,18,5,3000,500,'DELIVERY_READY','REFUND',0,'2023-01-19 06:11:14','2023-01-20 07:50:34',2);
REPLACE  INTO `orders_product_history` VALUES(3,5,18,5,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:12:03','2023-01-20 03:05:22',2);
REPLACE  INTO `orders_product_history` VALUES(4,5,1,11,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:12:03','2023-01-23 01:29:56',2);
REPLACE  INTO `orders_product_history` VALUES(5,5,18,12,3000,500,'DELIVERY','PAYMENT_COMPLETE',0,'2023-01-19 06:12:04','2023-02-10 02:51:14',2);
REPLACE  INTO `orders_product_history` VALUES(6,6,18,11,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:18:33','2023-01-20 03:05:22',2);
REPLACE  INTO `orders_product_history` VALUES(7,6,18,12,3000,500,'DELIVERY','PAYMENT_COMPLETE',0,'2023-01-19 06:18:33','2023-01-31 06:56:28',2);
REPLACE  INTO `orders_product_history` VALUES(8,6,18,5,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:18:33','2023-01-20 03:05:22',1);
REPLACE  INTO `orders_product_history` VALUES(9,7,18,11,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:31:49','2023-01-20 03:05:22',2);
REPLACE  INTO `orders_product_history` VALUES(10,7,18,12,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:31:49','2023-01-20 03:05:22',2);
REPLACE  INTO `orders_product_history` VALUES(11,8,18,11,3000,500,'DELIVERY_READY','PAYMENT_COMPLETE',0,'2023-01-19 06:36:49','2023-01-20 03:05:22',2);
-- point
REPLACE  INTO `point` VALUES (2,100,'OBTAIN','로그인 포인트',18,'2023-01-14 06:29:03','2023-01-16 11:43:48',1);
REPLACE  INTO `point` VALUES (8,100,'OBTAIN','로그인 포인트',18,'2023-01-14 06:54:00','2023-01-14 06:54:00',0);
REPLACE  INTO `point` VALUES (10,100,'OBTAIN','로그인 포인트',18,'2023-01-14 07:02:03','2023-01-15 02:10:37',1);
REPLACE  INTO `point` VALUES (13,100,'USED','로그인 포인트',18,'2023-01-14 10:59:39','2023-01-15 02:09:43',0);
REPLACE  INTO `point` VALUES (14,100,'OBTAIN','로그인 포인트',18,'2023-01-15 04:14:02','2023-01-15 04:14:02',0);
REPLACE  INTO `point` VALUES (15,100,'USED','로그인 포인트',18,'2023-01-15 04:17:24','2023-01-15 04:22:33',0);
REPLACE  INTO `point` VALUES (16,100,'OBTAIN','로그인 포인트',1,'2023-01-15 04:27:18','2023-01-15 04:27:18',0);
REPLACE  INTO `point` VALUES (17,100,'OBTAIN','로그인 포인트',18,'2023-01-15 04:30:07','2023-01-15 04:30:07',0);
REPLACE  INTO `point` VALUES (18,100,'OBTAIN','로그인 포인트',18,'2023-01-15 06:49:52','2023-01-15 06:49:52',0);
REPLACE  INTO `point` VALUES (19,100,'OBTAIN','로그인 포인트',18,'2023-01-16 06:22:09','2023-01-16 06:22:09',0);
REPLACE  INTO `point` VALUES (21,5000,'USED','주문 결제 주문번호 : 1',18,'2023-01-19 04:52:44','2023-01-19 04:52:44',0);
REPLACE  INTO `point` VALUES (22,500,'OBTAIN','주문 적립 주문번호 : 1',18,'2023-01-19 04:52:44','2023-01-19 04:52:44',0);
REPLACE  INTO `point` VALUES (23,100,'OBTAIN','로그인 포인트',18,'2023-01-19 05:27:46','2023-01-19 05:27:46',0);
REPLACE  INTO `point` VALUES (24,5000,'USED','주문 결제 주문번호 : 4',18,'2023-01-19 06:11:14','2023-01-19 06:11:14',0);
REPLACE  INTO `point` VALUES (25,500,'OBTAIN','주문 적립 주문번호 : 4',18,'2023-01-19 06:11:14','2023-01-19 06:11:14',0);
