package com.shutafin.model.web.initialization;

import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.web.deal.DealInitializationResponse;
import com.shutafin.model.web.matching.MatchingInitializationResponse;
import com.shutafin.model.web.user.FiltersWeb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<ChatWithUsersListDTO> listOfChats;
    private DealInitializationResponse dealInitializationResponse;
}
