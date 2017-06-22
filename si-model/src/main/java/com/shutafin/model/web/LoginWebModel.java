package com.shutafin.model.web;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * Created by Rogov on 22.06.2017.
 */
public class LoginWebModel implements DataResponse {

//    @NotBlank
//    @Max(value = 50)
    @Email
    @Length(max = 50)
    @NotBlank
    private String email;

//    @Min(value = 8)
    @NotBlank
    @Length(min = 8, max = 25)
    private String password;



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
