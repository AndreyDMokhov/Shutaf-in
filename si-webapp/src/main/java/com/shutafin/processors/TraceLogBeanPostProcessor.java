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
    HttpServletResponse response;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        if (beanClass.getName().contains("com.shutafin") && log.isTraceEnabled() &&
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

                Gson gson = new Gson();
                String methodName = method.getName();

log.trace("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
                System.out.println("Class name: " + beanClass.getName());
                System.out.println("Method name: \t" + methodName);

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    log.trace("\t\t{}:{} = {}", parameters[i].getType().getTypeName(), parameters[i].getName(), gson.toJson(args[i]));
                    //type:name = value
                }


                Object methodResult = executeMethod(method, bean, args);
                if (!method.getReturnType().getName().equals("void")){
                    System.out.println("Method " + methodName + " result: " + gson.toJson(methodResult, method.getReturnType()));
                }else {
                    System.out.println("Method " + methodName + " result: "+ method.getReturnType().getName());
                }

                return methodResult;
            });
        }else{
            return Enhancer.create(beanClass, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    Gson gson = new Gson();
                    String methodName = method.getName();

                    System.out.println("-------------");
                    System.out.println("Class name: " + beanClass.getName());
                    System.out.println("Method name: " + methodName);

                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        System.out.println(methodName + " parameter name: " + parameters[i].getName());
                        System.out.println(methodName + " parameter type: " + parameters[i].getType().getTypeName());
                        if (parameters[i].getType() != HttpServletResponse.class){
                            System.out.println(methodName + " parameter value: " + gson.toJson(args[i]));
                        }else{
                            System.out.println(methodName + " Parameter value: " + parameters[i].getType().getInterfaces());
                            System.out.println(methodName + " Parameter value: " + args[i]);
                            response = (HttpServletResponse) args[i];
                        }
                    }

                    Object methodResult = executeMethod(method, bean, args);
                    if (!method.getReturnType().getName().equals("void")){
                        System.out.println("Method " + methodName + " result: " + gson.toJson(methodResult, method.getReturnType()));
                    }else {
                        System.out.println("Method " + methodName + " result: "+ method.getReturnType().getName());
                    }
                    System.out.println("-------------");
                    return methodResult;
                }
            });
        }
    }

    private Object executeMethod(Method method, Object bean, Object ... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}


