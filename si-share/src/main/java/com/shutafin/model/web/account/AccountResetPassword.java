package com.shutafin.model.web.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResetPassword {

    @NotNull
    @Min(1)
    private Long userId;

    @NotBlank
    @Length(max = 25, min = 8)
    private String newPassword;
}
