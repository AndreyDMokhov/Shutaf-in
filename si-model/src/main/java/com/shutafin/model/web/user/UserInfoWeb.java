package com.shutafin.model.web.user;


import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UserInfoWeb implements DataResponse {

    @Min(value = 1)
    private Long userId;

    @Min(value = 1)
    private Integer genderId;

    @Min(value = 18)
    @Max(value = 150)
    private Integer age;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    public UserInfoWeb() {
    }

    public UserInfoWeb(Long userId, Integer genderId, Integer age, String firstName, String lastName, String email) {
        this.userId = userId;
        this.genderId = genderId;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
}
