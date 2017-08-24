package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.junit.Assert;
import org.springframework.http.HttpMethod;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HelperTest extends BaseTestImpl {

    public void testControllerInputValidationError(String requestUrl, String jsonContent, List<String> errorList){
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(requestUrl)
                .setHttpMethod(HttpMethod.POST)
                .setJsonContext(jsonContent)
                .build();
        APIWebResponse response = getResponse(request);
        Assert.assertNotNull(response.getError());
        InputValidationError inputValidationError = (InputValidationError) response.getError();
        Collections.sort(errorList);
        Collections.sort(inputValidationError.getErrors());
        Assert.assertEquals(errorList, inputValidationError.getErrors());
    }

    public User createUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("q@q");
        user.setFirstName("User");
        user.setLastName("User");
        user.setCreatedDate(Date.from(Instant.now()));
        return user;
    }
}
