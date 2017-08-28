package com.shutafin.processors;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpProtocolHandler extends AbstractAuthenticationProtocolTypeResolver {

    Object[] parameters;

    public HttpProtocolHandler(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    protected String getSessionId() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(SESSION_ID_HEADER_TOKEN);
    }
}
