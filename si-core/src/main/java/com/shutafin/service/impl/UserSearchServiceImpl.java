package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shutafin.model.web.user.UserSearchResponse;

import java.util.*;

@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    @Transactional
    public List<UserSearchResponse> userSearch(String fullName) {

        List<User> users = findUsers(fullName);

        List<UserSearchResponse> userSearchWebList = new ArrayList<>();

        for (User u : users) {

            UserInfoResponse userInfoResponse = userInfoService.getUserInfo(u);


            userSearchWebList.add(
                    new UserSearchResponse(
                            userInfoResponse.getUserId(),
                            userInfoResponse.getFirstName(),
                            userInfoResponse.getLastName(),
                            userInfoResponse.getUserImage(),
                            userInfoResponse.getGenderId(),
                            userInfoResponse.getCityId(),
                            userInfoResponse.getCountryId()
                    )
            );
        }
        return userSearchWebList;
    }

    private List<User> findUsers(String fullName) {

        List<String> names = Arrays.asList(fullName.split(" "));

        return userRepository.findUsersByFirstAndLastName(names);
    }

}
