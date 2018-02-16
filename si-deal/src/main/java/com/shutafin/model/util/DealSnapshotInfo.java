package com.shutafin.model.util;

import com.shutafin.model.web.deal.DealPanelResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealSnapshotInfo {

    private Long dealId;
    private String title;
    private Integer statusId;
    private List<DealPanelResponse> dealPanels;
}
