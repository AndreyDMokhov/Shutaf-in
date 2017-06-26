package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class RegistrationRequestWeb implements DataResponse {

    @NotBlank
    @Length(min=3, max=50)
    private String firstName;

    @NotBlank
    @Length(min=3, max=50)
    private String lastName;

    @NotBlank
    @Email
    @Length(max=50)
    private String email;

    @NotBlank
    @Length(min=8, max=25)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String password;

    public RegistrationRequestWeb() {
    }

    public RegistrationRequestWeb(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
