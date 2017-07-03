package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;

public class UserInit implements DataResponse {
    private String firstName;
    private String lastName;
    private int languageId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public UserInit() {
    }

    public UserInit(String firstName, String lastName, int languageId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.languageId = languageId;
    }
}


