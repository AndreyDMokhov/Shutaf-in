package com.shutafin.model.smtp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class EmailMessage {

    private Long userId;
    private String emailTo;
    private BaseTemplate mailTemplate;
    private Map<String, byte[]> imageSources;

}