package com.shutafin.repository;

import com.shutafin.model.entities.DealPanelUser;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealPanelUserRepository extends BaseJpaRepository<DealPanelUser, Long> {
    DealPanelUser findByDealPanelIdAndUserId(Long dealPanelId, Long userId);
    List<DealPanelUser> findAllByDealPanelIdAndDealUserPermissionType(Long dealPanelId, DealUserPermissionType
            dealUserPermissionType);
    List<DealPanelUser> findAllByDealPanelDealIdAndUserId(Long dealId, Long useId);
}
