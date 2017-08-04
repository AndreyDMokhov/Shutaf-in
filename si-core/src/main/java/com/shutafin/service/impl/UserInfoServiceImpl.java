package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public void addUserInfo(UserInfoWeb userInfoWeb, User user) {
        UserInfo userInfo = convertToUserInfo(userInfoWeb, user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo findUserInfo(User user) {
        return userInfoRepository.findUserInfo(user);
    }

    @Override
    public void updateUserInfo(UserInfoWeb userInfoWeb, User user) {
        UserInfo userInfo = findUserInfo(user);
        if (userInfo == null) {
            addUserInfo(userInfoWeb, user);
        } else {
            setUserInfoFields(userInfoWeb, userInfo);
            userInfoRepository.update(userInfo);
        }
    }

    private UserInfo convertToUserInfo(UserInfoWeb userInfoWeb, User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        setUserInfoFields(userInfoWeb, userInfo);
        return userInfo;
    }

    private void setUserInfoFields(UserInfoWeb userInfoWeb, UserInfo userInfo) {
        userInfo.setCurrentCity(cityRepository.findById(userInfoWeb.getCityId()));
        userInfo.setGender(genderRepository.findById(userInfoWeb.getGenderId()));
        userInfo.setFacebookLink(userInfoWeb.getFacebookLink());
        userInfo.setCompany(userInfoWeb.getCompany());
        userInfo.setProfession(userInfoWeb.getProfession());
        userInfo.setPhoneNumber(userInfoWeb.getPhoneNumber());
    }
}
