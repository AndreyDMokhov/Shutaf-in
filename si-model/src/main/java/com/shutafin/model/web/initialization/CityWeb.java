package com.shutafin.model.web.initialization;

import com.shutafin.model.web.DataResponse;

public class CityWeb implements DataResponse {
    private int cityId;
    private String description;
    private int languageId;
    private int countryId;

    public CityWeb() {
    }

    public CityWeb(int cityId, String description, int languageId, int countryId) {
        this.cityId = cityId;
        this.description = description;
        this.languageId = languageId;
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
