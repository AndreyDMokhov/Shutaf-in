package com.shutafin.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class UserDocumentTitleWeb {

    @Length(min = 1, max = 50)
    private String title;
}
