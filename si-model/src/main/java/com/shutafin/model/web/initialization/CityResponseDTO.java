package com.shutafin.model.web.initialization;

import com.shutafin.model.web.BaseResponseDTO;
import com.shutafin.model.web.DataResponse;

public class CityResponseDTO extends BaseResponseDTO implements DataResponse {

    private Integer countryId;

    public CityResponseDTO(Integer id, String description, Integer countryId) {
        super(id, description);
        this.countryId = countryId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
