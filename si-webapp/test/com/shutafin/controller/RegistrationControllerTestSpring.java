package com.shutafin.controller;


import com.shutafin.exception.exceptions.validation.InputValidationException;
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationControllerTestSpring {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

//    @InjectMocks
//    private RegistrationController registrationController;
//
//    @Mock
//    private RegistrationService registrationService;
//
//    @Mock
//    private SessionManagementService sessionManagementService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registration200Ok() throws Exception {

        String json = String.format("{\"firstName\":\"bbb\",\"lastName\":\"bbb\",\"email\":\"bbb@bbb\",\"password\":\"111111Zz\",\"userLanguageId\":\"1\"}");

//        this.registrationService.save(any(RegistrationRequestWeb.class));

        mockMvc.perform(post("/users/registration/request")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
//                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void invalidFirstNameError() throws Exception {
        String json = String.format("{\"firstName\":\"bb\",\"lastName\":\"bb\",\"email\":\"bbb@bbb\",\"password\":\"111111Za\",\"userLanguageId\":\"1\"}");

        MvcResult result = mockMvc.perform(post("/users/registration/request")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();

        System.out.println("TEST!!!!\n" + result.getResponse().getContentAsString());
    }

    @Test
    public void confirmRegistration200Ok() throws Exception {

//        Mockito.when(registrationService.confirmRegistration(any(String.class))).thenReturn(new User());
//
//        Mockito.when(sessionManagementService.generateNewSession(any(User.class))).thenReturn("3e6a0f1f-363e-42d0-b8f4-1a370d1ebe44");

        mockMvc.perform(get("/users/registration/confirmation/{link}", "9e6a0f1f-363e-42d0-b8f4-1a370d1ebe94"))
//                .andExpect(status().isOk())
//                .andExpect(header().string("session_id", containsString("3e6a0f1f-363e-42d0-b8f4-1a370d1ebe44")))
                .andDo(print());
    }

}
