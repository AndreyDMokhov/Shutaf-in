package com.shutafin.controller;

import com.shutafin.core.service.UserSearchService;
import com.shutafin.model.web.user.UserSearchResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserSearchController {

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> userSearch(@RequestParam("name") String fullName) {
        if (StringUtils.isBlank(fullName)){
            return new ArrayList<>();
        }
        return userSearchService.userSearch(fullName);
    }

}

