package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InternalDealUserDocumentWeb {

    private Long id;

    private Long userId;

    @NotNull
    private Long dealPanelId;

    @NotBlank
    private String fileData;

    private Long createdDate;

    @NotNull
    private Integer documentTypeId;

    @Length(min = 1, max = 50)
    private String documentTitle;
}
