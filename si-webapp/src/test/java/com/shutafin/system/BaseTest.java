package com.shutafin.system;

import com.shutafin.model.web.APIWebResponse;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Created by Edward Kats on 7/27/2017.
 */
public interface BaseTest {

    APIWebResponse getResponse(MvcResult mvcResult);

    APIWebResponse getResponse(ControllerRequest request);


}
