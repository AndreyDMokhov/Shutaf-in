package com.shutafin.configuration;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;


@Configuration
public class TestDataSourceConfiguration {

    @Autowired
    private Environment environment;



    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.shutafin");
        factoryBean.setJpaProperties(getJpaProperties());
        factoryBean.setJpaVendorAdapter(jpaVendor());
        factoryBean.setDataSource(dataSource());
        return factoryBean;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(javax.sql.DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.shutafin");
        factoryBean.setJpaProperties(getJpaProperties());
        factoryBean.setJpaVendorAdapter(jpaVendor());
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }

    @Bean
    public javax.sql.DataSource dataSource() {
        DataSourceProperties dataSourceProperties = dataSourceProperties();
        return DataSourceBuilder
                .create(dataSourceProperties.getClassLoader())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .type(HikariDataSource.class)
                .build();
    }


    @Bean
    public JpaVendorAdapter jpaVendor() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(Boolean.parseBoolean(environment.getRequiredProperty("spring.jpa.show-sql")));

        return vendorAdapter;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:changelog/db.changelog-master.xml");
        springLiquibase.setDataSource(dataSource());

        return springLiquibase;
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.cache.region.factory_class", environment.getRequiredProperty("spring.jpa.properties.hibernate.cache.region.factory_class"));
        properties.put("hibernate.cache.use_second_level_cache", environment.getRequiredProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
        properties.put("hibernate.cache.use_query_cache", environment.getRequiredProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
        properties.put("hibernate.cache.provider_class", environment.getRequiredProperty("spring.jpa.properties.hibernate.cache.provider_class"));
        properties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));

        return properties;
    }
}
