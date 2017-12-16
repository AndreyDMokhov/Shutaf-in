package com.shutafin.model.web.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Edward Kats
 */

@Setter
@Getter
public class AccountCountryResponseDTO extends BaseResponseDTO {

    @JsonCreator
    public AccountCountryResponseDTO(@JsonProperty("id") Integer id, @JsonProperty("description") String description) {
        super(id, description);
    }
}
