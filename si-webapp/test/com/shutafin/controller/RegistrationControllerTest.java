package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.RegistrationService;
import com.shutafin.service.SessionManagementService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private SessionManagementService sessionManagementService;

    @InjectMocks
    private RegistrationController registrationController;

//    @Rule
//    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void registration200Ok() throws Exception {

        String json = String.format("{\"firstName\":\"bbb\",\"lastName\":\"bbb\",\"email\":\"bbb@bbb\",\"password\":\"111111Zz\",\"userLanguageId\":\"1\"}");

        MvcResult result = mockMvc.perform(post("/users/registration/request")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println("TEST!!!!\n" + result.getResponse().getErrorMessage());
    }

    @Test(expected = NestedServletException.class)
    public void invalidFirstNameError() throws Exception {
        String json = String.format("{\"firstName\":\"bb\",\"lastName\":\"bbb\",\"email\":\"bbb@bbb\",\"password\":\"111111Za\",\"userLanguageId\":\"1\"}");

//        exception.expect(NestedServletException.class);
//        exception.expectMessage(containsString("[firstName],50,3"));

        MvcResult result = mockMvc.perform(post("/users/registration/request")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andReturn();
    }

    @Test
    public void confirmRegistration200Ok() throws Exception {

        Mockito.when(sessionManagementService.generateNewSession(any(User.class))).thenReturn("3e6a0f1f-363e-42d0-b8f4-1a370d1ebe44");

        mockMvc.perform(get("/users/registration/confirmation/{link}", "9e6a0f1f-363  e-42d0-b8f4-1a370d1ebe94"))
                .andExpect(status().isOk())
                .andExpect(header().string("session_id", containsString("3e6a0f1f-363e-42d0-b8f4-1a370d1ebe44")))
                .andDo(print());
    }

}