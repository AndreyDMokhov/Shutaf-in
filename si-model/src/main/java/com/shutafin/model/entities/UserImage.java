package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.types.PermissionTypeConverter;
import com.shutafin.model.entities.types.PermissionType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_IMAGE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImage extends AbstractEntity {

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "IMAGE_STORAGE_ID")
    @OneToOne
    private ImageStorage imageStorage;

    @Column(name = "LOCAL_PATH", unique = true, length = 200)
    private String localPath;

    @Column(name = "PERMISSION_TYPE_ID", nullable = false)
    @Convert(converter = PermissionTypeConverter.class)
    private PermissionType permissionType;

}
