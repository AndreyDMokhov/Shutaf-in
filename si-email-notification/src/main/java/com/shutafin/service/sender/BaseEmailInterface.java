package com.shutafin.service.sender;

import com.shutafin.model.web.email.EmailNotificationWeb;

public interface BaseEmailInterface {

    void send(EmailNotificationWeb emailNotificationWeb);

}
