package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DealAvailableUsersResponse {

    private List<Long> availableUsers;
    private DealUserWeb activeDeal;
}
