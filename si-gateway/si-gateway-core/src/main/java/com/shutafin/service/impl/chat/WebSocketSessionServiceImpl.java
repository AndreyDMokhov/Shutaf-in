package com.shutafin.service.impl.chat;

import com.shutafin.service.WebSocketSessionService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionServiceImpl implements WebSocketSessionService {

    private ConcurrentHashMap<Long, String> wsSessions = new ConcurrentHashMap<>();

    @Override
    public void addWsSession(Long userId, String sessionId) {
        wsSessions.put(userId,sessionId);
    }

    @Override
    public String findWsSessionId(Long userId) {
        if (!wsSessions.containsKey(userId)) {
            return null;
        }
        return wsSessions.get(userId);
    }

    @Override
    public void deleteWsSession(Long userId, String sessionId) {
        String sessionIdFromList = findWsSessionId(userId);
        if (sessionIdFromList!=null && sessionIdFromList.equals(sessionId)) {
            wsSessions.remove(userId);
        }
    }

}
