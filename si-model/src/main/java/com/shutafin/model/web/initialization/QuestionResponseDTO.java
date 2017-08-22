package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;

/**
 * Created by evgeny on 8/22/2017.
 */
public class QuestionResponseDTO extends BaseResponseDTO implements DataResponse {
    private Boolean isActive;

    public QuestionResponseDTO(Integer id, String description, Boolean isActive) {
        super(id, description);
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
