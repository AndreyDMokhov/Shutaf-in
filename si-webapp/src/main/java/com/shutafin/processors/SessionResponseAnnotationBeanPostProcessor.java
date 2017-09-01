package com.shutafin.processors;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.processors.annotations.response.SessionResponse;
import com.shutafin.processors.annotations.response.SessionResponseType;
import com.shutafin.service.SessionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SessionResponseAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> requiredProxyBeans = new HashMap<>();

    private static final String SESSION_ID_HEADER_TOKEN = "session_id";

    @Autowired
    private SessionManagementService sessionManagementService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());

        for (Method method : methods) {
            if (method.isAnnotationPresent(SessionResponse.class)) {
                requiredProxyBeans.put(beanName, clazz);
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = requiredProxyBeans.get(beanName);
        if (clazz == null) {
            return bean;
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            SessionResponse annotation = method.getAnnotation(SessionResponse.class);
            if (annotation == null) {continue;}
            if (annotation.value() == SessionResponseType.NEW_SESSION && !method.getReturnType().equals(User.class)) {
                throw new IllegalArgumentException("The SessionResponseType parameter is set to False or the returned class is not User.");
            }
        }

        return Enhancer.create(clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                if (!method.isAnnotationPresent(SessionResponse.class)) {
                    return executeMethod(method, bean, args);
                }
                Object retVal = executeMethod(method, bean, args);
                User user = (User) retVal;
                if (user == null) {
                    throw new AuthenticationException();
                }
                String sessionId = sessionManagementService.generateNewSession(user);

                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                response.setHeader(SESSION_ID_HEADER_TOKEN, sessionId);
                return retVal;
            }
        });
    }

    private Object executeMethod(Method method, Object bean, Object... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            log.warn("Error occurred: ", e);
            throw e.getCause();
        }
    }
}
