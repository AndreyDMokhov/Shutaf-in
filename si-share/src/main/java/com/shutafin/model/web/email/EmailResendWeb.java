package com.shutafin.model.web.email;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailResendWeb {

    @Email
    @Length(max = 50)
    @NotBlank
    private String emailTo;

    @NotNull
    private EmailReason emailReason;

}
