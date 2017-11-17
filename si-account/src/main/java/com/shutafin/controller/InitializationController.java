package com.shutafin.controller;

import com.shutafin.core.service.InitializationService;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/initialization")
@NoAuthentication
@Slf4j
public class InitializationController {

    @Autowired
    private InitializationService initializationService;

    @RequestMapping(value = "/all/{languageId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getAll(@PathVariable("languageId") Integer languageId) {
        log.debug("/initialization/all/{languageId}");
        return new HashMap<String, List>() {{
            put("genders", initializationService.findAllGendersByLanguage(languageId));
            put("countries", initializationService.findAllCountriesByLanguage(languageId));
            put("cities", initializationService.findAllCitiesByLanguage(languageId));
            put("languages", initializationService.findAllLanguages());
        }};
    }

    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getLanguages() {
        log.debug("/initialization/languages");
        return new HashMap<String, List>() {{
            put("languages", initializationService.findAllLanguages());
        }};
    }

    @RequestMapping(value = "/genders", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getGenders(@RequestParam(defaultValue = "1") Integer languageId) {
        log.debug("/initialization/genders");
        return new HashMap<String, List>() {{
            put("genders", initializationService.findAllGendersByLanguage(languageId));
        }};
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getCountries(@RequestParam(defaultValue = "1") Integer languageId) {
        log.debug("/initialization/countries");
        return new HashMap<String, List>() {{
            put("countries", initializationService.findAllCountriesByLanguage(languageId));
        }};
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getCities(@RequestParam(defaultValue = "1") Integer languageId) {
        log.debug("/initialization/cities");
        return new HashMap<String, List>() {{
            put("cities", initializationService.findAllCitiesByLanguage(languageId));
        }};
    }
}
