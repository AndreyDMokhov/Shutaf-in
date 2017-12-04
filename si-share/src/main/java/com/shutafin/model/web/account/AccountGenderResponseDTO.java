package com.shutafin.model.web.account;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Edward Kats
 */
@Getter
@Setter
public class AccountGenderResponseDTO extends BaseResponseDTO {
    public AccountGenderResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
