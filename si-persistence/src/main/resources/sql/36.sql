CREATE TABLE `I_QUESTION_LOCALE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `QUESTION_ID` INT NOT NULL,
  `LANGUAGE_ID` INT NOT NULL,
  `DESCRIPTION` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `DESCRIPTION_UNIQUE` (`DESCRIPTION` ASC),
  CONSTRAINT `FK_QUESTION_LOCALE_LANGUAGE_ID_LANGUAGE`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `I_LANGUAGE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_QUESTION_LOCALE_QUESTION_ID_QUESTION`
    FOREIGN KEY (`QUESTION_ID`)
    REFERENCES `I_QUESTION` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(1,1,'I''m looking for');
INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(2,1,'Attitude to pets');
INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(3,1,'Age range');
INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(4,1,'Payment limits');

INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(1,2,'я ищу');
INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(2,2,'Отножение к домашним животным');
INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(3,2,'Возрастной диапазон');
INSERT INTO `I_QUESTION_LOCALE`(`QUESTION_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(4,2,'Мой денежный лимит в месяц');
