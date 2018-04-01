CREATE TABLE `city` (
  `name` varchar(255) NOT NULL,
  `postcode` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `price_shipping` decimal(19,2) NOT NULL,
  PRIMARY KEY (`name`,`postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `newsletter_registration` (
  `email` varchar(255) NOT NULL,
  `city_name` varchar(255) NOT NULL,
  `city_postcode` int(11) NOT NULL,
  PRIMARY KEY (`email`),
  KEY (`city_name`,`city_postcode`),
  FOREIGN KEY (`city_name`, `city_postcode`) REFERENCES `city` (`name`, `postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `delivery_note` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `iban` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `city_name` varchar(255) NOT NULL,
  `city_postcode` int(11) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_email`),
  KEY (`city_name`,`city_postcode`),
  FOREIGN KEY (`user_email`) REFERENCES `user` (`email`),
  FOREIGN KEY (`city_name`, `city_postcode`) REFERENCES `city` (`name`, `postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `delivery_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliver_by` time NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `iban` varchar(255) NOT NULL,
  `account_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY (`account_id`),
  FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `delivery_note` varchar(255) NOT NULL,
  `delivery_time` datetime NOT NULL,
  `order_time` datetime NOT NULL,
  `postcode` int(11) NOT NULL,
  `price_shipping` decimal(19,2) NOT NULL,
  `account_id` int(11) NOT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY (`account_id`),
  KEY (`invoice_id`),
  FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_item` (
  `product` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `price_per_item` decimal(19,2) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `order_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`,`product`),
  FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `recurring_order` (
  `enabled` bit(1) NOT NULL,
  `account_id` int(11) NOT NULL,
  `delivery_slot_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`delivery_slot_id`),
  KEY (`delivery_slot_id`),
  FOREIGN KEY (`delivery_slot_id`) REFERENCES `delivery_slot` (`id`),
  FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `recurring_order_item` (
  `amount` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `recurring_order_account_id` int(11) NOT NULL,
  `recurring_order_delivery_slot_id` int(11) NOT NULL,
  PRIMARY KEY (`product_id`,`recurring_order_account_id`,`recurring_order_delivery_slot_id`),
  KEY (`recurring_order_account_id`,`recurring_order_delivery_slot_id`),
  FOREIGN KEY (`recurring_order_account_id`, `recurring_order_delivery_slot_id`) REFERENCES `recurring_order` (`account_id`, `delivery_slot_id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
