package com.shutafin.service.confirmations;

import com.shutafin.model.confirmations.EmailNotificationWeb;

public interface BaseEmailInterface {

    String CONFIRMATION_URL = "/#/confirmation/";

    void send(EmailNotificationWeb emailNotificationWeb);

}
