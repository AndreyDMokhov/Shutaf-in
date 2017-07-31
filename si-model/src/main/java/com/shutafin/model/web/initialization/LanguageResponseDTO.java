package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;

/**
 * Created by evgeny on 6/23/2017.
 */
public class LanguageResponseDTO extends BaseResponseDTO implements DataResponse {
    private String nativeName;

    public LanguageResponseDTO(Integer id, String description, String nativeName) {
        super(id, description);
        this.nativeName = nativeName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
