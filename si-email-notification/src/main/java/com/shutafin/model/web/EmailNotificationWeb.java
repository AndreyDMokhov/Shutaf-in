package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailNotificationWeb {

    @Min(1)
    private Long userId;

    @Email
    @Length(max = 50)
    @NotBlank
    private String emailTo;

    @NotBlank
    @Length(min = 2, max = 3)
    private String languageDescription;

    @NotBlank
    private String link;

    private Map<String, byte[]> imageSources;
}
