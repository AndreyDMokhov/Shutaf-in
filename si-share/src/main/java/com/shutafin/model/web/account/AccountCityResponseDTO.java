package com.shutafin.model.web.account;

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

    public AccountCityResponseDTO(Integer id, String description, Integer countryId) {
        super(id, description);
        this.countryId = countryId;
    }
}