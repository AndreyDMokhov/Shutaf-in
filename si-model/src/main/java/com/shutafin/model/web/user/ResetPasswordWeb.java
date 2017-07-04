package com.shutafin.model.web.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class ResetPasswordWeb {

    @NotBlank
    @Email
    @Length(max=50)
    private String email;

    @NotBlank
    @Length(min=8, max=25)
    private String newPassword;

    public ResetPasswordWeb() {
    }

    public ResetPasswordWeb(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
