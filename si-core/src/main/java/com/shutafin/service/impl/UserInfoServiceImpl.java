package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.initialization.custom.UserInitializationRepository;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.service.UserImageService;
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

    @Autowired
    private UserInitializationRepository userInitializationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserImageService userImageService;

    @Override
    public void addUserInfo(UserInfoWeb userInfoWeb, User user) {
        UserInfo userInfo = convertToUserInfo(userInfoWeb, user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfoResponse getUserInfo(User user) {
        UserInfoResponse userInfoResponse = userInitializationRepository.getUserInitializationData(user);
        userInfoResponse.setEmail(uglifyEmail(userInfoResponse.getEmail()));
        Long userImageId = userAccountRepository.findUserAccountImageId(user);
        if (userImageId != null) {
            UserImage userImage = userImageService.getUserImage(user, userImageId);
            userInfoResponse.addUserImage(userImage);
        }
        UserInfo userInfo = userInfoRepository.getUserInfo(user);
        if (userInfo != null) {
            userInfoResponse.addUserInfo(userInfo);
        }

        return userInfoResponse;
    }

    @Override
    public void updateUserInfo(UserInfoWeb userInfoWeb, User user) {
        UserInfo userInfo = userInfoRepository.getUserInfo(user);
        if (userInfo == null) {
            addUserInfo(userInfoWeb, user);
        } else {
            userInfo = setUserInfoFields(userInfoWeb, userInfo);
            userInfoRepository.update(userInfo);
        }
    }

    private UserInfo convertToUserInfo(UserInfoWeb userInfoWeb, User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo = setUserInfoFields(userInfoWeb, userInfo);
        return userInfo;
    }

    private UserInfo setUserInfoFields(UserInfoWeb userInfoWeb, UserInfo userInfo) {
        if (userInfoWeb.getCityId() != null) {
            userInfo.setCurrentCity(cityRepository.findById(userInfoWeb.getCityId()));
        }
        if (userInfoWeb.getGenderId() != null) {
            userInfo.setGender(genderRepository.findById(userInfoWeb.getGenderId()));
        }
        userInfo.setFacebookLink(userInfoWeb.getFacebookLink());
        userInfo.setCompany(userInfoWeb.getCompany());
        userInfo.setProfession(userInfoWeb.getProfession());
        userInfo.setPhoneNumber(userInfoWeb.getPhoneNumber());
        return userInfo;
    }

    private String uglifyEmail(String email) {
        String[] splitted = email.split("@");
        String emailName = splitted[0];
        if (emailName.length() > 1) {
            emailName = emailName.charAt(0) +
                    emailName.substring(1, emailName.length()).replaceAll("\\S", "*");
        }
        String emailDomain = splitted[1];
        StringBuilder rootDomain = new StringBuilder();
        if (emailDomain.contains(".")) {
            String[] emailDomainSplitted = emailDomain.split("\\.");
            emailDomain = emailDomainSplitted[0];
            for (int idx = 1; idx < emailDomainSplitted.length; idx++) {
                rootDomain.append('.')
                        .append(emailDomainSplitted[idx]);
            }
        }
        if (emailDomain.length() > 1) {
            emailDomain = emailDomain.charAt(0) +
                    emailDomain.substring(1, emailDomain.length()).replaceAll("\\S", "*");
        }

        StringBuilder uglifiedEmail = new StringBuilder();
        uglifiedEmail.append(emailName)
                .append('@')
                .append(emailDomain)
                .append(rootDomain);
        return uglifiedEmail.toString();
    }
}
