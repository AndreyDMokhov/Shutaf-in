package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Deprecated
@Entity
@Table(name = "IMAGE_STORAGE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageStorage extends AbstractBaseEntity{

    @JoinColumn(name = "USER_IMAGE_ID")
    @OneToOne
    private UserImage userImage;

    @Column(name = "BASE_64_IMAGE", nullable = false)
    @Lob
    private String imageEncoded;

}
