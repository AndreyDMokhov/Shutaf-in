package com.shutafin.model.web.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class EmailWeb {

    @NotBlank
    @Email
    @Length(max=50)
    private String email;

    public EmailWeb() {
    }

    public EmailWeb(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
