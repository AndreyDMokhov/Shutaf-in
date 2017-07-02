package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by usera on 6/29/2017.
 */
public class UserAccountSettingsWeb implements DataResponse {

    @NotBlank
    @Length(min=3, max=50)
    private String firstName;

    @NotBlank
    @Length(min=3, max=50)
    private String lastName;

    @NotBlank
    private String languageId;

    public UserAccountSettingsWeb() {
    }

    public UserAccountSettingsWeb(String firstName, String lastName, String languageId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.languageId = languageId;
    }

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

    public String getLanguageId() {return languageId;}

    public void setLanguageId(String languageId) {this.languageId = languageId;}
}
