package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import lombok.*;


@Setter
@Getter
public class CityResponseDTO extends BaseResponseDTO {

    private Integer countryId;

    public CityResponseDTO(Integer id, String description, Integer countryId) {
        super(id, description);
        this.countryId = countryId;
    }
}
