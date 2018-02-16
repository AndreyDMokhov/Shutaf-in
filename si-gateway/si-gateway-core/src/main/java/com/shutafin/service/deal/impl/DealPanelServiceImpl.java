package com.shutafin.service.deal.impl;

import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealPanelWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.sender.deal.DealPanelControllerSender;
import com.shutafin.service.deal.DealPanelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class DealPanelServiceImpl implements DealPanelService {

    @Autowired
    private DealPanelControllerSender dealPanelControllerSender;


    @Override
    public DealPanelResponse addDealPanel(DealPanelWeb dealPanelWeb, Long userId) {
        return dealPanelControllerSender.addDealPanel(dealPanelWeb, userId);
    }

    @Override
    public DealPanelResponse getDealPanel(Long dealPanelId, Long userId) {
        return dealPanelControllerSender.getDealPanel(dealPanelId, userId);
    }

    @Override
    public DealPanelResponse renameDealPanel(Long dealPanelId, Long userId, DealTitleChangeWeb newTitle) {
        return dealPanelControllerSender.renameDealPanel(dealPanelId, userId, newTitle);
    }

    @Override
    public void deleteDealPanel(Long dealPanelId, Long userId) {
        dealPanelControllerSender.deleteDealPanel(dealPanelId, userId);
    }
}
