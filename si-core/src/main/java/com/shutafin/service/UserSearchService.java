package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.web.user.AgeRangeResponseDTO;
import com.shutafin.model.web.user.UserSearchResponse;

import java.util.List;

public interface UserSearchService {
    List<UserSearchResponse> userSearchByList(List<User> users, String fullName);
    List<UserSearchResponse> userSearchByList(List<User> users);
    List<City> getCitiesForFilter(User user);
    List<Gender> getGenderForFilter(User user);
    AgeRangeResponseDTO getAgeRangeForFilter(User user);
}
