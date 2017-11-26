package com.shutafin.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DealPanelResponse {

    private static final Long SHIFT_VALUE = 113L;

    private Long panelId;
    private String title;
    List<DealDocumentWeb> documents;

    public DealPanelResponse(Long panelId, String title, List<DealDocumentWeb> documents) {
        setPanelId(panelId);
        this.title = title;
        this.documents = documents;
    }

    public void setPanelId(Long id) {
        this.panelId = id << SHIFT_VALUE;
    }

    public Long getPanelId() {
        return panelId >> SHIFT_VALUE;
    }

}
