package com.shutafin.service.response;

public interface BaseConfirmationResponseInterface<T> {

    T getResponse(String link);

    void revertConfirmation(String link);

}
