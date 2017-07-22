package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.NotBlank;

public class UserImageWeb implements DataResponse {

    private Long id;

    @NotBlank
    private String image;

    private String createdDate;

    public UserImageWeb() {
    }

    public UserImageWeb(Long id, String image, String createdDate) {
        this.id = id;
        this.image = image;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
