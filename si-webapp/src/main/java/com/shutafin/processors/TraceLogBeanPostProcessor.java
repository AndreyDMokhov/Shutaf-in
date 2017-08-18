package com.shutafin.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TraceLogBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Object> requiredProxyBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        if (beanClass.getName().contains("com.shutafin") && log.isTraceEnabled()) {
//            System.out.println(beanClass.getName());
//            System.out.println("-------------");
            requiredProxyBeans.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (requiredProxyBeans.get(beanName) == null) {
            return bean;
        }
        Class beanClass = bean.getClass();

        if (beanClass.getInterfaces().length == 0) {
            return Enhancer.create(beanClass, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    System.out.println("-------------");

                    System.out.println("Method name: " + method.getName());
                    System.out.println("Class name: " + beanClass.getName());

                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        System.out.println("Parameter name: " + parameters[i].getName());
                        System.out.println("Parameter type: " + parameters[i].getType().getTypeName());


                        ObjectMapper mapper = new ObjectMapper();
                        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                        try {
                            System.out.println("Parameter value: " + mapper.writeValueAsString(args[i]));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

//                        Gson gson = new Gson();
//                        GsonBuilder builder = new GsonBuilder();
//                        builder.serializeNulls();
//                        builder.enableComplexMapKeySerialization();
//                        Gson gson = builder.create();
//                        System.out.println("Parameter value: " + gson.toJson(args[i]));



                        //                        if (parameters[i].getType().getTypeName().contains("java.lang.")){
//                            System.out.println("Parameter value: " + args[i]);
//                        }else{
//                        }
//                        if (parameters[i].getType().getName().contains("com.shutafin")){
//                            Field[] declaredFields = args[i].getClass().getDeclaredFields();
//                            for (Field declaredField : declaredFields) {
//                                System.out.println(parameters[i].getType().getSimpleName() + " field name: " + declaredField.getName());
//                                System.out.println(parameters[i].getType().getSimpleName() + " field type: " + declaredField.getType().getName());
//                                declaredField.setAccessible(true);
//                                Class<?> fieldClass = Class.forName(declaredField.getType().getTypeName());
//                                Object obj = fieldClass.newInstance();
//                                System.out.println(parameters[i].getType().getSimpleName() + " field value: " + declaredField.get(args[i]));
//                            }
//                        }else {
//                            System.out.println("Parameter value: " + args[i]);
//                        }

                    }
                    System.out.println("-------------");

                    return executeMethod(method, bean, args);
                }
            });
        }
        return bean;
    }

    private Object executeMethod(Method method, Object bean, Object ... args) throws Throwable {
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}


