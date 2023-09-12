
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token_value` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  `expiration_datetime` timestamp NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB 
