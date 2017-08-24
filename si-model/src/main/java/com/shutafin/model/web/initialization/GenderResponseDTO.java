package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenderResponseDTO extends BaseResponseDTO {

    public GenderResponseDTO(Integer id, String description) {
        super(id, description);
    }
}
