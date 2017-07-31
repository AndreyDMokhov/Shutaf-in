package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;

public class CountryResponseDTO extends BaseResponseDTO implements DataResponse {

    public CountryResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
