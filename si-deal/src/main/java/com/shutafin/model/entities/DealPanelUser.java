package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.web.deal.DealUserPermissionType;
import com.shutafin.model.types.DealUserPermissionTypeConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DEAL_PANEL_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DealPanelUser extends AbstractEntity {
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @JoinColumn(name = "DEAL_PANEL_ID", nullable = false)
    @OneToOne
    private DealPanel dealPanel;

    @Column(name = "DEAL_USER_PERMISSION_ID", nullable = false)
    @Convert(converter = DealUserPermissionTypeConverter.class)
    private DealUserPermissionType dealUserPermissionType;
}
