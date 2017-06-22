package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationRequestWeb implements DataResponse {

    @Min(value = 1)
    private Long userId;

    @NotBlank
    @Size(min=3, max=50)
    private String firstName;

    @NotBlank
    @Size(min=3, max=50)
    private String lastName;

    @NotBlank
    @Email
    @Size(max=25)
    private String email;

    @NotBlank
    @Size(min=8, max=25)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String password;

    public RegistrationRequestWeb() {
    }

    public RegistrationRequestWeb(Long userId, String firstName, String lastName, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
