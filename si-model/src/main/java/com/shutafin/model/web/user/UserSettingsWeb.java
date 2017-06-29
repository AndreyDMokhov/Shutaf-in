package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.Length;

/**
 * Created by usera on 6/29/2017.
 */
public class UserSettingsWeb implements DataResponse {

    @Length(min=3, max=50)
    private String firstName;

    @Length(min=3, max=50)
    private String lastName;

    public UserSettingsWeb() {
    }

    public UserSettingsWeb(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
}
