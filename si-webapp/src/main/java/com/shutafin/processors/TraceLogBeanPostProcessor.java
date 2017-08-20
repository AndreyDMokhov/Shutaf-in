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
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TraceLogBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> requiredProxyBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        if (beanClass.getName().contains("SMTPContextConfiguration")) {
            System.out.println();
        }
        if (beanClass.getName().contains("com.shutafin") && log.isTraceEnabled() && AnnotationUtils.findAnnotation(beanClass, Configuration.class) == null) {
            System.out.println();
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

        System.out.println("-"+beanName + " interfaces="+beanClass.getInterfaces().length);

        if (interfaces != null && interfaces.length > 0) {

            ClassLoader beanClassClassLoader = beanClass.getClassLoader();
            Class<?>[] allInterfacesForBeanClass = ClassUtils.getAllInterfacesForClass(beanClass);
            System.out.println("+" + beanName + " interfaces: " + Arrays.toString(interfaces) + " allInterfacesForBeanClass: " + Arrays.toString(allInterfacesForBeanClass));

            return Proxy.newProxyInstance(beanClassClassLoader, beanClass.getInterfaces(), (proxy, method, args) -> {
                    System.out.println("+++++++++++++++");
                    System.out.println("Method name: " + method.getName());
                    System.out.println("Class name: " + beanClass.getName());
                    System.out.println("+++++++++++++++");
                    return executeMethod(method, bean, args);
            });
        }else{
            return Enhancer.create(beanClass, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {

                    Gson gson = new Gson();

                    System.out.println("-------------");
                    System.out.println("Method name: " + method.getName());
                    System.out.println("Class name: " + beanClass.getName());

                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        System.out.println("Parameter name: " + parameters[i].getName());
                        System.out.println("Parameter type: " + parameters[i].getType().getTypeName());
                        if (!parameters[i].getType().getTypeName().equals("javax.servlet.http.HttpServletResponse")){
                            System.out.println("Parameter value: " + gson.toJson(args[i]));
                        }else{
                            System.out.println("Parameter value: " + args[i]);
                        }
                    }

                    Object methodResult = executeMethod(method, bean, args);
                    if (!method.getReturnType().getName().equals("void")){
                        System.out.println("Method " + method.getName() + " result: " + gson.toJson(methodResult, method.getReturnType()));
                    }else {
                        System.out.println("Method " + method.getName() + " result: "+ method.getReturnType().getName());
                    }

                    System.out.println("-------------");
                    return methodResult;
                }
            });
        }
//        return bean;
    }

    private Object executeMethod(Method method, Object bean, Object ... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}


