package com.shutafin.processors;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.processors.annotations.authentication.WebSocketAuthentication;

public abstract class AbstractAuthenticationProtocolTypeResolver {

    protected static final String SESSION_ID_HEADER_TOKEN = "session_id";

    protected abstract String getSessionId();

    public String getSessionIdFromProtocol() {

        String sessionId = getSessionId();
        if (sessionId == null) {
            throw new AuthenticationException();
        }

        return sessionId;
    }

    public static AbstractAuthenticationProtocolTypeResolver getProtocolTypeResolver(WebSocketAuthentication webSocketAuthentication, Object... parameters) {

        if (webSocketAuthentication == null) {

            return new HttpProtocolHandler();
        } else {
            return new WebSocketProtocolHandler(parameters);
        }
    }

}
