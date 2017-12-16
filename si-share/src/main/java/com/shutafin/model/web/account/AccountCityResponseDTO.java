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
public class AccountCityResponseDTO extends BaseResponseDTO {

    private Integer countryId;

    @JsonCreator
    public AccountCityResponseDTO(@JsonProperty("id")  Integer id,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("countryId") Integer countryId) {
        super(id, description);
        this.countryId = countryId;
    }
}