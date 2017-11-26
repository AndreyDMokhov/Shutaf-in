package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.BaseConfirmation;
import com.shutafin.model.entity.ConfirmationNewEmail;

public interface BaseConfirmationService<T> {

    T get(EmailNotificationWeb emailNotificationWeb, String newEmail, T connectedConfirmation);

    T save(T confirmation);

    T getConfirmed(String link);
}
