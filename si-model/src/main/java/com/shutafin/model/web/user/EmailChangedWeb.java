package com.shutafin.model.web.user;


public class EmailChangedWeb {

    private Boolean isEmailChanged;

    public EmailChangedWeb() {}

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
