package com.shutafin.service.deal;

import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.NewTitleWeb;

public interface DealDocumentService {

    DealUserDocumentWeb addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, Long userId);
    DealUserDocumentWeb getDealDocument(Long userId, Long dealDocumentId);
    void deleteDealDocument(Long userId, Long dealDocumentId);
    DealUserDocumentWeb renameDealDocument(Long userId, Long dealDocumentId, NewTitleWeb newTitle);

}
