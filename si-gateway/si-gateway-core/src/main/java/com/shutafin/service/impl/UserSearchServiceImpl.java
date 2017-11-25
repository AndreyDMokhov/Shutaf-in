package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.model.web.user.UserSearchResponse;
import com.shutafin.repository.common.FilterAgeRangeRepository;
import com.shutafin.repository.common.FilterCityRepository;
import com.shutafin.repository.common.FilterGenderRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO move not @Deprecated methods to account service

@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FilterCityRepository filterCityRepository;

    @Autowired
    private FilterGenderRepository filterGenderRepository;

    @Autowired
    private FilterAgeRangeRepository filterAgeRangeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserSearchResponse> userSearchByList(List<User> users, String fullName) {
        if (StringUtils.isEmpty(fullName)) {
            return getUserResponseDTO(users);
        }
        return getUserResponseDTO(findMatchingUsersFromList(users, fullName));
    }

    @Override
    public List<UserSearchResponse> userSearchByList(List<User> users) {
        return getUserResponseDTO(users);
    }

    @Override
    public List<Integer> getCitiesForFilter(User user) {
        //todo ms-matching

        List<City> cities = filterCityRepository.getUserFilterCity(user);
        if (cities.isEmpty()) {
            return null;
        }

        return cities
                .stream()
                .map(City::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getGenderForFilter(User user) {
        //todo ms-matching
        Gender gender = filterGenderRepository.getUserFilterGender(user);

        return gender == null ? null : gender.getId();
    }

    @Override
    public AgeRangeWebDTO getAgeRangeForFilter(User user) {
        //todo ms-matching
        return filterAgeRangeRepository.getUserFilterAgeRange(user);
    }

    @Override
    public List<UserBaseResponse> userBaseResponseByList(List<User> users) {
        return getUserBaseResponse(users);

    }

    @Override
    public UserSearchResponse findUserDataById(Long userId) {
        //todo ms-account
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return getUserResponseDTO(user);
    }

    private List<UserBaseResponse> getUserBaseResponse(List<User> users) {
        List<UserBaseResponse> userBaseResponseList = new ArrayList<>();

        for (User u : users) {

            UserInfoResponseDTO userInfoResponseDTO = userInfoService.getUserInfo(u);

            userBaseResponseList.add(
                    new UserBaseResponse(
                            userInfoResponseDTO.getUserId(),
                            userInfoResponseDTO.getFirstName(),
                            userInfoResponseDTO.getLastName(),
                            userInfoResponseDTO.getUserImage()
                    )
            );
        }

        return userBaseResponseList;
    }
    private UserSearchResponse getUserResponseDTO(User users) {
        //todo ms-account
        UserInfoResponseDTO userInfoResponseDTO = userInfoService.getUserInfo(users);

        return new UserSearchResponse(
                userInfoResponseDTO.getUserId(),
                userInfoResponseDTO.getFirstName(),
                userInfoResponseDTO.getLastName(),
                userInfoResponseDTO.getUserImage(),
                userInfoResponseDTO.getUserImageId(),
                userInfoResponseDTO.getOriginalUserImageId(),
                userInfoResponseDTO.getGenderId(),
                userInfoResponseDTO.getCityId(),
                userInfoResponseDTO.getCountryId(),
                userInfoResponseDTO.getDateOfBirth() == null ? null : userInfoResponseDTO.getDateOfBirth().getTime()
        );
    }


    private List<UserSearchResponse> getUserResponseDTO(List<User> users) {
        List<UserSearchResponse> userSearchWebList = new ArrayList<>();

        for (User u : users) {
            userSearchWebList.add(getUserResponseDTO(u));
        }
        return userSearchWebList;
    }

    private List<User> findMatchingUsersFromList(List<User> users, String fullName) {

        return users.stream()
                .filter(u -> String.valueOf(u.getFirstName() + " " + u.getLastName()).equals(fullName) ||
                        String.valueOf(u.getLastName() + " " + u.getFirstName()).equals(fullName))
                .collect(Collectors.toList());
    }
}
