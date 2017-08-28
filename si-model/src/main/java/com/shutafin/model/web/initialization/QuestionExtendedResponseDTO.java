package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;


public class QuestionExtendedResponseDTO extends BaseResponseDTO implements DataResponse {

    public QuestionExtendedResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
