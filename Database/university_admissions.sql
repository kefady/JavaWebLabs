-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema university_admissions
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `university_admissions` ;

-- -----------------------------------------------------
-- Schema university_admissions
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `university_admissions` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `university_admissions` ;

-- -----------------------------------------------------
-- Table `university_admissions`.`exam_name`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`exam_name` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`exam_name` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`exam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`exam` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`exam` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `exam_name_id` INT NOT NULL,
  `min_grade` INT NOT NULL,
  `coefficient` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `exam_name_idx` (`exam_name_id` ASC) VISIBLE,
  CONSTRAINT `exam_name`
    FOREIGN KEY (`exam_name_id`)
    REFERENCES `university_admissions`.`exam_name` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`department` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`department` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `budget_place` INT NOT NULL,
  `all_place` INT NOT NULL,
  `first_exam_id` INT NOT NULL,
  `second_exam_id` INT NOT NULL,
  `third_exam_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `d_first_exam_idx` (`first_exam_id` ASC) VISIBLE,
  INDEX `d_second_exam_idx` (`second_exam_id` ASC) VISIBLE,
  INDEX `d_third_exam_idx` (`third_exam_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `d_first_exam`
    FOREIGN KEY (`first_exam_id`)
    REFERENCES `university_admissions`.`exam` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `d_second_exam`
    FOREIGN KEY (`second_exam_id`)
    REFERENCES `university_admissions`.`exam` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `d_third_exam`
    FOREIGN KEY (`third_exam_id`)
    REFERENCES `university_admissions`.`exam` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`role` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_role_UNIQUE` (`role` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`user` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NULL DEFAULT '1',
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `patronymic` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `region` VARCHAR(255) NOT NULL,
  `edu_name` VARCHAR(255) NOT NULL,
  `birthday` DATE NOT NULL,
  `blocked` TINYINT NULL DEFAULT '0',
  `certificate` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `role_idx` (`role_id` ASC) INVISIBLE,
  CONSTRAINT `u_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `university_admissions`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`application`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`application` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`application` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `department_id` INT NOT NULL,
  `priority` INT NOT NULL,
  `final_grade` INT NULL DEFAULT NULL,
  `verified` TINYINT NULL DEFAULT '0',
  `accepted` TINYINT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_idx` (`user_id` ASC) VISIBLE,
  INDEX `department_idx` (`department_id` ASC) VISIBLE,
  CONSTRAINT `a_department`
    FOREIGN KEY (`department_id`)
    REFERENCES `university_admissions`.`department` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `a_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `university_admissions`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`certificate_grade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`certificate_grade` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`certificate_grade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `exam_name_id` INT NOT NULL,
  `grade` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_idx` (`user_id` ASC) VISIBLE,
  INDEX `exam_name_idx` (`exam_name_id` ASC) VISIBLE,
  CONSTRAINT `c_exam_name`
    FOREIGN KEY (`exam_name_id`)
    REFERENCES `university_admissions`.`exam_name` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `c_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `university_admissions`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `university_admissions`.`exam_grade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admissions`.`exam_grade` ;

CREATE TABLE IF NOT EXISTS `university_admissions`.`exam_grade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `exam_name_id` INT NOT NULL,
  `grade` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_idx` (`user_id` ASC) VISIBLE,
  INDEX `exam_name_idx` (`exam_name_id` ASC) VISIBLE,
  CONSTRAINT `eg_exam_name`
    FOREIGN KEY (`exam_name_id`)
    REFERENCES `university_admissions`.`exam_name` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `eg_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `university_admissions`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `university_admissions`.`exam_name`
-- -----------------------------------------------------
START TRANSACTION;
USE `university_admissions`;
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (1, 'Українська мова і література');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (2, 'Математика');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (3, 'Історія України');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (4, 'Географія');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (5, 'Біологія');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (6, 'Фізика');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (7, 'Хімія');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (8, 'Англійська мова');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (9, 'Німецька мова');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (10, 'Французька мова');
INSERT INTO `university_admissions`.`exam_name` (`id`, `name`) VALUES (11, 'Іспанська мова');

COMMIT;


-- -----------------------------------------------------
-- Data for table `university_admissions`.`exam`
-- -----------------------------------------------------
START TRANSACTION;
USE `university_admissions`;
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (1, 1, 125, 0.25);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (2, 1, 115, 0.35);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (3, 2, 125, 0.5);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (4, 2, 115, 0.35);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (5, 3, 125, 0.25);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (6, 3, 115, 0.35);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (7, 4, 125, 0.2);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (8, 4, 115, 0.2);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (9, 5, 125, 0.35);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (10, 5, 115, 0.2);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (11, 6, 125, 0.35);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (12, 6, 115, 0.2);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (13, 7, 125, 0.25);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (14, 7, 115, 0.2);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (15, 8, 125, 0.35);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (16, 8, 115, 0.2);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (17, 9, 125, 0.45);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (18, 9, 115, 0.25);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (19, 10, 125, 0.45);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (20, 10, 115, 0.25);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (21, 11, 125, 0.45);
INSERT INTO `university_admissions`.`exam` (`id`, `exam_name_id`, `min_grade`, `coefficient`) VALUES (22, 11, 115, 0.25);

COMMIT;


-- -----------------------------------------------------
-- Data for table `university_admissions`.`department`
-- -----------------------------------------------------
START TRANSACTION;
USE `university_admissions`;
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (1, 'Факультет інформатики та обчислювальної техніки', 48, 168, 1, 3, 16);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (2, 'Факультет прикладної математики', 27, 69, 1, 3, 12);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (3, 'Факультет біомедичної інженерії', 42, 88, 1, 3, 10);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (4, 'Приладобудiвний факультет', 16, 69, 1, 3, 12);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (5, 'Факультет соціології і права', 45, 102, 1, 6, 15);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (6, 'Факультет лінгвістики', 63, 164, 2, 5, 15);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (7, 'Факультет менеджменту та маркетингу', 35, 85, 2, 4, 5);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (8, 'Інженерно-хімічний факультет', 65, 174, 1, 3, 14);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (9, 'Факультет електроенерготехніки та автоматики', 38, 74, 1, 3, 12);
INSERT INTO `university_admissions`.`department` (`id`, `name`, `budget_place`, `all_place`, `first_exam_id`, `second_exam_id`, `third_exam_id`) VALUES (10, 'Факультет електроніки', 46, 157, 1, 3, 12);

COMMIT;


-- -----------------------------------------------------
-- Data for table `university_admissions`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `university_admissions`;
INSERT INTO `university_admissions`.`role` (`id`, `role`) VALUES (1, 'user');
INSERT INTO `university_admissions`.`role` (`id`, `role`) VALUES (2, 'admin');

COMMIT;

