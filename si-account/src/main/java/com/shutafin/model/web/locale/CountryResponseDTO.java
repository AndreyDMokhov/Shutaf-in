package com.shutafin.model.web.locale;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountryResponseDTO extends BaseResponseDTO {

    public CountryResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
