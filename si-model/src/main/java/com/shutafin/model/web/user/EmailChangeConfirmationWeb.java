package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class EmailChangeConfirmationWeb implements DataResponse {

    @NotBlank
    @Length(max=50)
    private String userPassword;

    @NotBlank
    @Email
    @Length(max=50)
    private String newEmail;

    public EmailChangeConfirmationWeb() {
    }

    public EmailChangeConfirmationWeb(String userPassword, String newEmail) {
        this.userPassword = userPassword;
        this.newEmail = newEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
