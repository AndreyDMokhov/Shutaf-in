package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSearchResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String image;

    private Integer genderId;

    private Integer cityId;

    private Integer countryId;

    private Long dateOfBirth;

}
