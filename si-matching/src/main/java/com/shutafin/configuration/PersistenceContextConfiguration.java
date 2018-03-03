package com.shutafin.configuration;

import com.shutafin.repository.base.BaseRepositoryFactoryBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCaching
@EnableJpaRepositories(basePackages = "com.shutafin", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class PersistenceContextConfiguration {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
