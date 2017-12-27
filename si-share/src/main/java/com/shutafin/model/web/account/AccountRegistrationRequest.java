package com.shutafin.model.web.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountRegistrationRequest {

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

    @NotBlank
    @Length(min=8, max=25)
    private String password;

    @NotNull
    @Min(value = 1)
    private Integer userLanguageId;

}
