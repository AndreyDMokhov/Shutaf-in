package com.shutafin.initialization;

import com.shutafin.service.initialization.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 6/23/2017.
 */
@RestController
@RequestMapping("/initialization")
public class InitializationController {

    @Autowired
    private InitializationService initializationService;

    @RequestMapping(value = "/constants", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, List> getConstants(){
        return initializationService.findAllConstants();
    }
}
