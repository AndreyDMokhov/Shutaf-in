CREATE TABLE `I_QUESTION_EXTENDED_LOCALE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `QUESTION_ID` INT NOT NULL,
  `LANGUAGE_ID` INT NOT NULL,
  `DESCRIPTION` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `DESCRIPTION_UNIQUE` (`DESCRIPTION` ASC),
  CONSTRAINT `FK_QUESTION_EXTENDED_LOCALE_LANGUAGE_ID_LANGUAGE`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `I_LANGUAGE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_QUESTION_EXTENDED_LOCALE_QUESTION_ID_QUESTION_EXTENDED`
    FOREIGN KEY (`QUESTION_ID`)
    REFERENCES `I_QUESTION_EXTENDED` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(1,1,'Choose your religion');
INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(2,1,'Choose your political view');
INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(3,1,'Choose your gastronomical orientation');
INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(4,1,'What describes you the best');

INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(1,2,'Выберите вашу религию');
INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(2,2,'Укажите ваши политические взгляды');
INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(3,2,'Ваши предпочтения в еде');
INSERT INTO `I_QUESTION_EXTENDED_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(4,2,'Какой пункт вас описывает лучше всего');