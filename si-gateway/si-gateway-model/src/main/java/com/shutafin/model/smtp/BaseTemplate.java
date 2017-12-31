package com.shutafin.model.smtp;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class BaseTemplate {

    private static final String HTML_TEMPLATE = "<html><body><h1>${header}</h1><p>${section}</p><hr/><p>${signature}</p></body></html>";
    private static final String HEADER_TOKEN = "header";
    private static final String SECTION_TOKEN = "section";
    private static final String SIGNATURE_TOKEN = "signature";

    private String emailHeader;
    private String section;
    private String signature;

    public BaseTemplate(String emailHeader, String section, String signature) {
        this.emailHeader = emailHeader;
        this.section = section;
        this.signature = signature;
    }

    public Map<String, String> getTokenValueMap() {
        Map<String, String> tokens = new HashMap<>();
        tokens.put(HEADER_TOKEN, emailHeader);
        tokens.put(SECTION_TOKEN, section);
        tokens.put(SIGNATURE_TOKEN, signature);
        return tokens;
    }

    public String getHtmlTemplate() {
        return HTML_TEMPLATE;
    }

    public String getEmailHeader() {
        return emailHeader;
    }
}
