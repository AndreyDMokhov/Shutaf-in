package com.shutafin.sender.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.account.AccountUserFilterRequest;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserFilterControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public List<UserSearchResponse> getFilteredUsers(Long userId, AccountUserFilterRequest accountUserFilterRequest) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/filter/%d", userId);

        return new ObjectMapper().convertValue(
                new RestTemplate().postForEntity(url, accountUserFilterRequest, List.class)
                        .getBody(), new TypeReference<List<UserSearchResponse>>() {});
    }

    public void saveUserFilters(Long userId, FiltersWeb filtersWeb) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/save/%d", userId);

        new RestTemplate().put(url, filtersWeb);
    }

    public void saveUserFilterCity(Long userId, List<Integer> cities) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/city/%d", userId);

        new RestTemplate().put(url, cities);
    }
    public void saveUserFilterGender(Long userId, Integer genderId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/gender/%d", userId);

        new RestTemplate().put(url, genderId);
    }
    public void saveUserFilterAgeRange(Long userId, AgeRangeWebDTO ageRangeWebDTO) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/age-range/%d", userId);

        new RestTemplate().put(url, ageRangeWebDTO);
    }

    public FiltersWeb getUserFilters(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/get/%d", userId);

        return new RestTemplate().getForEntity(url, FiltersWeb.class).getBody();
    }

    public List<Integer> getCitiesForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/city/%d", userId);

        return new RestTemplate().getForEntity(url, List.class).getBody();
    }

    public Integer getGenderForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/gender/%d", userId);

        return new RestTemplate().getForEntity(url, Integer.class).getBody();
    }

    public AgeRangeWebDTO getAgeRangeForFilter(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/filters/age-range/%d", userId);

        return new RestTemplate().getForEntity(url, AgeRangeWebDTO.class).getBody();
    }

}
