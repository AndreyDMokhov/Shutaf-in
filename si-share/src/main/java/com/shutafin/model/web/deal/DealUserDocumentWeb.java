package com.shutafin.model.web.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shutafin.annotations.annotations.LimitSize;
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
public class DealUserDocumentWeb {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private Long dealPanelId;

    @NotBlank
    @LimitSize
    private String fileData;

    private Long createdDate;

    @NotNull
    private DocumentType documentTypeId;

    @Length(min = 1, max = 255)
    @NotBlank
    private String documentTitle;

}
