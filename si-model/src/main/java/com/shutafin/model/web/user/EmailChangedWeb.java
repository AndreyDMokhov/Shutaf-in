package com.shutafin.model.web.user;


/**
 * Created by usera on 7/24/2017.
 */
public class EmailChangedWeb {

    private Boolean isEmailChanged;

    public EmailChangedWeb() {
    }



    public EmailChangedWeb(Boolean isEmailChanged) {
        this.isEmailChanged = isEmailChanged;
    }

    public Boolean getEmailChanged() {
        return isEmailChanged;
    }

    public void setEmailChanged(Boolean emailChanged) {
        isEmailChanged = emailChanged;
    }
}
