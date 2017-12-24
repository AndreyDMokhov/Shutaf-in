package com.shutafin.model.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDate;

import java.util.Date;

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

    private Integer genderId;

    private Integer cityId;

    private Integer countryId;

    private Integer age;

    private Integer score;

    public UserSearchResponse(Long userId, String firstName, String lastName, String userImage, Long userImageId, Integer genderId, Integer cityId, Integer countryId, Date dateOfBirth) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userImage = userImage;
        this.userImageId = userImageId;
        this.genderId = genderId;
        this.cityId = cityId;
        this.countryId = countryId;
        this.age = org.joda.time.Years.yearsBetween(new LocalDate(dateOfBirth), LocalDate.now()).getYears();
    }

}
