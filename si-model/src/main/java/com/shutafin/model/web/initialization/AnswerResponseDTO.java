package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;

/**
 * Created by evgeny on 8/22/2017.
 */
public class AnswerResponseDTO extends BaseResponseDTO implements DataResponse {
    private Boolean isUniversal;

    public AnswerResponseDTO(Integer id, String description, Boolean isUniversal) {
        super(id, description);
        this.isUniversal = isUniversal;
    }

    public Boolean getUniversal() {
        return isUniversal;
    }

    public void setUniversal(Boolean universal) {
        isUniversal = universal;
    }
}
