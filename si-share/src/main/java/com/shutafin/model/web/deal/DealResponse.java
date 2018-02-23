package com.shutafin.model.web.deal;

import com.shutafin.model.web.account.AccountUserImageWeb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealResponse {
    private Long dealId;
    private String title;
    private DealStatus statusId;
    private List<AccountUserImageWeb> users;
    private Map<Long, String> panels;
    private DealPanelResponse firstPanel;
}
