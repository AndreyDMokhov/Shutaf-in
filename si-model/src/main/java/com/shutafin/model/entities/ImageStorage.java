package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE_STORAGE")
public class ImageStorage extends AbstractBaseEntity{

    @JoinColumn(name = "USER_IAMGE_ID")
    @OneToOne
    private UserImage userImage;

    @Column(name = "BASE_64_IMAGE", nullable = false)
    private String imageEncoded;

    public ImageStorage() {
    }

    public UserImage getUserImage() {
        return userImage;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
    }

    public String getImageEncoded() {
        return imageEncoded;
    }

    public void setImageEncoded(String imageEncoded) {
        this.imageEncoded = imageEncoded;
    }
}
