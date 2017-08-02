package com.shutafin.controller;


import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.service.EmailChangeConfirmationService;
import com.shutafin.service.SessionManagementService;
import com.shutafin.system.BaseTestImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;



import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
public class EmailChangeConfirmationControllerTest extends BaseTestImpl{

    private static final String EMAIL_CHANGE_REQUEST_URL = "/users/account/change-email-request";

    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private EmailChangeConfirmationService emailChangeConfirmationService;

    @Before
    public void setUp() {
        Mockito.doNothing().when(emailChangeConfirmationService).emailChangeRequest(any(User.class), any(EmailChangeConfirmationWeb.class));
        Mockito.when(sessionManagementService.findUserWithValidSession(anyString())).thenReturn(new User());
    }

    @Test
    public void emailChangeRequest_Positive(){
        APIWebResponse response = getResponse(EMAIL_CHANGE_REQUEST_URL, HttpMethod.POST);
        Assert.assertNull(response.getError());
    }

    @Test
    public void emailChangeRequestJson_IncorrectPassword() throws Exception{
        String bodyJSON = "{\"userPassword\":\"222\",\"newEmail\":\"ccc@site.com\"}";
        APIWebResponse apiResponse = getResponse(EMAIL_CHANGE_REQUEST_URL, bodyJSON, HttpMethod.POST);
        Assert.assertNotNull(apiResponse.getError());
        Assert.assertEquals(apiResponse.getError().getErrorTypeCode(), ErrorType.INPUT.getErrorCodeType());
    }

    @Test
    public void setEmailChangeRequestObject_Positive(){
        EmailChangeConfirmationWeb emailChangeConfirmationWeb = new EmailChangeConfirmationWeb();
        emailChangeConfirmationWeb.setUserPassword("22222222");
        emailChangeConfirmationWeb.setNewEmail("bbb@site.com");

        APIWebResponse response = getResponse(
                                    EMAIL_CHANGE_REQUEST_URL,
                                    emailChangeConfirmationWeb,
                                    HttpMethod.POST);

        Assert.assertNull(response.getError());
    }
}
