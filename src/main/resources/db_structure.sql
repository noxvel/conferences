-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema conferences
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `conferences` ;

-- -----------------------------------------------------
-- Schema conferences
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `conferences` DEFAULT CHARACTER SET utf8 ;
USE `conferences` ;

-- -----------------------------------------------------
-- Table `conferences`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conferences`.`user_role` ;

CREATE TABLE IF NOT EXISTS `conferences`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conferences`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conferences`.`user` ;

CREATE TABLE IF NOT EXISTS `conferences`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `user_role_id` INT NOT NULL,
  `first_name` VARCHAR(255) NULL,
  `last_name` VARCHAR(255) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `receive_notifications` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_user_role_idx` (`user_role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_user_role`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `conferences`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `conferences`.`event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conferences`.`event` ;

CREATE TABLE IF NOT EXISTS `conferences`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `place` VARCHAR(255) NOT NULL,
  `begin_date` TIMESTAMP NOT NULL,
  `end_date` TIMESTAMP NOT NULL,
  `participants_came` INT ZEROFILL NULL,
  `description` VARCHAR(4096) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `conferences`.`report_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conferences`.`report_status` ;

CREATE TABLE IF NOT EXISTS `conferences`.`report_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `conferences`.`report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conferences`.`report` ;

CREATE TABLE IF NOT EXISTS `conferences`.`report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `topic` VARCHAR(255) NOT NULL,
  `speaker_id` INT NULL,
  `event_id` INT NOT NULL,
  `report_status_id` INT NOT NULL,
  `description` VARCHAR(4096) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_report_user1_idx` (`speaker_id` ASC) VISIBLE,
  INDEX `fk_report_report_status1_idx` (`report_status_id` ASC) VISIBLE,
  INDEX `fk_report_event1_idx` (`event_id` ASC) VISIBLE,
  CONSTRAINT `fk_report_user1`
    FOREIGN KEY (`speaker_id`)
    REFERENCES `conferences`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_report_status1`
    FOREIGN KEY (`report_status_id`)
    REFERENCES `conferences`.`report_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `conferences`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `conferences`.`event_has_participant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conferences`.`event_has_participant` ;

CREATE TABLE IF NOT EXISTS `conferences`.`event_has_participant` (
  `event_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`event_id`, `user_id`),
  INDEX `fk_event_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_event_has_user_event1_idx` (`event_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_has_user_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `conferences`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_event_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `conferences`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
