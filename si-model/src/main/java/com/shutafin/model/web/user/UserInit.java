package com.shutafin.model.web.user;

import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.model.web.DataResponse;

public class UserInit implements DataResponse {

    private String firstName;
    private String lastName;
    private LanguageEnum languageId;
    private Long userImageId;
    private String userImage;

    public UserInit(String firstName, String lastName, LanguageEnum languageId, Long userImageId, String userImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.languageId = languageId;
        this.userImageId = userImageId;
        this.userImage = userImage;
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

    public LanguageEnum getLanguageId() {
        return languageId;
    }

    public void setLanguageId(LanguageEnum languageId) {
        this.languageId = languageId;
    }

    public Long getUserImageId() {
        return userImageId;
    }

    public void setUserImageId(Long userImageId) {
        this.userImageId = userImageId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


}


