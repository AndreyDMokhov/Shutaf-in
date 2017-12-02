package com.shutafin.service.deal;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.deal.DealUserDocumentWeb;
import com.shutafin.model.web.deal.NewTitleWeb;

public interface DealDocumentService {

    DealUserDocumentWeb addDealDocument(DealUserDocumentWeb dealUserDocumentWeb, User user);
    DealUserDocumentWeb getDealDocument(User user, Long dealDocumentId);
    void deleteDealDocument(User user, Long dealDocumentId);
    DealUserDocumentWeb renameDealDocument(User user, Long dealDocumentId, NewTitleWeb newTitle);

}
