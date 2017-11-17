package com.shutafin.model.smtp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailMessage {

    private Long userId;
    private String emailTo;
    private BaseTemplate mailTemplate;

}