package com.shutafin.model.infrastructure;


import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "I_COUNTRY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
public class Country extends AbstractKeyConstEntity {

}
