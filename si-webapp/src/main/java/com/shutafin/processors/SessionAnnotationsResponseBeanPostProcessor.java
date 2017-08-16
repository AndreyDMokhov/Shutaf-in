package com.shutafin.processors;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.processors.annotations.authentication.SessionResponse;
import com.shutafin.service.RegistrationService;
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
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class SessionAnnotationsResponseBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Object> requiredProxyBeans = new HashMap<>();

    private static final String SESSION_ID_HEADER_TOKEN = "session_id";

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private RegistrationService registrationService;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        if (clazz.getAnnotation(SessionResponse.class) != null ) {
            requiredProxyBeans.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (requiredProxyBeans.get(beanName) == null) {
            return bean;
        }

        Class clazz = bean.getClass();

        return Enhancer.create(clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {


                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();


                Parameter[] parameters = method.getParameters();
                String link = "";


                User user = registrationService.confirmRegistration(link);
                response.setHeader("session_id", sessionManagementService.generateNewSession(user));


//                User user = sessionManagementService.findUserWithValidSession(sessionId);

                if (user == null) {
                    throw new AuthenticationException();
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
