package com.shutafin.model.web.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoRequest {

    @NotBlank
    @Length(min=3, max=50)
    private String firstName;

    @NotBlank
    @Length(min=3, max=50)
    private String lastName;

    @NotBlank
    @Email
    @Length(max=50)
    private String email;

    @Min(value = 1)
    private Integer cityId;
    @Min(value = 1)
    private Integer genderId;

    @Length(max = 255)
    private String facebookLink;
    @Length(max = 255)
    private String profession;
    @Length(max = 255)
    private String company;
    @Length(max = 255)
    private String phoneNumber;


}
