package com.shutafin.model.web.user;

import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.model.entities.FilterCity;
import com.shutafin.model.entities.FilterGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by evgeny on 10/17/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FiltersWeb {
    private Long userId;
    private FilterCity filterCity;
    private FilterGender filterGender;
    private FilterAgeRange filterAgeRange;
}
