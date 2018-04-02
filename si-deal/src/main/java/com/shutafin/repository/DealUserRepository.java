package com.shutafin.repository;

import com.shutafin.model.entities.DealUser;
import com.shutafin.model.web.deal.DealUserStatus;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealUserRepository extends BaseJpaRepository<DealUser, Long> {
    List<DealUser> findAllByDealId(Long dealId);
    List<DealUser> findAllByDealIdAndDealUserStatus(Long dealId, DealUserStatus dealUserStatus);
    List<DealUser> findAllByUserId(Long userId);
    List<DealUser> findAllByUserIdAndDealUserStatusNot(Long userId, DealUserStatus dealUserStatus);
    DealUser findByDealIdAndUserId(Long dealId, Long userId);

    List<DealUser> findAllByUserIdInAndDealUserStatus(List<Long> users, DealUserStatus dealUserStatus);
}
