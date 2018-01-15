# Users schema

# --- !Ups
CREATE TABLE `Users` (
  `user_id`    INT(10)  UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(80)       NOT NULL,
  `last_name`  VARCHAR(80)       NOT NULL,
  `username`   VARCHAR(255)      NOT NULL,
  `email`      VARCHAR(255)      NOT NULL,
  `password`   VARCHAR(184)      NOT NULL,
  `salt`       VARCHAR(184)      NOT NULL,
  `birth`      DATE              NOT NULL,
  `gender`     ENUM ('M', 'F')            DEFAULT NULL,
  `country_id` INT(3)            NOT NULL,
  PRIMARY KEY (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX `idx_username`
  ON `Users` (`username`);
CREATE INDEX `idx_email`
  ON `Users` (`email`);

CREATE TABLE `Roles` (
  `role_id` INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(50)          NOT NULL,
  PRIMARY KEY (`role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `Permissions` (
  `perm_id` INT(10)     UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(50)          NOT NULL,
  `action`  VARCHAR(50)          NOT NULL,
  PRIMARY KEY (`perm_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `User_Role` (
  `user_id` INT(10) UNSIGNED NOT NULL,
  `role_id` INT(10) UNSIGNED NOT NULL,
  FOREIGN KEY (`role_id`) REFERENCES Roles (`role_id`),
  FOREIGN KEY (`user_id`) REFERENCES Users (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX `idx_role_id`
  ON `User_Role` (`role_id`);
CREATE INDEX `idx_user_id`
  ON `User_Role` (`user_id`);

CREATE TABLE `Role_Permission` (
  `role_id` INT(10) UNSIGNED NOT NULL,
  `perm_id` INT(10) UNSIGNED NOT NULL,
  FOREIGN KEY (`role_id`) REFERENCES Roles (`role_id`),
  FOREIGN KEY (`perm_id`) REFERENCES Permissions (`perm_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX `idx_role_id`
  ON `Role_Permission` (`role_id`);
CREATE INDEX `idx_perm_id`
  ON `Role_Permission` (`perm_id`);

INSERT INTO `Users` (`user_id`, `first_name`, `last_name`, `username`, `email`, `password`, `salt`, `birth`, `gender`, `country_id`)
VALUES
  (
    1, 'Admin', 'Admin', 'admin', 'igor@coder.io', '$2a$10$wdQWluFjGuxldC.Wmam0q.sHA8AXcAlWKecqauxvDRzHOTRTKRmJa',
    '$2a$10$wdQWluFjGuxldC.Wmam0q.', '2018-01-01', 'M', 54
  );

INSERT INTO `Roles` (`role_id`, `name`) VALUES (1, 'Admin');

INSERT INTO `Permissions` (`perm_id`, `name`, `action`) VALUES (1, 'Admin Access', '*');

INSERT INTO `Role_Permission` (`role_id`, `perm_id`) VALUES (1, 1);

INSERT INTO `User_Role` (`user_id`, `role_id`) VALUES (1, 1);

# --- !Downs

DROP TABLE `Role_Permission`;
DROP TABLE `User_Role`;
DROP TABLE `Permissions`;
DROP TABLE `Users`;
DROP TABLE `Roles`;