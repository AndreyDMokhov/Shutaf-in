package com.company.controller;

import com.company.model.User;
import com.company.pojo.UserBooks;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Rina on 6/15/2017.
 */
@RestController
@RequestMapping("/books")
public class UserBookController {
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody @Valid UserBooks userBooks, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            System.out.println("You have some error");
        }
    }

}
