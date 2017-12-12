package com.shutafin.model.web.common;

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
public class LanguageWeb {
    private Integer id;
    private String description;
    private String languageNativeName;
    private Boolean isActive;
}
