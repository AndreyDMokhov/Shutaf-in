package com.shutafin.model.web.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountEmailChangeRequest {

    @NotBlank
    @Email
    @Length(max=50)
    private String newEmail;
}
