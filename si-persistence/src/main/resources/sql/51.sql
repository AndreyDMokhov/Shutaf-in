CREATE TABLE `USER_MATCHING_SCORE` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `CREATED_DATE` TIMESTAMP NOT NULL,
  `UPDATED_DATE` TIMESTAMP NULL,
  `USER_ORIGIN_ID` BIGINT NOT NULL,
  `USER_TO_MATCH_ID` BIGINT NOT NULL,
  `SCORE` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK_USER_MATCHING_SCORE_USER_ORIGIN_ID_USER_idx` (`USER_ORIGIN_ID` ASC),
  INDEX `FK_USER_MATCHING_SCORE_USER_TO_MATCH_ID_USER_idx` (`USER_TO_MATCH_ID` ASC),
  CONSTRAINT `FK_USER_MATCHING_SCORE_USER_ORIGIN_ID_USER`
    FOREIGN KEY (`USER_ORIGIN_ID`)
    REFERENCES `userdb`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_USER_MATCHING_SCORE_USER_TO_MATCH_ID_USER`
    FOREIGN KEY (`USER_TO_MATCH_ID`)
    REFERENCES `userdb`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

