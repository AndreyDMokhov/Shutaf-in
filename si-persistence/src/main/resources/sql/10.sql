CREATE TABLE `I_COUNTRY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `LANGUAGE_ID` INT NOT NULL,
  `DESCRIPTION` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_COUNTRY_LANGUAGE_ID_LANGUAGE`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `I_LANGUAGE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `i_country` (`ID`,`LANGUAGE_ID`,`DESCRIPTION`) VALUES(1,1,'Israel');
INSERT INTO `i_country` (`ID`,`LANGUAGE_ID`,`DESCRIPTION`) VALUES(2,2,'Израиль');