package com.shutafin.model.web.user;

import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    private String firstName;
    private String lastName;
    private String email;

    private Integer languageId;
    private Integer cityId;
    private Integer genderId;

    private Long userImageId;
    private String userImage;

    private String facebookLink;
    private String profession;
    private String company;
    private String phoneNumber;


    public UserInfoResponse(String firstName, String lastName, String email, Integer languageId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.languageId = languageId;
    }

    public void addUserInfo(UserInfo userInfo) {
        if (userInfo.getCurrentCity() != null) {
            cityId = userInfo.getCurrentCity().getId();
        }
        if (userInfo.getGender() != null) {
            genderId = userInfo.getGender().getId();
        }
        facebookLink = userInfo.getFacebookLink();
        profession = userInfo.getProfession();
        company = userInfo.getCompany();
        phoneNumber = userInfo.getPhoneNumber();
    }

    public void addUserImage(UserImage userImage) {
        this.userImageId = userImage.getId();
        this.userImage = userImage.getImageStorage().getImageEncoded();
    }

}


