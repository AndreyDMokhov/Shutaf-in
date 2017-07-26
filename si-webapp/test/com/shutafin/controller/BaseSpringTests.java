package com.shutafin.controller;


import com.google.gson.Gson;
import org.junit.Before;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseSpringTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public ApiResponse test(String uri, String bodyJSON) throws Exception {
        Gson gson = new Gson();

        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(bodyJSON))
                .andDo(print())
                .andReturn();

        ApiResponse apiResponse = new ApiResponse();
        String json = result.getResponse().getContentAsString();
        if (!json.equals("")){
            apiResponse = gson.fromJson(json, ApiResponse.class);
        }
        apiResponse.setStatus(result.getResponse().getStatus());

        return apiResponse;

    }

}
