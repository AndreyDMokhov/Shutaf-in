package com.shutafin.model.confirmations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailConfirmationResponse {

    private Long userId;
    private String newEmail;

}
