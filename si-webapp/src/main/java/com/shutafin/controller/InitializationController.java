package com.shutafin.controller;

import com.shutafin.service.InitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/initialization")
public class InitializationController {

    @Autowired
    private InitializationService initializationService;

    private static final Logger logger = LoggerFactory.getLogger(InitializationController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getAll(){

        logger.info("The info message is '{}'.", new String("some INFO message"));
        logger.trace("The trace message is '{}'.", new String("some TRACE message"));
        logger.debug("The debug message is '{}'.", new String("some DEBUG message"));
        logger.warn("The warn message is '{}'.", new String("some WARN message"));
        logger.error("The exception is '{}'.", new Exception("some exception"));

        return new HashMap<String, List>() {{
            put("languages", initializationService.findAllLanguages());
        }};

    }

    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List getLanguages(){
        return initializationService.findAllLanguages();
    }
}
