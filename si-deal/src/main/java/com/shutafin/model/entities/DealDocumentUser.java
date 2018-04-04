package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.web.deal.DealUserPermissionType;
import com.shutafin.model.types.DealUserPermissionTypeConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DEAL_DOCUMENT_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DealDocumentUser extends AbstractEntity {
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @JoinColumn(name = "DEAL_DOCUMENT_ID", nullable = false)
    @OneToOne
    private DealDocument dealDocument;

    @Column(name = "DEAL_USER_PERMISSION_ID", nullable = false)
    @Convert(converter = DealUserPermissionTypeConverter.class)
    private DealUserPermissionType dealUserPermissionType;
}
