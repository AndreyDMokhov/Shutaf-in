package com.shutafin.model.smtp;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class BaseTemplate {

    private static final String DEFAULT_HTML_TEMPLATE = "<html><body><h1>${header}</h1><p>${section1}</p><p>${section2}</p><hr/><p>${backwardCredentials}</p></body></html>";
    private static final String DEFAULT_HEADER_TOKEN = "header";
    private static final String DEFAULT_SECTION_1_TOKEN = "section1";
    private static final String DEFAULT_SECTION_2_TOKEN = "section2";
    private static final String DEFAULT_BACKWARD_CREDENTIALS_TOKEN = "backwardCredentials";



    private String emailHeader;
    private String firstSection;
    private String secondSection;
    private String backwardCredentials;


    public BaseTemplate(String emailHeader, String firstSection, String secondSection, String backwardCredentials) {
        this.emailHeader = emailHeader;
        this.firstSection = firstSection;
        this.secondSection = secondSection;
        this.backwardCredentials = backwardCredentials;
    }


    @JsonIgnore
    public Map<String, String> getTokenValueMap() {
        Map<String, String> tokens = new HashMap<>();
        tokens.put(DEFAULT_HEADER_TOKEN, emailHeader);
        tokens.put(DEFAULT_SECTION_1_TOKEN, firstSection);
        tokens.put(DEFAULT_SECTION_2_TOKEN, secondSection);
        tokens.put(DEFAULT_BACKWARD_CREDENTIALS_TOKEN, backwardCredentials);
        return tokens;
    }


    @JsonValue
    public String getHtmlTemplate() {
        return DEFAULT_HTML_TEMPLATE;
    }

    @JsonValue
    public String getHeader() {
        return this.emailHeader;
    }


}
