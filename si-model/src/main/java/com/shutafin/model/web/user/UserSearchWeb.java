package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;

public class UserSearchWeb implements DataResponse {

    private String firstName;

    private String lastName;

    private String image;

    public UserSearchWeb() {
    }

    public UserSearchWeb(String firstName, String lastName/*, String image*/) {
    //    this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
