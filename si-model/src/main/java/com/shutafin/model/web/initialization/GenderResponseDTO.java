package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;

public class GenderResponseDTO extends BaseResponseDTO implements DataResponse {

    public GenderResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
