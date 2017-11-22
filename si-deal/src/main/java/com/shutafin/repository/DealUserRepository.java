package com.shutafin.repository;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealUser;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealUserRepository extends BaseJpaRepository<DealUser, Long> {
    List<DealUser> findAllByDealId(Long dealId);
    DealUser findByDealIdAndUserId(Long dealId, Long userId);
}
