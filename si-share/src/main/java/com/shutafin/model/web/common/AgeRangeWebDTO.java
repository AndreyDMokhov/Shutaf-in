package com.shutafin.model.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by evgeny on 10/2/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgeRangeWebDTO {

    @Min(value = 18)
    private Integer fromAge;

    @Min(value = 18)
    @Max(value = 120)
    private Integer toAge;

}
