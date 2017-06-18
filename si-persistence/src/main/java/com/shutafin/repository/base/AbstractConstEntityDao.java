package com.shutafin.repository.base;


import com.shutafin.model.AbstractConstEntity;
import org.springframework.stereotype.Repository;


@Repository
public abstract class AbstractConstEntityDao<T extends AbstractConstEntity> extends AbstractDao<T> implements Dao<T> {
}
