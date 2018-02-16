package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternalDealUserWeb {

    @NotNull
    @Min(1)
    private Long dealId;

    @NotNull
    @Min(1)
    private Long userOriginId;

    @NotNull
    @Min(1)
    private Long userToChangeId;
    
}
