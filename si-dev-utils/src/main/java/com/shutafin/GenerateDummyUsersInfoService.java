package com.shutafin;

import com.shutafin.image.Image;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static com.shutafin.GenerateDummyUsersService.getEmail;
import static com.shutafin.GenerateDummyUsersService.getName;

@Service
public class GenerateDummyUsersInfoService {

    private static final String URL_UPDATE_USER_INFO = "http://localhost:8000/users/info";
    private static final String URL_UPDATE_USER_IMAGE = "http://localhost:8000/users/profile-image?";
    private static final Integer CITY_ID = 1;
    private static final Integer GENDER_ID = 1;

    private static Random random = new Random();

    private RestTemplateService restTemplateService;

    public GenerateDummyUsersInfoService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @Async
    public CompletableFuture<String> generateUsersInfo(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            restTemplateService.sendRequest(getAccountUserInfoRequest(i),
                    URL_UPDATE_USER_INFO + "?userId=" + i, HttpMethod.PUT);
            restTemplateService.sendRequest(getAccountUserImageWeb(),
                    URL_UPDATE_USER_IMAGE + "userId=" + i, HttpMethod.POST);
        }
        return CompletableFuture.completedFuture(null);
    }

    private Object getAccountUserInfoRequest(int num) {
        return AccountUserInfoRequest.builder()
                .firstName(getName())
                .lastName(getName())
                .email(getEmail(num))
                .cityId(CITY_ID)
                .genderId(GENDER_ID)
                .dateOfBirth(new Date(1000L))
                .build();
    }

    private Object getAccountUserImageWeb() {
        String imageName = "image_" + (random.nextInt(5) + 1) + ".jpg";
        return AccountUserImageWeb.builder()
                .image(Image.getImage(this.getClass(), imageName))
                .build();
    }

}
