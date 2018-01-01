package com.shutafin.model.web.matching;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shutafin.model.web.BaseResponseDTO;

public class QuestionImportanceDTO extends BaseResponseDTO {

    @JsonCreator
    public QuestionImportanceDTO(@JsonProperty("id") Integer id,
                                 @JsonProperty("description") String description) {
        super(id, description);
    }
}
