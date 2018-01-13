package com.shutafin.model.web.account;

import lombok.*;

import java.util.Date;

/**
 * @author Edward Kats
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SuppressWarnings("PMD.TooManyFields")
public class AccountUserInfoResponseDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    private Integer languageId;
    private Integer countryId;
    private Integer cityId;
    private Integer genderId;
    private Date dateOfBirth;

    private AccountStatus accountStatus;

    private Long userImageId;
    private String userImage;

    private String facebookLink;
    private String profession;
    private String company;
    private String phoneNumber;

    private Long originalUserImageId;
    private String originalUserImage;

    @SuppressWarnings("PMD.ExcessiveParameterList")
    public AccountUserInfoResponseDTO(
            Long userId,
            String firstName,
            String lastName,
            String email,
            Integer languageId,
            Integer countryId,
            Integer cityId,
            Integer genderId,
            Date dateOfBirth,
            AccountStatus accountStatus,
            String facebookLink,
            String profession,
            String company,
            String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.languageId = languageId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.genderId = genderId;
        this.dateOfBirth = dateOfBirth;
        this.accountStatus = accountStatus;
        this.facebookLink = facebookLink;
        this.profession = profession;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

    public void addUserImage(Long userImageId, String imageEncoded) {
        this.userImageId = userImageId;
        this.userImage = imageEncoded;
    }

    public void addOriginalUserImage(Long userImageId, String imageEncoded) {
        this.originalUserImageId = userImageId;
        this.originalUserImage = imageEncoded;
    }
}

