package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "I_ACCOUNT_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class AccountType extends AbstractConstEntity {
}
