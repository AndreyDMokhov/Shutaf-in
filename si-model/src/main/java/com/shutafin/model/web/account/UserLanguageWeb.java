package com.shutafin.model.web.account;

import com.shutafin.model.web.DataResponse;

import javax.validation.constraints.Min;

/**
 * Created by evgeny on 6/26/2017.
 */
public class UserLanguageWeb implements DataResponse {
    private Integer languageId;

    public UserLanguageWeb() {
    }

    public UserLanguageWeb(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
}
