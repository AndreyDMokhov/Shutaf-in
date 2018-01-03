package com.shutafin.configuration;

import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.service.SessionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

    private static final String SESSION_ID = "SESSION_ID";
    private static final String USER_ID = "userId";

    @Autowired
    private SessionManagementService sessionManagementService;

    /**
     * Checks session id in path variable, that was sent by SockJs.
     * After sessionId check, adds userId to attributes list, which will be add to WebSocketSession.
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String sessionId = servletRequest.getServletRequest().getParameter(SESSION_ID);

            if (sessionId != null) {
                Long userId = sessionManagementService.findUserWithValidSession(sessionId);
                if (userId == null) {
                    log.warn("Authentication exception in HttpSessionIdHandshakeInterceptor:");
                    log.warn("SessionId {} was not found", sessionId);
                    throw new AuthenticationException();
                }else{
                    attributes.put(USER_ID, userId);
                }
            }
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
    }
}