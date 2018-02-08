package com.shutafin.model.web.email;

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
public class EmailUserLanguage {

    @Min(1)
    @NotNull
    private Long userId;

    @Email
    @Length(max = 50)
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 2, max = 3)
    private String languageCode;
}
