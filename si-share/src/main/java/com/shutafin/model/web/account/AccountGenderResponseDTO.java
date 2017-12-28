package com.shutafin.model.web.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Edward Kats
 */
@Getter
@Setter
public class AccountGenderResponseDTO extends BaseResponseDTO {

    @JsonCreator
    public AccountGenderResponseDTO(@JsonProperty("id") Integer id, @JsonProperty("description") String description) {
        super(id, description);
    }
}
