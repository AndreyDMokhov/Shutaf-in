package com.shutafin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ImportResource(value = {
        "classpath:application-context.xml",
        "classpath:application-db.xml",
        "classpath:application-smtp.xml"})
public class App {


    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
