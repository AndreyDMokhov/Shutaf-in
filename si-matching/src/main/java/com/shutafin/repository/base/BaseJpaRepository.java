package com.shutafin.repository.base;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@NoRepositoryBean
@Deprecated
public interface BaseJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    void evict(T entity);
    T refresh(T entity);
    Specification build(List<Specification> specificationList);
}
