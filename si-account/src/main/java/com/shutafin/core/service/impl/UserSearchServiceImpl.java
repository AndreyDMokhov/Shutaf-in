package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserInfoService;
import com.shutafin.core.service.UserSearchService;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.model.web.user.UserSearchResponse;
import com.shutafin.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            UserInfoResponseDTO userInfoResponseDTO = userInfoService.getUserInfo(u);


            userSearchWebList.add(
                    new UserSearchResponse(
                            userInfoResponseDTO.getUserId(),
                            userInfoResponseDTO.getFirstName(),
                            userInfoResponseDTO.getLastName(),
                            userInfoResponseDTO.getUserImage(),
                            userInfoResponseDTO.getGenderId(),
                            userInfoResponseDTO.getCityId(),
                            userInfoResponseDTO.getCountryId()
                    )
            );
        }
        return userSearchWebList;
    }

    private List<User> findUsers(String fullName) {

        List<String> names = Arrays.asList(fullName.split(" "));

        return userRepository.findAllByFirstNameInAndLastNameIn(names, names);
    }

}
