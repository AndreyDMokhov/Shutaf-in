package com.shutafin.processors;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.DataResponse;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.AuthenticatedUserType;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by evgeny on 8/14/2017.
 */
public class ControllerResponseBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Object> requiredProxyBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();

        if (clazz.getAnnotation(RestController.class) != null) {
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
                return executeMethod(method, bean, args);
            }
        });
    }

    private Object executeMethod(Method method, Object bean, Object ... args) throws Throwable {
        APIWebResponse apiWebResponse = new APIWebResponse();
        try {

            Object invoke = method.invoke(bean, args);
            apiWebResponse.setData(new DataResponse() {
                public Object getData() {
                    return invoke;
                }
            });
            apiWebResponse.setError(null);
            return apiWebResponse;

        } catch (InvocationTargetException e) {

            Throwable exception = e.getCause();
            apiWebResponse.setData(null);
            exception.printStackTrace();

            if (exception instanceof AbstractAPIException){
                apiWebResponse.setError(((AbstractAPIException)exception).getErrorResponse());
            } else if(exception instanceof Exception){
                ErrorType errorType = ErrorType.SYSTEM;
                ErrorResponse responseError = new ErrorResponse(exception.getMessage(), errorType);
                apiWebResponse.setError(responseError);
            }
            return apiWebResponse;

            //throw e.getCause();
        }
    }
}
