package com.shutafin.model.web.deal;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DealUserWeb {

    private Long dealId;

    private String title;

    private DealStatus statusId;


    private DealUserStatus userStatusId;
}
