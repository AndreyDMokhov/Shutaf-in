ALTER TABLE `userdb`.`email_notification_log`
ADD COLUMN `EMAIL_HEADER` VARCHAR(100) NOT NULL AFTER `IS_SEND_FAILED`;