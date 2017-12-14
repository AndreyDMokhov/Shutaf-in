package com.shutafin.model.web.deal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class NewTitleWeb {

    @Length(min = 3, max = 50)
    private String title;
}
