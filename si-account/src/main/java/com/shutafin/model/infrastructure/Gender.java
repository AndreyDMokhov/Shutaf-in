package com.shutafin.model.infrastructure;

import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "I_GENDER")
@NoArgsConstructor
public class Gender extends AbstractKeyConstEntity{

}
