CREATE TABLE `EMAIL_CHANGE_CONFIRMATION` (
  `ID` BIGINT NOT NULL,
  `USER_ID` BIGINT NOT NULL,
  `IS_NEW_EMAIL` BIT NOT NULL,
  `UPDATE_EMAIL_ADDRESS` VARCHAR(50) NULL,
  `URL_LINK` VARCHAR(50) NOT NULL,
  `IS_CONFIRMED` BIT NOT NULL,
  `CONNECTED_ID` BIGINT NULL,
  `EXPIRES_AT` TIMESTAMP NOT NULL,
  `CRAETED_DATE` TIMESTAMP NOT NULL,
  `UPDATED_DATE` TIMESTAMP NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_EMAIL_CHANGE_CONFIRMATION_USER_ID_USER_idx` (`USER_ID` ASC),
  CONSTRAINT `FK_EMAIL_CHANGE_CONFIRMATION_USER_ID_USER`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);