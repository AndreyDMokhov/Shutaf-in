package com.shutafin.repository;

import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@NoRepositoryBean
public interface BaseConfirmationRepository<T, ID extends Serializable> extends BaseJpaRepository<T, ID> {

    T findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(String link, Date date);

}
