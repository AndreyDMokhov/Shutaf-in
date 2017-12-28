package com.shutafin.service.impl.chat;

import com.shutafin.service.WebSocketSessionService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionServiceImpl implements WebSocketSessionService {

    private static final String USER_ID = "userId";

    private ConcurrentHashMap<Long, WebSocketSession> wsSessions = new ConcurrentHashMap<>();

    @Override
    public void addWsSession(WebSocketSession webSocketSession) {
        wsSessions.put((Long) webSocketSession.getAttributes().get(USER_ID), webSocketSession);
    }

    @Override
    public WebSocketSession getWsSession(Long userId) {
        if (!wsSessions.containsKey(userId)) {
            return null;
        }
        return wsSessions.get(userId);
    }

    @Override
    public String findWsSessionId(Long userId) {
        if (!wsSessions.containsKey(userId)) {
            return null;
        }
        return wsSessions.get(userId).getId();
    }

    @Override
    public void deleteWsSession(Long userId, String sessionId) {
        String sessionIdFromList = findWsSessionId(userId);
        if (sessionIdFromList!=null && sessionIdFromList.equals(sessionId)) {
            wsSessions.remove(userId);
        }
    }

    @Override
    @SneakyThrows
    public void closeWsSession(Long userId) {
        WebSocketSession webSocketSession = getWsSession(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            webSocketSession.close(CloseStatus.POLICY_VIOLATION);
            wsSessions.remove(userId);
        }
    }

}
