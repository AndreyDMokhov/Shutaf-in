package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LanguageResponseDTO extends BaseResponseDTO {
    private String nativeName;

    public LanguageResponseDTO(Integer id, String description, String nativeName) {
        super(id, description);
        this.nativeName = nativeName;
    }

}
