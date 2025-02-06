# Host: localhost  (Version 5.5.5-10.4.28-MariaDB)
# Date: 2025-02-05 22:28:29
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "billservice"
#

CREATE TABLE `billservice` (
  `invoice_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`invoice_id`,`service_id`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `billservice_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`),
  CONSTRAINT `billservice_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "billservice"
#

INSERT INTO `billservice` VALUES (2,1);

#
# Structure for table "client"
#

CREATE TABLE `client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "client"
#

INSERT INTO `client` VALUES (1,'Ed','GANDRA',815566442),(2,'Leo','KK',784561234),(3,'Leo','KK',784561234),(4,'Teste','TESTE',777777777),(5,'Teste','TESTE',777777777),(6,'Teste','TESTE',777777777),(7,'Teste','TESTE',777777777),(8,'Ed','GANDRA',815566442),(9,'Teste','TESTE',777777777),(10,'Aaa','AAA',888888888);

#
# Structure for table "product"
#

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "product"
#

INSERT INTO `product` VALUES (1,'Pomada para Cabelo','Pomada fixadora',3,20),(2,'Shampoo','Shampoo Clear Men',5,15);

#
# Structure for table "service"
#

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "service"
#

INSERT INTO `service` VALUES (1,'Corte de Cabelo','Corte de cabelo tradicional',30),(2,'Barba 1','Corte de Barba',10);

#
# Structure for table "event"
#

CREATE TABLE `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `client_id` (`client_id`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `event_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `event_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "event"
#

INSERT INTO `event` VALUES (4,'2025-02-05 18:20:00',1,1,'Corte de cabelo tradicional',2,0),(5,'2025-02-06 18:25:00',3,1,'Corte de cabelo tradicional',2,2),(6,'2025-02-07 21:30:00',4,1,'Corte de cabelo tradicional',1,0),(7,'2025-02-08 22:51:00',10,1,'Corte de cabelo tradicional',0,0);

#
# Structure for table "invoice"
#

CREATE TABLE `invoice` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `reductions` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `client_id` (`client_id`),
  KEY `fk_invoice_event` (`event_id`),
  CONSTRAINT `fk_invoice_event` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "invoice"
#

INSERT INTO `invoice` VALUES (1,2,5,20,0,20,24),(2,3,5,50,10,0,45);

#
# Structure for table "billproduct"
#

CREATE TABLE `billproduct` (
  `invoice_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  KEY `invoice_id` (`invoice_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `billproduct_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`),
  CONSTRAINT `billproduct_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

#
# Data for table "billproduct"
#

INSERT INTO `billproduct` VALUES (1,1),(2,1);
