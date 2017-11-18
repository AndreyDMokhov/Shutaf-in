package com.shutafin.model.web.locale;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CityResponseDTO extends BaseResponseDTO {

    private Integer countryId;

    public CityResponseDTO(Integer id, String description, Integer countryId) {
        super(id, description);
        this.countryId = countryId;
    }
}
