USE ot_users;

CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(40) NOT NULL,
  `last_name` VARCHAR(40) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
--   UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
--   INDEX `first_name_IDX` (`first_name` ASC) VISIBLE,
--   INDEX `last_name_IDX` (`last_name` ASC) VISIBLE);
