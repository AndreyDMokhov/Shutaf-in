package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.NotBlank;

public class UserImageWeb implements DataResponse {

    @NotBlank
    private String image;

    private String createdDate;

    public UserImageWeb() {
    }

    public UserImageWeb(String image, String createdDate) {
        this.image = image;
        this.createdDate = createdDate;
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
