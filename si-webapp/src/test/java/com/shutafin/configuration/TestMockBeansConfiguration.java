package com.shutafin.configuration;

import com.shutafin.service.LoginService;
import com.shutafin.service.RegistrationService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserImageService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by Edward Kats.
 * 26 / Jul / 2017
 */
@Configuration
public class TestMockBeansConfiguration {

    @Bean
    @Primary
    public LoginService loginService() {
        return Mockito.mock(LoginService.class);
    }

    @Bean
    public RegistrationService registrationService() {
        return Mockito.mock(RegistrationService.class);
    }

    @Bean
    @Primary
    public UserImageService userImageService() {
        return Mockito.mock(UserImageService.class);
    }

    @Bean
    @Primary
    public SessionManagementService sessionManagementService() {
        return Mockito.mock(SessionManagementService.class);
    }
}
