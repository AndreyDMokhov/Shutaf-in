  CREATE TABLE `USER_QUESTION_ANSWER_CITY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` BIGINT(20) NOT NULL,
  `QUESTION_ID` INT(11) NOT NULL,
  `CITY_ID` INT(11) NOT NULL,
  PRIMARY KEY (`ID`)
);