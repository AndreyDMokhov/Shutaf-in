package com.shutafin.controller;

import com.shutafin.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/initialization")
public class InitializationController {

    @Autowired
    private InitializationService initializationService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getAll(){
        return new HashMap<String, List>() {{
            put("languages", initializationService.findAllLanguages());
        }};

    }

    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List getLanguages(){
        return initializationService.findAllLanguages();
    }

    @RequestMapping(value = "/genders", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List getGenders(@RequestParam Integer languageId){
        return initializationService.findAllGendersByLanguage(languageId);
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List getCountries(@RequestParam Integer languageId){
        return initializationService.findAllCountriesByLanguage(languageId);
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List getCities(@RequestParam Integer languageId){
        return initializationService.findAllCitiesByLanguage(languageId);
    }
}
