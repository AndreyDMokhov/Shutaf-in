package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Deprecated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailWeb {

    @NotBlank
    @Email
    @Length(max=50)
    private String email;


}
