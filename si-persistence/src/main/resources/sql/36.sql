CREATE TABLE `user_mandatory_match_result` (
  `USER_ID` bigint(20) NOT NULL,
  `QUESTION_ID` int(11) NOT NULL,
  `ANSWER_ID` int(11) NOT NULL,
  UNIQUE KEY `USER_ID_UNIQUE` (`USER_ID`),
  UNIQUE KEY `QUESTION_ID_UNIQUE` (`QUESTION_ID`),
  UNIQUE KEY `ANSWER_ID_UNIQUE` (`ANSWER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;