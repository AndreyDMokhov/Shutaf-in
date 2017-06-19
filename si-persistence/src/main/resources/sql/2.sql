CREATE TABLE `USER_CREDENTIALS` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `USER_ID` BIGINT NOT NULL,
  `PASSWORD_HASH` LONGTEXT NOT NULL,
  `PASSWORD_SALT` LONGTEXT NOT NULL,
  `CREATED_DATE` TIMESTAMP NOT NULL,
  `UPDATED_DATE` TIMESTAMP NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `USER_ID_UNIQUE` (`USER_ID` ASC),
  CONSTRAINT `FK_USER_CREDENTIALS_USER_ID_USERS`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `userdb`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
