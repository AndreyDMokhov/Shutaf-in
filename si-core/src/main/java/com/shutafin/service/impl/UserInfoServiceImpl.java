package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.common.UserRepository;
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

    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private GenderRepository genderRepository;
    private UserInitializationRepository userInitializationRepository;
    private UserAccountRepository userAccountRepository;
    private UserImageService userImageService;

    @Autowired
    public UserInfoServiceImpl(
            UserInfoRepository userInfoRepository,
            UserRepository userRepository,
            CityRepository cityRepository,
            GenderRepository genderRepository,
            UserInitializationRepository userInitializationRepository,
            UserAccountRepository userAccountRepository,
            UserImageService userImageService) {
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
        this.userInitializationRepository = userInitializationRepository;
        this.userAccountRepository = userAccountRepository;
        this.userImageService = userImageService;
    }

    @Override
    public void createUserInfo(UserInfoRequest userInfoRequest, User user) {
        UserInfo userInfo = convertToUserInfo(userInfoRequest, user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfoResponseDTO getUserInfo(User user) {
        UserInfoResponseDTO userInfoResponseDTO = userInitializationRepository.getUserInitializationData(user);
        userInfoResponseDTO.setEmail(uglifyEmail(userInfoResponseDTO.getEmail()));

        Long userImageId = userAccountRepository.findUserAccountImageId(user);
        if (userImageId != null) {
            UserImage userImage = userImageService.getUserImage(user, userImageId);
            userInfoResponseDTO.addUserImage(userImage);
        }

        return userInfoResponseDTO;
    }

    @Override
    public void updateUserInfo(UserInfoRequest userInfoRequest, User user) {
        UserInfo userInfo = userInfoRepository.getUserInfo(user);
        userInfo = setUserInfoFields(userInfoRequest, userInfo);
        userInfoRepository.update(userInfo);


        user = userRepository.findById(user.getId());

        user.setFirstName(userInfoRequest.getFirstName());
        user.setLastName(userInfoRequest.getLastName());
        userRepository.update(user);
    }

    private UserInfo convertToUserInfo(UserInfoRequest userInfoRequest, User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo = setUserInfoFields(userInfoRequest, userInfo);
        return userInfo;
    }

    private UserInfo setUserInfoFields(UserInfoRequest userInfoRequest, UserInfo userInfo) {
        if (userInfoRequest.getCityId() != null) {
            userInfo.setCurrentCity(cityRepository.findById(userInfoRequest.getCityId()));
        }
        if (userInfoRequest.getGenderId() != null) {
            userInfo.setGender(genderRepository.findById(userInfoRequest.getGenderId()));
        }
        userInfo.setDateOfBirth(userInfoRequest.getDateOfBirth());
        userInfo.setFacebookLink(userInfoRequest.getFacebookLink());
        userInfo.setCompany(userInfoRequest.getCompany());
        userInfo.setProfession(userInfoRequest.getProfession());
        userInfo.setPhoneNumber(userInfoRequest.getPhoneNumber());
        return userInfo;
    }

    private String uglifyEmail(String email) {
        String[] split = email.split("@");
        String emailName = split[0];
        if (emailName.length() > 1) {
            emailName = emailName.charAt(0) +
                    emailName.substring(1, emailName.length()).replaceAll("\\S", "*");
        }
        String emailDomain = split[1];
        StringBuilder rootDomain = new StringBuilder();
        if (emailDomain.contains(".")) {
            String[] emailDomainSplit = emailDomain.split("\\.");
            emailDomain = emailDomainSplit[0];
            for (int idx = 1; idx < emailDomainSplit.length; idx++) {
                rootDomain.append('.')
                        .append(emailDomainSplit[idx]);
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
