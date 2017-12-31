package com.shutafin.model.web.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Edward Kats
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountUserLanguageWeb {
    @NotNull
    @Min(1)
    private Integer id;
}
