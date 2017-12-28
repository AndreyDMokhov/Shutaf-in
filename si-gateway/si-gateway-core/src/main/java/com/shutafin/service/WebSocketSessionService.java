package com.shutafin.service;

import org.springframework.web.socket.WebSocketSession;

public interface WebSocketSessionService {
    void addWsSession(WebSocketSession webSocketSession);

    WebSocketSession getWsSession(Long userId);

    String findWsSessionId(Long userId);

    void deleteWsSession(Long userId, String sessionId);

    void closeWsSession(Long userId);
}
