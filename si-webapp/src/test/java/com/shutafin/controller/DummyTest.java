package com.shutafin.controller;

import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.service.RegistrationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class DummyTest extends BaseSpringTests {

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
    public void registration200Ok() throws Exception {
        String uri = "/users/registration/request";
        String bodyJSON = "{\"firstName\":\"cc\",\"lastName\":\"ccc\",\"email\":\"ccc@ccc\",\"password\":\"111111Zz\",\"userLanguageId\":\"1\"}";

        APIWebResponse apiResponse = getResponse(uri, bodyJSON);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(apiResponse.getError().getErrorTypeCode(), ErrorType.INPUT.getErrorCodeType());
    }
}
