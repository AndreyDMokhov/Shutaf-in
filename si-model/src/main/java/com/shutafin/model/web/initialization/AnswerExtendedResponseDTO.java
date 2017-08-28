package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;


public class AnswerExtendedResponseDTO extends BaseResponseDTO implements DataResponse {
    public AnswerExtendedResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
