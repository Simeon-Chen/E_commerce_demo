CREATE TABLE IF NOT EXISTS `product` (
    `product_id`         INT          NOT NULL AUTO_INCREMENT,
    `product_name`       VARCHAR(256) NOT NULL,
    `category`           VARCHAR(256) NOT NULL,
    `image_url`          VARCHAR(256) NOT NULL,
    `price`              INT          NOT NULL,
    `stock`              INT          NOT NULL,
    `description`        VARCHAR(256),
    `created_date`       TIMESTAMP    NOT NULL,
    `last_modified_date` TIMESTAMP    NOT NULL,
    PRIMARY KEY (`product_id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `user_id`            INT          NOT NULL AUTO_INCREMENT,
    `email`              VARCHAR(256) NOT NULL UNIQUE,
    `password`           VARCHAR(256) NOT NULL,
    `created_date`       TIMESTAMP    NOT NULL,
    `last_modified_date` TIMESTAMP    NOT NULL,
    PRIMARY KEY (`user_id`)
);
