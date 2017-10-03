ALTER TABLE `USER_ACCOUNT`
ADD COLUMN `COMPRESSED_USER_IMAGE_ID` BIGINT(20) NULL AFTER `USER_IMAGE_ID`,
ADD INDEX `FK_USER_ACCOUNT_COMPRESSED_USER_IMAGE_ID_USER_IMAGE_ID_idx` (`COMPRESSED_USER_IMAGE_ID` ASC);
ALTER TABLE `USER_ACCOUNT`
ADD CONSTRAINT `FK_USER_ACCOUNT_COMPRESSED_USER_IMAGE_ID_USER_IMAGE_ID`
  FOREIGN KEY (`COMPRESSED_USER_IMAGE_ID`)
  REFERENCES `userdb`.`user_image` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;