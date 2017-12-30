package com.shutafin.configuration;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.WebSocketSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

/**
 * Checks the incoming request on subject of having subscription and authentication
 * When gets disconnect command, removes WebSocketSession from WebSocketSessionService
 */
@Slf4j
public class ClientSessionInboundChannelInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Long userIdFromAttributes = (Long) accessor.getSessionAttributes().get("userId");

        if (accessor.getCommand() == StompCommand.CONNECT ||
                accessor.getCommand() == StompCommand.SUBSCRIBE) {

            Long userId = sessionManagementService.findUserWithValidSession(accessor.getFirstNativeHeader("session_id"));
            if (userId == null || !userId.equals(userIdFromAttributes)) {
                log.warn("Authentication exception in ClientSessionInboundChannelInterceptor:");
                log.warn("SessionId {} was not found", accessor.getFirstNativeHeader("session_id"));
                throw new AuthenticationException();
            }
        }

        if (accessor.getCommand() == StompCommand.CONNECT) {
            webSocketSessionService.addWsSession(userIdFromAttributes, accessor.getSessionId());
        }

        if (accessor.getCommand() == StompCommand.DISCONNECT) {
            webSocketSessionService.deleteWsSession(userIdFromAttributes, accessor.getSessionId());
        }

        return message;
    }
}
