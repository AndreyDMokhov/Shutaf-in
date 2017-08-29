package com.shutafin.processors;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TraceLogBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> requiredProxyBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        if (beanClass.getName().contains("com.shutafin") && log.isTraceEnabled() &&
                !beanClass.getName().contains("com.shutafin.configuration") &&
                AnnotationUtils.findAnnotation(beanClass, Configuration.class) == null) {
            requiredProxyBeans.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = requiredProxyBeans.get(beanName);
        if (beanClass == null) {
            return bean;
        }
        Class[] interfaces = beanClass.getInterfaces();

        if (interfaces != null && interfaces.length > 0) {

            ClassLoader beanClassClassLoader = beanClass.getClassLoader();

            return Proxy.newProxyInstance(beanClassClassLoader, beanClass.getInterfaces(), (proxy, method, args) -> {
                log.trace("\n\r \n\r * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * \n\r");
                Object result = helperInvoke(beanClass, bean, method, args);
                log.trace("\n\r \n\r * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * \n\r");
                return result;
            });
        } else {
            return Enhancer.create(beanClass, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    log.trace("\n\r \n\r + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + \n\r");
                    Object result = helperInvoke(beanClass, bean, method, args);
                    log.trace("\n\r \n\r + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + \n\r");
                    return result;
                }
            });
        }
    }

    private Object helperInvoke(Class beanClass, Object bean, Method method, Object...args) throws Throwable {
        Gson gson = new Gson();
        String methodName = method.getName();

        log.trace("Class name: {}", beanClass.getName());
        log.trace("Method name: \t{}", methodName);

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType() == HttpServletResponse.class) {
                log.trace("\t\t{} : {} = {}", parameters[i].getType().getTypeName(), parameters[i].getName(), args[i]);
            } else {
                log.trace("\t\t{} : {} = {}", parameters[i].getType().getTypeName(), parameters[i].getName(), gson.toJson(args[i]));
            }
        }

        Object methodResult = executeMethod(method, bean, args);
        if (method.getReturnType() == Void.TYPE) {
            log.trace("\n\r\n\rMethod {} result: {}\n\r", methodName, method.getReturnType().getName());
        } else {
            log.trace("\n\r\n\rMethod {} result: {}\n\r", methodName, gson.toJson(methodResult, method.getReturnType()));
        }

        return methodResult;
    }

    private Object executeMethod(Method method, Object bean, Object... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}


