# Users schema

# --- !Ups

CREATE TABLE `Users` (
  `user_id`    INT(10)      NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(80)  NOT NULL,
  `last_name`  VARCHAR(80)  NOT NULL,
  `username`   VARCHAR(255) NOT NULL,
  `email`      VARCHAR(255) NOT NULL,
  `password`   VARCHAR(184) NOT NULL,
  `salt`       VARCHAR(184) NOT NULL,
  `birth`      DATE         NOT NULL,
  `gender`     ENUM ('M', 'F')       DEFAULT NULL,
  `country_id` INT(3)       NOT NULL,
  PRIMARY KEY (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


INSERT INTO `Users` (`user_id`, `first_name`, `last_name`, `username`, `email`, `password`, `salt`, `birth`, `gender`, `country_id`) VALUES
  (
    1, 'Admin', 'Admin', 'admin', 'igor@coder.io', '$2y$10$f5watXLN6fF.caKu2i1yuergTWASZZhRmW.kf14m1PI6zD1FVElFq',
    'Rm1NPZ5cxlIIRLvnbyuM', '2018-01-01', 'M', 54
  );


# --- !Downs

DROP TABLE `Users`;