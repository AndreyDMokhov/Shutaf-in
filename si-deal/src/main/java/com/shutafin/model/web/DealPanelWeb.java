package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealPanelWeb {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long dealId;

    @Length(min = 3, max = 50)
    private String title;
}
