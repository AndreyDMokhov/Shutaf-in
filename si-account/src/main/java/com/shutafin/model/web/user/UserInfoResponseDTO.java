package com.shutafin.model.web.user;

import com.shutafin.model.entities.UserImage;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInfoResponseDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    private Integer languageId;
    private Integer countryId;
    private Integer cityId;
    private Integer genderId;

    private Long userImageId;
    private String userImage;

    private String facebookLink;
    private String profession;
    private String company;
    private String phoneNumber;

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public UserInfoResponseDTO(Long userId, String firstName, String lastName, String email, Integer languageId, Integer countryId, Integer cityId, Integer genderId, String facebookLink, String profession, String company, String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.languageId = languageId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.genderId = genderId;
        this.facebookLink = facebookLink;
        this.profession = profession;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

    public void addUserImage(UserImage userImage) {
        this.userImageId = userImage.getId();
        this.userImage = userImage.getImageStorage().getImageEncoded();
    }

}


