package com.shutafin.model.web.initialization;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Created by evgeny on 6/23/2017.
 */
public class LanguageInfoWeb implements DataResponse {
    @Min(value = 1)
    private Integer languageId;

    @NotBlank
    private String description;

    @NotBlank
    private String languageNativeName;

    public LanguageInfoWeb() {
    }

    public LanguageInfoWeb(Integer languageId, String description, String languageNativeName) {
        this.languageId = languageId;
        this.description = description;
        this.languageNativeName = languageNativeName;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguageNativeName() {
        return languageNativeName;
    }

    public void setLanguageNativeName(String languageNativeName) {
        this.languageNativeName = languageNativeName;
    }
}
