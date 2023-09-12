DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(68) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` tinyint DEFAULT NULL,
  `smtp_config_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_smtp_config_id` (`smtp_config_id`),
  CONSTRAINT `fk_smtp_config_id` FOREIGN KEY (`smtp_config_id`) REFERENCES `smtp_config` (`id`)
) ENGINE=InnoDB
