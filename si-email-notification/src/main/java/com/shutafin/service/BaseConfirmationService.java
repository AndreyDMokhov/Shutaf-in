package com.shutafin.service;

public interface BaseConfirmationService<T> {

    Integer LINK_HOURS_EXPIRATION = 24;

    T save(T confirmation);

    T getConfirmed(String link);
}
