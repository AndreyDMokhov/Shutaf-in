package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSearchResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String userImage;

    private Long userImageId;

    private Long originalUserImageId;

    private Integer genderId;

    private Integer cityId;

    private Integer countryId;

    private Long dateOfBirth;

}
