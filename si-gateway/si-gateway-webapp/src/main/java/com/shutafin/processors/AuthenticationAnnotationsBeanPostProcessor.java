package com.shutafin.processors;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.AuthenticatedUserType;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.processors.annotations.authentication.WebSocketAuthentication;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationAnnotationsBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> requiredProxyBeans = new HashMap<>();

    @Autowired
    private SessionManagementService sessionManagementService;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        if (clazz.getAnnotation(RestController.class) != null && clazz.getAnnotation(NoAuthentication.class) == null) {
            requiredProxyBeans.put(beanName, clazz);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = requiredProxyBeans.get(beanName);
        if (clazz == null) {
            return bean;
        }


        return Enhancer.create(clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {

                if (method.getAnnotation(NoAuthentication.class) != null) {
                    return executeMethod(method, bean, args);
                }

                AbstractAuthenticationProtocolTypeResolver protocolTypeResolver =
                        AbstractAuthenticationProtocolTypeResolver
                                .getProtocolTypeResolver(method.getAnnotation(WebSocketAuthentication.class), args);
                String sessionId = protocolTypeResolver.getSessionIdFromProtocol();

                Long userId = sessionManagementService.findUserWithValidSession(sessionId);

                if (userId == null) {
                    throw new AuthenticationException();
                }

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    AuthenticatedUser authenticatedUser = parameter.getAnnotation(AuthenticatedUser.class);
                    if (authenticatedUser == null) {
                        continue;
                    }

                    AuthenticatedUserType type = authenticatedUser.value();

                    if (type == AuthenticatedUserType.USER &&
                            parameter.getType().equals(Long.class)) {
                        args[i] = userId;
                    }

                    if (type == AuthenticatedUserType.SESSION_ID &&
                            parameter.getType().equals(String.class)) {
                        args[i] = sessionId;
                    }
                }

                return executeMethod(method, bean, args);
            }
        });


    }

    private Object executeMethod(Method method, Object bean, Object... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
