package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.model.types.DealUserPermissionTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DEAL_PANEL_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
