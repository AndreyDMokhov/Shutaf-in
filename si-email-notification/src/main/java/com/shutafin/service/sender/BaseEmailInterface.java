package com.shutafin.service.sender;

import com.shutafin.model.web.email.EmailNotificationWeb;

public interface BaseEmailInterface {

    String CONFIRMATION_URL = "/#/confirmation/";

    void send(EmailNotificationWeb emailNotificationWeb);

}
