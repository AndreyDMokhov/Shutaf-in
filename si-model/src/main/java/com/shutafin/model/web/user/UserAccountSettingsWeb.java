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
    private String languageDes;

    public UserAccountSettingsWeb() {
    }

    public UserAccountSettingsWeb(String firstName, String lastName, String languageDes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.languageDes = languageDes;
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

    public String getLanguageDes() {return languageDes;}

    public void setLanguageDes(String languageDes) {this.languageDes = languageDes;}
}
