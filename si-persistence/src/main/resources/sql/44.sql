ALTER TABLE `USER_IMAGE`
ADD COLUMN `PERMISSION_TYPE_ID` INT(11) NOT NULL DEFAULT 1 AFTER `UPDATED_DATE`;
ALTER TABLE `USER_IMAGE`
ADD COLUMN `COMPRESSION_TYPE_ID` INT(11) NOT NULL DEFAULT 0 AFTER `PERMISSION_TYPE_ID`;
