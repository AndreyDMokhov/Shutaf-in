package com.shutafin.processors;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.AuthenticatedUserType;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationAnnotationsBeanPostProcessor implements BeanPostProcessor {

    private static final String SESSION_ID_HEADER_TOKEN = "session_id";

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
                    return method.invoke(bean, args);
                }


                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String sessionId = request.getHeader(SESSION_ID_HEADER_TOKEN);
                if (sessionId == null) {
                    throw new AuthenticationException();
                }

                User user = sessionManagementService.findUserWithValidSession(sessionId);

                if (user == null) {
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
                            parameter.getType().equals(User.class)) {
                        args[i] = user;
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

    private Object executeMethod(Method method, Object bean, Object ... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
