package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDocumentWeb {

    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String fileData;

    private Long createdDate;

    @NotNull
    private Integer documentTypeId;

    @Length(min = 1, max = 50)
    private String documentTitle;
}
