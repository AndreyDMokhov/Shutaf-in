package com.shutafin.configuration;

import com.shutafin.service.filter.UsersFilter;
import com.shutafin.service.filter.filters.UsersFilterByAgeRange;
import com.shutafin.service.filter.filters.UsersFilterByCity;
import com.shutafin.service.filter.filters.UsersFilterByGender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Rogov on 18.11.2017.
 */
@Configuration
public class MatchingAppContext {

    @Bean
    UsersFilterByGender usersFilterByGender(){
        return new UsersFilterByGender();
    }

    @Bean
    UsersFilterByCity usersFilterByCity(){
        return new UsersFilterByCity();
    }

    @Bean
    UsersFilterByAgeRange usersFilterByAgeRange(){
        return new UsersFilterByAgeRange();
    }

    @Autowired
    List<UsersFilter> usersFilters;
}
