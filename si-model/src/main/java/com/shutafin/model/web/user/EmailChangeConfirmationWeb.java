package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmailChangeConfirmationWeb {

    @NotBlank
    @Length(max=50)
    private String userPassword;

    @NotBlank
    @Email
    @Length(max=50)
    private String newEmail;

}
