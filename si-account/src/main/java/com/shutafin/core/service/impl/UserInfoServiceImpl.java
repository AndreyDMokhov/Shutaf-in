package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserImageService;
import com.shutafin.core.service.UserInfoService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.account.UserRepository;
import com.shutafin.repository.locale.CityRepository;
import com.shutafin.repository.locale.GenderRepository;
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
    private UserAccountRepository userAccountRepository;
    private UserImageService userImageService;

    @Autowired
    public UserInfoServiceImpl(
            UserInfoRepository userInfoRepository,
            UserRepository userRepository,
            CityRepository cityRepository,
            GenderRepository genderRepository,
            UserAccountRepository userAccountRepository,
            UserImageService userImageService) {
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
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
        UserAccount userAccount = userAccountRepository.findByUser(user);

        UserInfoResponseDTO userInfoResponseDTO = UserInfoResponseDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(uglifyEmail(user.getEmail()))
                .languageId(userAccount.getLanguage().getId())
                .build();

        UserInfo userInfo = userInfoRepository.findByUser(user);
        if (userInfo != null) {
            if (userInfo.getCurrentCity() != null) {
                userInfoResponseDTO.setCityId(userInfo.getCurrentCity().getId());
                userInfoResponseDTO.setCountryId(userInfo.getCurrentCity().getCountry().getId());
            }
            if (userInfo.getGender() != null) {
                userInfoResponseDTO.setGenderId(userInfo.getGender().getId());
            }
            userInfoResponseDTO.setFacebookLink(userInfo.getFacebookLink());
            userInfoResponseDTO.setCompany(userInfo.getCompany());
            userInfoResponseDTO.setProfession(userInfo.getProfession());
            userInfoResponseDTO.setPhoneNumber(userInfo.getPhoneNumber());
        }

        UserImage userImage = userAccount.getUserImage();
        if (userImage != null) {
            userInfoResponseDTO.addUserImage(userImage);
        }

        return userInfoResponseDTO;
    }

    @Override
    public UserInfoResponseDTO getUserInfo(Long userId) {
        return getUserInfo(userRepository.findOne(userId));
    }

    @Override
    public void updateUserInfo(UserInfoRequest userInfoRequest, User user) {
        // TODO: filterCity
        UserInfo userInfo = userInfoRepository.findByUser(user);
        userInfo = setUserInfoFields(userInfoRequest, userInfo);

        user = userRepository.findOne(user.getId());
        user.setFirstName(userInfoRequest.getFirstName());
        user.setLastName(userInfoRequest.getLastName());
    }

    private UserInfo convertToUserInfo(UserInfoRequest userInfoRequest, User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo = setUserInfoFields(userInfoRequest, userInfo);
        return userInfo;
    }

    private UserInfo setUserInfoFields(UserInfoRequest userInfoRequest, UserInfo userInfo) {
        if (userInfoRequest.getCityId() != null) {
            userInfo.setCurrentCity(cityRepository.findOne(userInfoRequest.getCityId()));
        }
        if (userInfoRequest.getGenderId() != null) {
            userInfo.setGender(genderRepository.findOne(userInfoRequest.getGenderId()));
        }
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
