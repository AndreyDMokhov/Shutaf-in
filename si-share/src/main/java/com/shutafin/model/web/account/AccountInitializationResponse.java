package com.shutafin.model.web.account;

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
public class AccountInitializationResponse {

    private AccountUserInfoResponseDTO userProfile;
    private List<AccountGenderResponseDTO> genders;
    private List<AccountCountryResponseDTO> countries;
    private List<AccountCityResponseDTO> cities;
}
