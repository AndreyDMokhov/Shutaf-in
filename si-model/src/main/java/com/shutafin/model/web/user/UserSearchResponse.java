package com.shutafin.model.web.user;

import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.DataResponse;

public class UserSearchResponse implements DataResponse {

    private String firstName;

    private String lastName;

  //  private UserImage image;

    public UserSearchResponse() {
    }

    public UserSearchResponse(String firstName, String lastName/*, UserImage image*/) {
   //     this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /*public UserImage getImage() {
        return image;
    }

    public void setImage(UserImage image) {
        this.image = image;
    }*/

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
