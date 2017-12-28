package com.shutafin.model.web.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edward Kats
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountUserWeb {

    private Long userId;
    private String lastName;
    private String firstName;
}
