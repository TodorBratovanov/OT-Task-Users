ALTER TABLE `ot_users`.`users`
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
ADD INDEX `first_name_INDEX` (`first_name` ASC) VISIBLE,
ADD INDEX `last_name_INDEX` (`last_name` ASC) VISIBLE;
;
