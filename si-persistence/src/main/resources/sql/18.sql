<<<<<<< HEAD
ALTER TABLE `EMAIL_CHANGE_CONFIRMATION`
CHANGE COLUMN `CRAETED_DATE` `CREATED_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
CHANGE COLUMN `UPDATED_DATE` `UPDATED_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ;
=======
ALTER TABLE `userdb`.`IMAGE_STORAGE`
DROP FOREIGN KEY `FK_IMAGE_STORAGE_USER_IMAGE_ID_USER_IMAGE`;
ALTER TABLE `userdb`.`IMAGE_STORAGE`
CHANGE COLUMN `USER_IAMGE_ID` `USER_IMAGE_ID` BIGINT(20) NULL DEFAULT NULL ;
ALTER TABLE `userdb`.`IMAGE_STORAGE`
ADD CONSTRAINT `FK_IMAGE_STORAGE_USER_IMAGE_ID_USER_IMAGE`
  FOREIGN KEY (`USER_IMAGE_ID`)
  REFERENCES `userdb`.`USER_IMAGE` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
>>>>>>> marina/change-email
