package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.UserAccountService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shutafin.model.web.user.UserSearchResponse;

import java.util.*;

@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService{
    @Autowired
    UserSearchService userSearchService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountService userAccountService;

    @Override
    @Transactional
    public List<UserSearchResponse> userSearch(String fullName){

        String[] firstLastName = fullName.split(" ");

        List<User> users = new ArrayList<>();
        if(firstLastName.length == 2) {
            users = userRepository.findUsersByFirstAndLastName(firstLastName[0], firstLastName[1]);
        }else if(firstLastName.length == 1){
            users = userRepository.findUsersByName(firstLastName[0]);
        }
        List<UserSearchResponse> userSearchWebList = new ArrayList<>();
        for (User u : users) {
            String image = userAccountService.findUserAccountProfileImage(u).getImageStorage().getImageEncoded();
            userSearchWebList.add(new UserSearchResponse(u.getFirstName(), u.getLastName(), image));
        }
        return userSearchWebList;
    };


}
