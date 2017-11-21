package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractModifiableEntity;
import com.shutafin.model.types.DealStatus;
import com.shutafin.model.types.DealStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DEAL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deal extends AbstractModifiableEntity {

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "DEAL_STATUS_ID", nullable = false)
    @Convert(converter = DealStatusConverter.class)
    private DealStatus dealStatus;
}
