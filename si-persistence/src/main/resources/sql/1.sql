CREATE TABLE `USER` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(50) NOT NULL,
  `LAST_NAME` VARCHAR(50) NOT NULL,
  `EMAIL` VARCHAR(50) NOT NULL,
  `CREATED_DATE` TIMESTAMP NOT NULL,
  `UPDATED_DATE` TIMESTAMP NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC));