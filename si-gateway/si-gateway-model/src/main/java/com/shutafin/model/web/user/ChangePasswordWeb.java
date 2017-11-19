package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChangePasswordWeb {

    @NotBlank
    @Length(min = 8, max = 25)
    private String oldPassword;

    @NotBlank
    @Length(min = 8, max = 25)
    private String newPassword;

}