package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by evgeny on 10/2/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgeRangeResponseDTO {

    private Integer fromAge;
    private Integer toAge;

}
