CREATE TABLE `I_CITY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `LANGUAGE_ID` INT NOT NULL,
  `COUNTRY_ID` INT NOT NULL,
  `DESCRIPTION` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_CITY_LANGUAGE_ID_LANGUAGE`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `I_LANGUAGE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CITY_COUNTRY_ID_COUNTRY`
    FOREIGN KEY (`COUNTRY_ID`)
    REFERENCES `I_COUNTRY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(1,1,1,'Jerusalem');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(2,1,1,'Tel Aviv');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(3,1,1,'Haifa');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(4,1,1,'Beersheva');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(5,1,1,'Rehovot');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(6,2,2,'Иерусалим');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(7,2,2,'Тель Авив');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(8,2,2,'Хайфа');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(9,2,2,'Беэр Шева');
INSERT INTO `i_city`(`ID`,`LANGUAGE_ID`,`COUNTRY_ID`,`DESCRIPTION`) VALUES(10,2,2,'Реховот');