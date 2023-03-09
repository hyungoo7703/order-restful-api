CREATE TABLE `localschema`.`ataglance_menu_category` (
  `CATEGORY_ID` VARCHAR(45) NOT NULL,
  `CATEGORY_NAME` VARCHAR(45) NULL,
  `DATE_CREATED` DATETIME NULL,
  `DATE_UPDATED` DATETIME NULL,
  `ENABLED` BIT(1) NULL,
  PRIMARY KEY (`CATEGORY_ID`));

CREATE TABLE `localschema`.`ataglance_menu_theme` (
  `THEME_ID` VARCHAR(45) NOT NULL,
  `THEME_NAME` VARCHAR(45) NULL,
  `DATE_CREATED` DATETIME NULL,
  `DATE_UPDATED` DATETIME NULL,
  `ENABLED` BIT(1) NULL,
  `FK_CATEGORY_ID` VARCHAR(45) NULL,
  PRIMARY KEY (`THEME_ID`));

CREATE TABLE `localschema`.`ataglance_menu_detail` (
  `DETAIL_ID` VARCHAR(45) NOT NULL,
  `DETAIL_NAME` VARCHAR(45) NULL,
  `PRICE` INT NULL,
  `MARGIN` INT NULL,
  `MEMO` TEXT NULL,
  `DATE_CREATED` DATETIME NULL,
  `DATE_UPDATED` DATETIME NULL,
  `ENABLED` BIT(1) NULL,
  `ON_EVENT` BIT(1) NULL DEFAULT 0,
  `FK_THEME_ID` VARCHAR(45) NULL,
  PRIMARY KEY (`DETAIL_ID`));

ALTER TABLE `localschema`.`ataglance_menu_theme`
ADD INDEX `FK_CATEGORY_ID_idx` (`FK_CATEGORY_ID` ASC) VISIBLE;
;
ALTER TABLE `localschema`.`ataglance_menu_theme`
ADD CONSTRAINT `FK_CATEGORY_ID`
  FOREIGN KEY (`FK_CATEGORY_ID`)
  REFERENCES `localschema`.`ataglance_menu_category` (`CATEGORY_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `localschema`.`ataglance_menu_detail`
ADD INDEX `FK_THEME_ID_idx` (`FK_THEME_ID` ASC) VISIBLE;
;
ALTER TABLE `localschema`.`ataglance_menu_detail`
ADD CONSTRAINT `FK_THEME_ID`
  FOREIGN KEY (`FK_THEME_ID`)
  REFERENCES `localschema`.`ataglance_menu_theme` (`THEME_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `localschema`.`ataglance_menu_order` (
  `ORDER_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `ORDER_NUMBER` VARCHAR(10) NULL,
  `ORDER_STATUS` VARCHAR(45) NULL,
  `DATE_CREATED` DATETIME NULL,
  `DATE_UPDATED` DATETIME NULL,
  PRIMARY KEY (`ORDER_ID`));

CREATE TABLE `localschema`.`ataglance_menu_order_detail` (
  `ORDER_DETAIL_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `ORDER_ID` BIGINT NULL,
  `DETAIL_ID` VARCHAR(45) NULL,
  `QUANTITY` INT NULL,
  PRIMARY KEY (`ORDER_DETAIL_ID`));

ALTER TABLE `localschema`.`ataglance_menu_order_detail`
ADD INDEX `ORDER_ID_idx` (`ORDER_ID` ASC) VISIBLE,
ADD INDEX `DETAIL_ID_idx` (`DETAIL_ID` ASC) VISIBLE;
;
ALTER TABLE `localschema`.`ataglance_menu_order_detail`
ADD CONSTRAINT `ORDER_ID`
  FOREIGN KEY (`ORDER_ID`)
  REFERENCES `localschema`.`ataglance_menu_order` (`ORDER_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `DETAIL_ID`
  FOREIGN KEY (`DETAIL_ID`)
  REFERENCES `localschema`.`ataglance_menu_detail` (`DETAIL_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;