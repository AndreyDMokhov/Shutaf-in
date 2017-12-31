package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE_STORAGE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageStorage extends AbstractBaseEntity {

    @JoinColumn(name = "USER_IMAGE_ID")
    @OneToOne
    private UserImage userImage;

    @Column(name = "BASE_64_IMAGE", nullable = false)
    @Lob
    private String imageEncoded;

}
