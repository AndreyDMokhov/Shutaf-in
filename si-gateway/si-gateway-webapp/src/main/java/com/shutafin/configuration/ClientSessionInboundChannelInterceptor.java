package com.shutafin.configuration;

import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

/**
 * Checks the incoming request on subject of having subscription and authentication
 */
public class ClientSessionInboundChannelInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor.getCommand()== StompCommand.CONNECT || accessor.getCommand()== StompCommand.SUBSCRIBE) {
            sessionManagementService.validate(accessor.getFirstNativeHeader("session_id"));
        }
        return message;
    }
}