package com.shutafin.model.web.account;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Edward Kats
 */

@Setter
@Getter
public class AccountCountryResponseDTO extends BaseResponseDTO {
    public AccountCountryResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
