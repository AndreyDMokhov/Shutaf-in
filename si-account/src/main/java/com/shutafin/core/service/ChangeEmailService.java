package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.EmailChangeWeb;

public interface ChangeEmailService {
    boolean changeEmail(User user, EmailChangeWeb emailChangeWeb);
}
