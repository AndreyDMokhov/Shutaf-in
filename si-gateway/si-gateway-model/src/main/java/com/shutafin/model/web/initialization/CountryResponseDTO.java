package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Deprecated
@Setter
@Getter
public class CountryResponseDTO extends BaseResponseDTO {

    public CountryResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
