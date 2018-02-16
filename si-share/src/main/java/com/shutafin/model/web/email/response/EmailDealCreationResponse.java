package com.shutafin.model.web.email.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailDealCreationResponse {

    private Long dealId;
    private Integer countUsersSend;
    private Integer countUsersConfirmed;
    private Boolean isConfirmed;

}
