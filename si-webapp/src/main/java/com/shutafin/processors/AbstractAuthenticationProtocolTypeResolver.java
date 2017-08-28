package com.shutafin.processors;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.processors.annotations.authentication.WebSocketAuthentication;

public abstract class AbstractAuthenticationProtocolTypeResolver {

    protected static final String SESSION_ID_HEADER_TOKEN = "session_id";

    protected AbstractAuthenticationProtocolTypeResolver() {};

    protected abstract String getSessionId();

    protected String getSessionIdFromProtocol() {

        String sessionId = getSessionId();
        if (sessionId == null) {
            throw new AuthenticationException();
        }

        return sessionId;
    }

    protected static AbstractAuthenticationProtocolTypeResolver getProtocolTypeResolver(WebSocketAuthentication webSocketAuthentication, Object[] parameters) {

        if (webSocketAuthentication != null) {
            return new WebSocketProtocolHandler(parameters);

        } else {
            return new HttpProtocolHandler(parameters);
        }
    }

}
