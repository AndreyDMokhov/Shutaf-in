package com.shutafin.service.impl;

import com.shutafin.model.entities.FilterCity;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.repository.account.UserInfoRepository;
import com.shutafin.repository.common.FilterCityRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.initialization.locale.CityRepository;
import com.shutafin.repository.initialization.locale.GenderRepository;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private GenderRepository genderRepository;
    //TODO moved to matching service
    private FilterCityRepository filterCityRepository;

    private DiscoveryRoutingService routingService;

    @Autowired
    public UserInfoServiceImpl(
            UserInfoRepository userInfoRepository,
            UserRepository userRepository,
            CityRepository cityRepository,
            GenderRepository genderRepository,
            FilterCityRepository filterCityRepository,
            DiscoveryRoutingService routingService) {
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
        this.filterCityRepository = filterCityRepository;
        this.routingService = routingService;
    }

    @Override
    //todo remove after registration
    public void createUserInfo(UserInfoRequest userInfoRequest, User user) {
        UserInfo userInfo = convertToUserInfo(userInfoRequest, user);
        userInfoRepository.save(userInfo);
    }


    @Override
    public AccountUserInfoResponseDTO getUserInfo(Long userId){
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/users/%d/info", userId);
        ResponseEntity<AccountUserInfoResponseDTO> response = new RestTemplate().getForEntity(url, AccountUserInfoResponseDTO.class);
        return response.getBody();
    }

    @Override
    // TODO: MS-account UserAccountController.updateUserInfo()
    //todo move filters
    public void updateUserInfo(UserInfoRequest userInfoRequest, User user) {
        UserInfo userInfo = userInfoRepository.findByUser(user);
        userInfo = setUserInfoFields(userInfoRequest, userInfo);
        userInfoRepository.save(userInfo);


        user = userRepository.findOne(user.getId());

        user.setFirstName(userInfoRequest.getFirstName());
        user.setLastName(userInfoRequest.getLastName());
        userRepository.save(user);

        if (userInfoRequest.getCityId() != null && filterCityRepository.getUserFilterCity(user).isEmpty()){

            filterCityRepository.save(new FilterCity(user, cityRepository.findOne(userInfoRequest.getCityId())));
        }
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
        userInfo.setDateOfBirth(userInfoRequest.getDateOfBirth());
        userInfo.setFacebookLink(userInfoRequest.getFacebookLink());
        userInfo.setCompany(userInfoRequest.getCompany());
        userInfo.setProfession(userInfoRequest.getProfession());
        userInfo.setPhoneNumber(userInfoRequest.getPhoneNumber());
        return userInfo;
    }
}
