package com.shutafin.model.web.initialization;

import com.shutafin.model.web.DataResponse;

public class GenderWeb implements DataResponse {

    private int genderId;
    private String description;
    private int languageId;

    public GenderWeb() {
    }

    public GenderWeb(int genderId, String description, int languageId) {
        this.genderId = genderId;
        this.description = description;
        this.languageId = languageId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
}
