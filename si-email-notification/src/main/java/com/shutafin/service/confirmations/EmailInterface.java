package com.shutafin.service.confirmations;

import com.shutafin.model.confirmations.EmailNotificationWeb;

public interface EmailInterface {

    void send(EmailNotificationWeb emailNotificationWeb);

}
