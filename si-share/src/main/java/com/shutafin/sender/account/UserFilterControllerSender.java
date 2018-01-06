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

    @SneakyThrows
    public List<UserSearchResponse> getFilteredUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/filter/%d", userId);

        String jsonBody = restTemplate.postForEntity(url, accountUserFilterRequest, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserSearchResponse>>() {
        });
    }

    public void saveUserFilters(Long userId, FiltersWeb filtersWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/save/%d", userId);
        restTemplate.put(url, filtersWeb);
    }

    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/city/%d", userId);
        restTemplate.put(url, cities);
    }

    public void saveUserFilterGender(Long userId, Integer genderId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/gender/%d", userId);
        restTemplate.put(url, genderId);
    }

    public void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/age-range/%d", userId);
        restTemplate.put(url, ageRangeWebDTO);
    }

    public FiltersWeb getUserFilters(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/get/%d", userId);
        return restTemplate.getForEntity(url, FiltersWeb.class).getBody();
    }

    @SneakyThrows
    public List<Integer> getCitiesForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/city/%d", userId);
        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();
        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<Integer>>() {
        });
    }

    public Integer getGenderForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/gender/%d", userId);
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    public AgeRangeWebDTO getAgeRangeForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/age-range/%d", userId);
        return restTemplate.getForEntity(url, AgeRangeWebDTO.class).getBody();
    }

}
