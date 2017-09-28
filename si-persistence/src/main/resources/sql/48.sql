CREATE TABLE `I_ANSWER_EXTENDED` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ANSWER_ID` INT NOT NULL,
  `QUESTION_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_ANSWER_EXTENDED_QUESTION_ID_QUESTION_idx` (`QUESTION_ID` ASC));

INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(1,1);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(2,1);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(3,1);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(4,1);

INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(5,2);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(6,2);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(7,2);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(8,2);

INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(9,3);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(10,3);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(11,3);

INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(12,4);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(13,4);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(14,4);
INSERT INTO `I_ANSWER_EXTENDED` (`ANSWER_ID`,`QUESTION_ID`) VALUES(15,4);