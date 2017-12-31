package com.shutafin.processors;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class WebSocketProtocolHandler extends AbstractAuthenticationProtocolTypeResolver {

    private Object[] parameters;

    WebSocketProtocolHandler(Object... parameters) {
        this.parameters = parameters;
    }

    @Override
    protected String getSessionId() {

        String sessionId = null;

        for (Object parameter : parameters) {
            if (parameter == null || parameter.getClass() != GenericMessage.class) {
                continue;
            }
            Message<?> message = (Message) parameter;

            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            sessionId = accessor.getFirstNativeHeader(SESSION_ID_HEADER_TOKEN);

        }

        return sessionId;
    }
}