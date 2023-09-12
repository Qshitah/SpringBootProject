DROP TABLE IF EXISTS `smtp_config`;
CREATE TABLE `smtp_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `host` varchar(255) NOT NULL,
  `port` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB

