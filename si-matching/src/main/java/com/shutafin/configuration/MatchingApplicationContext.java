package com.shutafin.configuration;

import com.shutafin.service.filter.filters.UsersFilterByAgeRange;
import com.shutafin.service.filter.filters.UsersFilterByCity;
import com.shutafin.service.filter.filters.UsersFilterByGender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rogov on 18.11.2017.
 */
@Configuration
public class MatchingApplicationContext {

    @Bean
    public UsersFilterByGender usersFilterByGender(){
        return new UsersFilterByGender();
    }

    @Bean
    public UsersFilterByCity usersFilterByCity(){
        return new UsersFilterByCity();
    }

    @Bean
    public UsersFilterByAgeRange usersFilterByAgeRange(){
        return new UsersFilterByAgeRange();
    }

}
