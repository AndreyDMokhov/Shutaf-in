package com.shutafin.repository.account;

import com.shutafin.model.entities.User;

public interface UserLoginLogRepositoryCustom {
    boolean hasExceededMaxLoginTries(User user, int nMaxTries, int timeForTriesInMin);
}
