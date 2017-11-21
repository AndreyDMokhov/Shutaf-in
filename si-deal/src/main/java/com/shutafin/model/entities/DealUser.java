package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.types.DealUserStatus;
import com.shutafin.model.types.DealUserStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DEAL_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealUser extends AbstractEntity {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @JoinColumn(name = "DEAL_ID", nullable = false)
    @OneToOne
    private Deal deal;

    @Column(name = "DEAL_STATUS_ID", nullable = false)
    @Convert(converter = DealUserStatusConverter.class)
    private DealUserStatus dealUserStatus;
}
