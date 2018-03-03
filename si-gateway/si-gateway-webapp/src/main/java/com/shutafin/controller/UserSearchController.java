package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.common.FiltersWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by evgeny on 10/3/2017.
 */
@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserSearchController {
    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> getMatchingUsers(@RequestParam(value = "name", required = false) String fullName,
                                                     @NotNull(message = "INP.page.NotNull") @Min(value = 0, message = "INP.page.Min")
                                                     @RequestParam(value = "page") Integer page,
                                                     @NotNull(message = "INP.results.NotNull") @Min(value = 1, message = "INP.results.Min")
                                                         @RequestParam(value = "results") Integer results,
                                                     @AuthenticatedUser Long authenticatedUserId) {
        return userMatchService.getMatchedUserSearchResponses(authenticatedUserId, fullName, page, results, null);
    }

    @RequestMapping(value = "/search/{user_id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserSearchResponse getUserById(@PathVariable("user_id") Long userId) {
        return userSearchService.findUserDataById(userId);
    }

    @RequestMapping(value = "/search/save/filters", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchResponse> saveUserFilters(@RequestParam(value = "name", required = false) String fullName,
                                                    @NotNull(message = "INP.page.NotNull") @Min(value = 0, message = "INP.page.Min")
                                                    @RequestParam(value = "page") Integer page,
                                                    @NotNull(message = "INP.results.NotNull") @Min(value = 1, message = "INP.results.Min")
                                                        @RequestParam(value = "results") Integer results,
                                                    @AuthenticatedUser Long authenticatedUserId,
                                                    @RequestBody @Valid FiltersWeb filtersWeb,
                                                    BindingResult result) {
        log.debug("/users/search");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return userMatchService.getMatchedUserSearchResponses(authenticatedUserId, fullName, page, results, filtersWeb);
    }
}
