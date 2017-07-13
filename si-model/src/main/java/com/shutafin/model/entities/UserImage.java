package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "USER_IMAGE")
public class UserImage extends AbstractEntity {

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "IMAGE_STORAGE_ID")
    @OneToOne
    private ImageStorage imageStorage;

    @Column(name = "LOCAL_PATH")
    private String localPath;

    public UserImage() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ImageStorage getImageStorage() {
        return imageStorage;
    }

    public void setImageStorage(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
