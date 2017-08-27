CREATE TABLE `I_ANSWER_LOCALE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ANSWER_ID` INT NOT NULL,
  `LANGUAGE_ID` INT NOT NULL,
  `DESCRIPTION` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`),
--  UNIQUE INDEX `DESCRIPTION_UNIQUE` (`DESCRIPTION` ASC),
  CONSTRAINT `FK_ANSWER_LOCALE_LANGUAGE_ID_LANGUAGE`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `I_LANGUAGE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ANSWER_LOCALE_ANSWER_ID_ANSWER`
    FOREIGN KEY (`ANSWER_ID`)
    REFERENCES `I_ANSWER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(1,1,'A neighbor for what would have sat down in his apartment');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(2,1,'Neighbor to look for a new apartment together');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(3,1,'I want to find a free room to enter');

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(4,1,'Unacceptable');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(5,1,'There are no problems if the owner follows him');

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(6,1,'18-22');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(7,1,'23-27');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(8,1,'28-34');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(9,1,'35-41');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(10,1,'It does not matter to me');

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(11,1,'1500-2000');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(12,1,'2000-2500');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(13,1,'2500-3000');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(14,1,'3000-3500');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(15,1,'3500-4000');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(16,1,'It does not matter to me');



INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(1,2,'Соседа для того что бы подселись к себе в квартиру');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(2,2,'Соседа что-бы вместе искать новую квартиру');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(3,2,'Хочу найти свободную комнату что бы въехать');

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(4,2,'Непреемлемо');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(5,2,'Нет ни каких проблем, если хозяин за ним следит');

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(6,2,'18-22');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(7,2,'23-27');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(8,2,'28-34');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(9,2,'35-41');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(10,2,'Неважо');

INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(11,2,'1500-2000');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(12,2,'2000-2500');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(13,2,'2500-3000');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(14,2,'3000-3500');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(15,2,'3500-4000');
INSERT INTO `I_ANSWER_LOCALE`(`ANSWER_ID`,`LANGUAGE_ID`,`DESCRIPTION`)VALUES(16,2,'Неважо');
