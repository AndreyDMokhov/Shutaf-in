package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.repository.common.ImagePairRepository;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.UserImageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserImageServiceImpl implements UserImageService {

    private static final String DEFAULT_AVATAR = "default_avatar.jpg";

    private UserImageRepository userImageRepository;
    private EnvironmentConfigurationService environmentConfigurationService;
    private ImagePairRepository imagePairRepository;
    private DiscoveryRoutingService routingService;

    @Autowired
    public UserImageServiceImpl(
            UserImageRepository userImageRepository,
            EnvironmentConfigurationService environmentConfigurationService,
            ImagePairRepository imagePairRepository,
            DiscoveryRoutingService routingService) {

        this.userImageRepository = userImageRepository;
        this.environmentConfigurationService = environmentConfigurationService;
        this.imagePairRepository = imagePairRepository;
        this.routingService = routingService;
    }

    @Override
    @SneakyThrows
    public AccountUserImageWeb addUserImage(AccountUserImageWeb image, User user) {

        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/";
        ResponseEntity<AccountUserImageWeb> response = new RestTemplate().postForEntity(
                url,
                image,
                AccountUserImageWeb.class,
                new HashMap<String, Object>() {{
                    put("userId", user.getId());
                }});
        return response.getBody();
    }

    @Override
    public AccountUserImageWeb getUserImage(User user, Long userImageId) {

        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/images/%d/users/%d", userImageId, user.getId());
        ResponseEntity<AccountUserImageWeb> response = new RestTemplate().getForEntity(url, AccountUserImageWeb.class);
        return response.getBody();
    }

    @Override
    public void deleteUserImage(User user, Long userImageId) {
        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + String.format("/images/%d/users/%d", userImageId, user.getId());

        new RestTemplate().delete(url, new HashMap<>());
    }

    //todo remove this after registration completes
    @Override
    public void createUserImageDirectory(User user) {
        String userDirPath = getUserDirectoryPath(user);
        File directory = new File(userDirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public List<UserImage> getAllUserImages(User user) {
        return userImageRepository.findAllByUser(user);
    }

    @Override
    public UserImage getOriginalUserImage(UserImage compressedUserImage) {
        return imagePairRepository.findOriginalUserImage(compressedUserImage);
    }


    @Override
    @SneakyThrows
    //todo remote this after email notification
    public String getDefaultImageBase64() {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_AVATAR);

        if (resourceAsStream != null) {
            byte[] imageDecoded = new byte[resourceAsStream.available()];
            resourceAsStream.read(imageDecoded);
            resourceAsStream.close();
            return Base64.getEncoder().encodeToString(imageDecoded);
        }

        return null;

    }


    private String getUserDirectoryPath(User user) {
        return environmentConfigurationService.getLocalImagePath() + user.getId()
                + File.separator;
    }

}
