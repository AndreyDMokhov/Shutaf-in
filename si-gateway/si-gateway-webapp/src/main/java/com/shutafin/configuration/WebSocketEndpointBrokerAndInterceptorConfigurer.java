package com.shutafin.configuration;

import com.shutafin.service.WebSocketSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketEndpointBrokerAndInterceptorConfigurer extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private ChannelInterceptor channelInterceptor;

    @Autowired
    private HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor;

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    /**
     * Configures subscriptions path.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/api/subscribe");
    }

    /**
     * Add connection endpoint, enable SockJs, set SockJs library version,
     * set custom handshake interceptor.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/socket")
                .setAllowedOrigins("*")
                .withSockJS()
                .setClientLibraryUrl("http://cdn.jsdelivr.net/sockjs/1.1.4/sockjs.min.js")
                .setInterceptors(httpSessionIdHandshakeInterceptor);

    }

    /**
     * Set custom client inbound channel interceptor. Checks all incoming messages
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(channelInterceptor);
    }

    /**
     * Set custom argument resolver for AuthenticatedUser annotation resolving
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticatedUserAnnotationMethodArgumentResolver());
    }

    /**
     * Add all user WebSocketSessions to map in WebSocketSessionService
     */
    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        webSocketSessionService.addWsSession(session);
                        super.afterConnectionEstablished(session);
                    }
                };
            }
        });
        super.configureWebSocketTransport(registration);
    }

}