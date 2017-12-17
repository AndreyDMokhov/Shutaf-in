package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternalDealRemoveUserWeb {

    private Long dealId;
    private Long userOriginId;
    private Long userToRemoveId;
    
}
