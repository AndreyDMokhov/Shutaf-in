package com.shutafin.processors;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class WebSocketProtocolHandler extends AbstractAuthenticationProtocolTypeResolver {

    Object[] parameters;

    protected WebSocketProtocolHandler(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    protected String getSessionId() {

        String sessionId = null;

        for (Object parameter : parameters) {
            if (parameter.getClass() != GenericMessage.class) {
                continue;
            }
            Message<?> message = (Message) parameter;
            if (message != null) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                sessionId = accessor.getFirstNativeHeader(SESSION_ID_HEADER_TOKEN);
            }
        }

        return sessionId;
    }
}