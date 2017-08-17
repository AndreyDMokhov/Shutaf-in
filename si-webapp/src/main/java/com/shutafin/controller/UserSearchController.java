package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserSearchResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserSearchController {

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> userSearch(@AuthenticatedUser User user, @RequestParam("name") String fullName) {

        return userSearchService.userSearch(fullName);
    }

}

