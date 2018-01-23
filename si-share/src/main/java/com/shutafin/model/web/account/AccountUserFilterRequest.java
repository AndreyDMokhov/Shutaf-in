package com.shutafin.model.web.account;

import com.shutafin.model.web.common.FiltersWeb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountUserFilterRequest {

    private List<Long> userIds;
    private String fullName;
    @Valid
    private FiltersWeb filtersWeb;
}
