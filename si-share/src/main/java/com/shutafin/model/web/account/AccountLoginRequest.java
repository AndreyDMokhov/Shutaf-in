package com.shutafin.model.web.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Edward Kats
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountLoginRequest {
    @Email
    @Length(max = 50)
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 25)
    private String password;
}
