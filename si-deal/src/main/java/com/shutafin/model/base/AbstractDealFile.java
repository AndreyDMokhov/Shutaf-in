package com.shutafin.model.base;

import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.types.PermissionTypeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractDealFile extends AbstractRemovableEntity {

    @JoinColumn(name = "DEAL_FOLDER_ID", nullable = false)
    @OneToOne
    private DealPanel dealPanel;

    @Column(name = "PERMISSION_TYPE_ID", nullable = false)
    @Convert(converter = PermissionTypeConverter.class)
    private PermissionType permissionType;

    @Column(name = "LOCAL_PATH", unique = true, length = 200)
    private String localPath;

}
