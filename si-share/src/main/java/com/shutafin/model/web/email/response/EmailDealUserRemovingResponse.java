package com.shutafin.model.web.email.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailDealUserRemovingResponse {

    private Long dealId;
    private Long userIdToRemove;
    private Integer countUsersSend;
    private Integer countUsersConfirmed;
    private Boolean isConfirmed;

}
