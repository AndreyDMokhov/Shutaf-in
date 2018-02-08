package com.shutafin.model.web.email.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailDealUserAddingResponse {

    private Long dealId;
    private Long userIdToAdd;
    private Integer countUsersSend;
    private Integer countUsersConfirmed;
    private Boolean isConfirmed;

}
