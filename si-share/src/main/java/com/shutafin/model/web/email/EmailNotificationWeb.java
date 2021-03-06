package com.shutafin.model.web.email;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationWeb {

    @Min(1)
    @NotNull
    private Long userId;

    @Email
    @Length(max = 50)
    @NotBlank
    private String emailTo;

    @Email
    @Length(max = 50)
    private String emailChange;

    @NotBlank
    @Length(min = 2, max = 3)
    private String languageCode;

    @NotNull
    private EmailReason emailReason;

    private Set<EmailUserImageSource> emailUserImageSources;
}
