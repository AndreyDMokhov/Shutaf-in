package com.shutafin.controller;

import com.shutafin.service.RegistrationService;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    @Before
    public void setUp() throws Exception {

        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void registration() throws Exception {
        mockMvc.perform(post("/users/registration/request")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\":\"bbb\",\"lastName\":\"bbb\",\"email\":\"bbb@bbb\",\"password\":\"111111Zz\",\"userLanguageId\":1}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void confirmRegistration() throws Exception {
    }

}