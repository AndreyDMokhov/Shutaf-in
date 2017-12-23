package com.shutafin.helpers;


import java.util.Map;

@Deprecated
public class EmailTemplateHelper {


    private static final String TOKEN_MATCHER_PREFIX = "${";
    private static final String TOKEN_MATCHER_SUFFIX = "}";


    public String getMessageContent(Map<String, String> tokens, String htmlTemplate) {
        return getReadyHtml(tokens, htmlTemplate);
    }


    private String getReadyHtml(Map<String, String> tokens, String html) {

        String finalHtmlTemplate = html;
        for (Map.Entry<String, String> map : tokens.entrySet()) {
            String parametrizedToken = TOKEN_MATCHER_PREFIX + map.getKey() + TOKEN_MATCHER_SUFFIX;

            String value = map.getValue() == null ? "" : map.getValue();
            finalHtmlTemplate = finalHtmlTemplate.replace(parametrizedToken, value);
        }

        return finalHtmlTemplate;
    }


}
