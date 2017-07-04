package com.shutafin.service;

import com.shutafin.model.web.user.ChangePasswordWeb;

public interface ChangePasswordService {

    String changePassword(ChangePasswordWeb changePasswordWeb, String session_id);
}
