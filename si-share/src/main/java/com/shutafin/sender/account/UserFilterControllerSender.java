package com.shutafin.sender.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserFilterControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    @Deprecated
    @SneakyThrows
    public List<Long> saveUserFiltersAndGetUserIds(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/users/id/%d", userId);
        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<Long>>() {
        });
    }

    @SneakyThrows
    @Deprecated
    public List<UserSearchResponse> getUsers(List<Long> usersId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                "/filters/users";

        String jsonBody = restTemplate.postForEntity(url, usersId, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {
        });
    }

    @SneakyThrows
    public List<UserSearchResponse> getFilteredUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/filter/%d", userId);

        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {
        });
    }

    @SneakyThrows
    public List<UserSearchResponse> saveUserFiltersAndGetUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/save/%d", userId);
        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {});
    }

    @Deprecated
    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/city/%d", userId);
        restTemplate.put(url, cities);
    }

    @Deprecated
    public void saveUserFilterGender(Long userId, Integer genderId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/gender/%d", userId);
        restTemplate.put(url, genderId);
    }

    @Deprecated
    public void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/age-range/%d", userId);
        restTemplate.put(url, ageRangeWebDTO);
    }

    @Deprecated
    public FiltersWeb getUserFilters(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/get/%d", userId);
        return restTemplate.getForEntity(url, FiltersWeb.class).getBody();
    }

    @SneakyThrows
    @Deprecated
    public List<Integer> getCitiesForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/city/%d", userId);
        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();
        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<Integer>>() {
        });
    }

    @Deprecated
    public Integer getGenderForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/gender/%d", userId);
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    @Deprecated
    public AgeRangeWebDTO getAgeRangeForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/age-range/%d", userId);
        return restTemplate.getForEntity(url, AgeRangeWebDTO.class).getBody();
    }

}
