package com.shutafin.repository.base;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class BaseJpaRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
//        implements BaseJpaRepository<T, ID>
{



    private EntityManager entityManager;

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

//    @Override
    public void evict(T entity) {
        entityManager.detach(entity);
    }

//    @Override
    public T refresh(T entity) {
        if (entity != null && !entityManager.contains(entity)) {
            entityManager.refresh(entityManager.merge(entity));
        }
        return entity;
    }

//    @Override
    public Specification build(List<Specification> specificationList) {
        if (specificationList == null || specificationList.isEmpty()) {
            return null;
        }

        Specification<?> result = specificationList.get(0);

        if (specificationList.size() == 1) {
            return result;
        }

        for (int i=1; i < specificationList.size(); i++) {
            result = Specifications.where(result).and(specificationList.get(i));
        }
        return result;
    }
}
