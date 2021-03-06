package com.shutafin.repository;

import com.shutafin.model.entities.DealDocumentUser;
import com.shutafin.model.web.deal.DealUserPermissionType;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealDocumentUserRepository extends BaseJpaRepository<DealDocumentUser, Long> {
    DealDocumentUser findByDealDocumentIdAndUserId(Long dealDocumentId, Long userId);
    List<DealDocumentUser> findAllByDealDocumentIdAndDealUserPermissionType(Long dealDocumentId,
                                                                            DealUserPermissionType dealUserPermissionType);
    List<DealDocumentUser> findAllByDealDocumentDealPanelDealIdAndUserIdAndDealUserPermissionTypeNot(Long dealId, Long userId, DealUserPermissionType permissionType);
}
