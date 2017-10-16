package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shutafin.model.web.user.UserSearchResponse;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserInfoService userInfoService;


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

    private List<UserSearchResponse> getUserResponseDTO(List<User> users) {
        List<UserSearchResponse> userSearchWebList = new ArrayList<>();

        for (User u : users) {

            UserInfoResponseDTO userInfoResponseDTO = userInfoService.getUserInfo(u);


            userSearchWebList.add(
                    new UserSearchResponse(
                            userInfoResponseDTO.getUserId(),
                            userInfoResponseDTO.getFirstName(),
                            userInfoResponseDTO.getLastName(),
                            userInfoResponseDTO.getUserImage(),
                            userInfoResponseDTO.getUserImageId(),
                            userInfoResponseDTO.getGenderId(),
                            userInfoResponseDTO.getCityId(),
                            userInfoResponseDTO.getCountryId(),
                            userInfoResponseDTO.getDateOfBirth() == null ? null : userInfoResponseDTO.getDateOfBirth().getTime()
                    )
            );
        }

        return userSearchWebList;
    }


    private List<User> findMatchingUsersFromList(List<User> users, String fullName) {

        return users.stream()
                .filter(u -> String.valueOf(u.getFirstName() + " " + u.getLastName()).equals(fullName))
                .filter(u -> String.valueOf(u.getLastName() + " " + u.getFirstName()).equals(fullName))
                .collect(Collectors.toList());
    }
}
