package com.shutafin.model.web.initialization;

import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.model.web.user.FiltersWeb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edward Kats
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InitializationResponse {
    private AccountInitializationResponse accountInitialization;
    private MatchingInitializationResponse matchingInitializationResponse;
    private FiltersWeb filters;
}
