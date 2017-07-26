package com.shutafin.controller;


import com.google.gson.Gson;
import com.shutafin.App;
import com.shutafin.model.web.APIWebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(value = App.class)
@EnableAutoConfiguration
class BaseSpringTests {

    @Autowired
    private MockMvc mockMvc;


    protected APIWebResponse getResponse(String uri, String jsonToSend) throws Exception {

        MvcResult result = mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonToSend))
                .andDo(print())
                .andReturn();

        Gson gson = new Gson();


        APIWebResponse apiResponse = gson.fromJson(result.getResponse().getContentAsString(), APIWebResponse.class);

        if (apiResponse == null) {
            apiResponse = new APIWebResponse();
        }

        return apiResponse;
    }


    protected APIWebResponse getResponse(MvcResult mvcResult) {
//        return gson.fromJson(mvcResult.getResponse().getContentAsString(), APIWebResponse.class);
        return null;
    }

    protected APIWebResponse getResponse(Object obj, HttpMethod httpMethod) {
        return null;
    }

}
