package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealTitleChangeWeb {

    @Length(min = 3, max = 50)
    private String title;
}
