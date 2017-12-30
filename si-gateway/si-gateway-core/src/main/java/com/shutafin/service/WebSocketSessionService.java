package com.shutafin.service;

public interface WebSocketSessionService {

    void addWsSession(Long userId, String sessionId);

    String findWsSessionId(Long userId);

    void deleteWsSession(Long userId, String sessionId);

}
