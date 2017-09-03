package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
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
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    @Transactional
    public List<UserSearchResponse> userSearch(String fullName) {

        List<User> users = findUsers(fullName);

        List<UserSearchResponse> userSearchWebList = new ArrayList<>();

        for (User u : users) {
            UserImage userImage = userAccountService.findUserAccountProfileImage(u);
            String image = null;
            if (userImage != null) {
                image = userImage.getImageStorage().getImageEncoded();
            }

            userSearchWebList.add(new UserSearchResponse(u.getFirstName(), u.getLastName(), image));
        }
        return userSearchWebList;
    }

    private List<User> findUsers(String fullName) {

        List<String> names = Arrays.asList(fullName.split(" "));

        return userRepository.findUsersByFirstAndLastName(names);
    }

}
