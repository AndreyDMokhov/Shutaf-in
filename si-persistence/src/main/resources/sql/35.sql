CREATE TABLE `I_QUESTION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `IS_ACTIVE` BIT NULL DEFAULT 1,
  PRIMARY KEY (`ID`));

INSERT INTO `I_QUESTION` (`ID`,`IS_ACTIVE`) VALUES(1,TRUE);
INSERT INTO `I_QUESTION` (`ID`,`IS_ACTIVE`) VALUES(2,TRUE);
INSERT INTO `I_QUESTION` (`ID`,`IS_ACTIVE`) VALUES(3,TRUE);
INSERT INTO `I_QUESTION` (`ID`,`IS_ACTIVE`) VALUES(4,TRUE);