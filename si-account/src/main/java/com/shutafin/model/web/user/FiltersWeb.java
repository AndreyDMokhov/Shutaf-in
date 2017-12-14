package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by evgeny on 10/17/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FiltersWeb {
    private List<Integer> filterCitiesIds;
    private Integer filterGenderId;
    @Valid
    private AgeRangeWebDTO filterAgeRange;
}
