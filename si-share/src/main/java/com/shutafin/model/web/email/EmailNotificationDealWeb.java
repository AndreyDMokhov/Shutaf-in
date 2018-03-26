package com.shutafin.model.web.email;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationDealWeb {

    @NotNull
    private EmailReason emailReason;

    @NotNull
    private Long dealId;

    @NotBlank
    private String dealTitle;

    @NotNull
    private EmailUserImageSource userOrigin;

    private EmailUserImageSource userToChange;

    @NotNull
    @Valid
    private List<EmailUserLanguage> emailUserLanguage;
}
