package com.shutafin.model.web.initialization;

import com.shutafin.model.web.DataResponse;

public class CountryWeb implements DataResponse {

    private int countryId;
    private String description;
    private int languageId;

    public CountryWeb() {
    }

    public CountryWeb(int countryId, String description, int languageId) {
        this.countryId = countryId;
        this.description = description;
        this.languageId = languageId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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
