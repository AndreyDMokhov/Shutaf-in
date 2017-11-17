package com.shutafin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:smtp.config.properties")
public class SMTPContextConfiguration {

    @Autowired
    private Environment environment;


    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(environment.getProperty("mail.transport.protocol"));
        javaMailSender.setHost(environment.getProperty("mail.smtp.host"));
        javaMailSender.setUsername("shutafin@gmail.com");
        javaMailSender.setPassword("shutafin2017k");
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setJavaMailProperties(getMailSMTPProperties());

        return javaMailSender;
    }

    private Properties getMailSMTPProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        properties.setProperty("mail.smtp.ssl", environment.getProperty("mail.smtp.ssl"));
        properties.setProperty("mail.smtp.socketFactory.port", environment.getProperty("mail.smtp.socketFactory.port"));
        properties.setProperty("mail.smtp.socketFactory.class", environment.getProperty("mail.smtp.socketFactory.class"));
        properties.setProperty("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        properties.setProperty("mail.smtp.starttls.required", environment.getProperty("mail.smtp.starttls.required"));

        return properties;
    }

}
