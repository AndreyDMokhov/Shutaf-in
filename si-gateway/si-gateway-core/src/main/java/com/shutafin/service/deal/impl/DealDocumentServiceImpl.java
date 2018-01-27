package com.shutafin.service.deal.impl;

import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.DealTitleChangeWeb;
import com.shutafin.sender.deal.DealDocumentControllerSender;
import com.shutafin.service.deal.DealDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class DealDocumentServiceImpl implements DealDocumentService {

    @Autowired
    private DealDocumentControllerSender dealDocumentControllerSender;
    
    @Override
    public DealUserDocumentWeb addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, Long userId) {
        return dealDocumentControllerSender.addDealDocument(dealUserDocumentWeb, userId);
    }

    @Override
    public DealUserDocumentWeb getDealDocument(Long userId, Long dealDocumentId) {
        return dealDocumentControllerSender.getDealDocument(userId, dealDocumentId);
    }

    @Override
    public void deleteDealDocument(Long userId, Long dealDocumentId) {
        dealDocumentControllerSender.deleteDealDocument(userId, dealDocumentId);
    }

    @Override
    public DealUserDocumentWeb renameDealDocument(Long userId, Long dealDocumentId, DealTitleChangeWeb newTitle) {
        return dealDocumentControllerSender.renameDealDocument(userId, dealDocumentId, newTitle);
    }

}
