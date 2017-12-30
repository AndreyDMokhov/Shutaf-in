package com.shutafin.sender.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserFilterControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public List<UserSearchResponse> getFilteredUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/filter/%d", userId);
            return new ObjectMapper().convertValue(
                    new RestTemplate().postForEntity(url, accountUserFilterRequest, List.class)
                            .getBody(), new TypeReference<List<UserSearchResponse>>() {
                    });
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void saveUserFilters(Long userId, FiltersWeb filtersWeb) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/save/%d", userId);
            new RestTemplate().put(url, filtersWeb);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/city/%d", userId);
            new RestTemplate().put(url, cities);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void saveUserFilterGender(Long userId, Integer genderId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/gender/%d", userId);
            new RestTemplate().put(url, genderId);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/age-range/%d", userId);
            new RestTemplate().put(url, ageRangeWebDTO);
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public FiltersWeb getUserFilters(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/get/%d", userId);
            return new RestTemplate().getForEntity(url, FiltersWeb.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public List<Integer> getCitiesForFilter(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/city/%d", userId);
            return new RestTemplate().getForEntity(url, List.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public Integer getGenderForFilter(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/gender/%d", userId);
            return new RestTemplate().getForEntity(url, Integer.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public AgeRangeWebDTO getAgeRangeForFilter(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                    String.format("/filters/age-range/%d", userId);
            return new RestTemplate().getForEntity(url, AgeRangeWebDTO.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

}
