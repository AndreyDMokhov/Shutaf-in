ALTER TABLE `EMAIL_CHANGE_CONFIRMATION`
CHANGE COLUMN `CRAETED_DATE` `CREATED_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
CHANGE COLUMN `UPDATED_DATE` `UPDATED_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ;