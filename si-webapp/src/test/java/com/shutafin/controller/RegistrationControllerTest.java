package com.shutafin.controller;

import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.RegistrationService;
import com.shutafin.system.BaseTestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class RegistrationControllerTest extends BaseTestImpl {

    private static final String REGISTRATION_REQUEST_URL = "/users/registration/request";

    @Autowired
    private RegistrationService registrationService;

    /**
     * This is an optional mock configuration.
     * To be Use is cases when it is necessary to define specific output from the service
     */

    @Before
    public void setUp() {
        Mockito.doNothing().when(registrationService);
    }

    @Test
    public void registrationRequestJson_IncorrectFirstName() throws Exception {

        String bodyJSON = "{\"firstName\":\"cc\",\"lastName\":\"ccc\",\"email\":\"ccc@ccc\",\"password\":\"111111Zz\",\"userLanguageId\":\"1\"}";

        APIWebResponse apiResponse = getResponse(REGISTRATION_REQUEST_URL, bodyJSON, HttpMethod.POST);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(apiResponse.getError().getErrorTypeCode(), ErrorType.INPUT.getErrorCodeType());
    }

    @Test
    public void registrationRequestObject_Positive() {
        RegistrationRequestWeb registrationRequestWeb = new RegistrationRequestWeb();
        registrationRequestWeb.setEmail("email@site.com");
        registrationRequestWeb.setFirstName("Peter");
        registrationRequestWeb.setLastName("Dale");
        registrationRequestWeb.setPassword("12345678");
        registrationRequestWeb.setUserLanguageId(1);

        APIWebResponse response = getResponse(
                                REGISTRATION_REQUEST_URL,
                                registrationRequestWeb,
                                HttpMethod.POST);

        Assert.assertNull(response.getError());
    }
}
