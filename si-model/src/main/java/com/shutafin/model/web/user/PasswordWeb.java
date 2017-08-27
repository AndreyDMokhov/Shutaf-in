package com.shutafin.model.web.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class PasswordWeb {

    @NotBlank
    @Length(min=8, max=25)
    private String newPassword;

    public PasswordWeb() {
    }

    public PasswordWeb(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
