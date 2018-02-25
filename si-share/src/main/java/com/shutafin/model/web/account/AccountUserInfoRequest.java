package com.shutafin.model.web.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountUserInfoRequest {

    @NotBlank
    @Length(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @Email
    @Length(max = 50)
    private String email;

    @Min(value = 1)
    private Integer cityId;
    @Min(value = 1)
    private Integer genderId;

    @JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date dateOfBirth;

    @Length(max = 255)
    private String facebookLink;
    @Length(max = 255)
    private String profession;
    @Length(max = 255)
    private String company;
    @Length(max = 255)
    private String phoneNumber;

}
