package com.company.pojo;

import com.company.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Rina on 6/15/2017.
 */
public class UserBooks {

    @NotNull
    @Size(min = 2, max = 14)
    private String bookName;

    @Pattern(regexp = "[0-9]+", message = "Wrong userPhone!")
    private String userPhone;
    @Pattern(regexp = ".+@.+\\..+", message = "Wrong email!")
    private String email;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
